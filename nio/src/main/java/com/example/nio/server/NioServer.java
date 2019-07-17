package com.example.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.nio.server.NioServer.Tasker.handleRequest;


/**
 * @author anson
 * @date 2019/7/16
 */
public class NioServer {

    private  static Selector selector;
    private static LinkedBlockingQueue<SelectionKey> requestQueue;
    private static ExecutorService threadPool;

    public static void main(String[] args) {
        init();
        listen();
    }

    private static void listen() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    handleRequest(key);
                }
            }catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private static void init() {
        ServerSocketChannel serverSocketChannel = null;

        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            // 将Channel设置为非阻塞的 NIO就是支持非阻塞的
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(9000),100);
            // ServerSocket，就是负责去跟各个客户端连接连接请求的
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            //就是仅仅关注这个ServerSocketChannel接收到的TCP连接的请求
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestQueue = new LinkedBlockingQueue<SelectionKey>(500);

        threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            threadPool.submit(new Tasker());
        }
    }

    public static class Tasker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    SelectionKey key = requestQueue.take();
                    handleRequest(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



        public static void handleRequest(SelectionKey key) throws IOException, ClosedChannelException {
            //假设想象一下，后台有个线程池获取到了请求
            // 下面的代码，都是在后台线程池的工作线程里在处理和执行
            SocketChannel channel = null;

            try{
                //如果说这个key是个acceptable，是个连接请求的话
                if (key.isAcceptable()) {
                    System.out.println("[" + Thread.currentThread().getName() + "]接受到连接请求");
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
                    //调用accept方法 和客户端进行三次握手
                    channel = serverSocketChannel.accept();
                    // 如果三次握手成功了之后，就可以获取到一个建立好TCP连接的SocketChannel
                    // 这个SocketChannel大概可以理解为，底层有一个Socket，是跟客户端进行连接
                    // 你的SocketChannel就是联通到那个Socket上去，负责进行网络数据的读写的
                    // 设置为非阻塞的
                    channel.configureBlocking(false);
                    //关注read请求
                    channel.register(selector,SelectionKey.OP_READ);
                    // 如果说这个key是readable，是个发送了数据过来的话，此时需要读取客户端发送过来的数据

                } else if (key.isReadable()) {
                    channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int count = channel.read(buffer);
                    // 通过底层的socket读取数据，写入buffer中，position可能就会变成21之类的
                    // 你读取到了多少个字节，此时buffer的position就会变成多少
                    System.out.println("[" + Thread.currentThread().getName()+ "]接受到请求");

                    if (count > 0) {
                        buffer.flip(); // position = 0，limit = 21，仅仅读取buffer中，0~21这段刚刚写入进去的数据
                        System.out.println("服务端接受请求：" + new String(buffer.array(),0,count));
                        channel.register(selector,SelectionKey.OP_WRITE);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put("copy".getBytes());
                    buffer.flip();

                    channel = (SocketChannel)key.channel();
                    channel.write(buffer);
                    channel.register(selector,SelectionKey.OP_READ);
                }
            }catch (Throwable t) {
                t.printStackTrace();
                if (channel != null) {
                    channel.close();
                }
            }
        }
    }
}

