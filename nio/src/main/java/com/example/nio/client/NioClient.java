package com.example.nio.client;

import javafx.concurrent.Worker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author anson
 * @date 2019/7/16
 */
public class NioClient {
    public static void main (String args[]) {
        for (int i=0; i < 10; i++ ) {
            new Tasker().start();
        }
    }

    static class Tasker extends Thread {

        @Override
        public void run() {
            SocketChannel channel = null;
            try (Selector selector = Selector.open())
            {
                //setup channel
                channel = SocketChannel.open();
                channel.configureBlocking(false);
                channel.connect(new InetSocketAddress("localhost",9000));
                //建立连接
                // listen connect
                channel.register(selector, SelectionKey.OP_CONNECT);

                while (true) {
                    // selector多路复用机制的实现  循环去遍历各个注册的Channel
                    selector.select();
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();
                        // 如果发现返回的时候一个可连接的消息 走到下面去接受数据
                        if (key.isConnectable()) {
                            channel = (SocketChannel) key.channel();
                            if (channel.isConnectionPending()) {
                                channel.finishConnect();
                                // 接下来对这个SocketChannel感兴趣的就是人家server给你发送过来的数据了
                                // READ事件，就是可以读数据的事件
                                // 一旦建立连接成功了以后，此时就可以给server发送一个请求了
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                buffer.put("hello".getBytes());
                                buffer.flip();
                                channel.write(buffer);
                            }

                            channel.register(selector,SelectionKey.OP_READ);
                        }
                        //这里的话就说明服务器端返回了一条数据可以读了
                        else if (key.isReadable()){
                            channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int len = channel.read(buffer);
                            if (len > 0) {
                                System.out.println("[" + Thread.currentThread().getName()+
                                        "]收到响应：" + new String(buffer.array(),0,len));
                                Thread.sleep(5000);
                                channel.register(selector,SelectionKey.OP_WRITE);
                            }
                        } else if (key.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.put("hello".getBytes());
                            buffer.flip();

                            channel = (SocketChannel) key.channel();
                            channel.write(buffer);
                            channel.register(selector,SelectionKey.OP_READ);
                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
