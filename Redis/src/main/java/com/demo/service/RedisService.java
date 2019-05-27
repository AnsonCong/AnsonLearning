package com.demo.service;

import com.demo.Utils.BeanUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.RedisNodes;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.RedisClient;
import org.redisson.client.RedisConnection;
import org.redisson.client.RedisException;
import org.redisson.client.protocol.RedisCommands;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.connection.ConnectionManager;
import org.redisson.connection.RedisClientEntry;
import org.redisson.misc.RPromise;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Anson
 * @date 2019/5/25
 */

@Slf4j
public class RedisService {

    private final Redisson redisson;

    /**
     * The constructor. init Redisson with SentinelServers
     */
    public RedisService() {
        Config config = null;
        String workingDir = BeanUtility.getWorkingDirectory();
        log.info("Working Directory >>> {}", workingDir);
        File file = new File(workingDir + "/redis-config.json");
        if (file.exists()) {
            try {
                config = Config.fromJSON(file);
            } catch (Exception e) {
                throw new RedisException("An exception occurred while parsing redis-config.json.", e);
            }
        } else {
            throw new RedisException("Missing in classpath: redis-config.json.");
        }

        redisson = (Redisson) Redisson.create(config);
    }
    /*
    *  1.SingleServer
    *   Config config = new Config();
        config.useSingleServer().setAddress("address");
        Redisson.create(config);
    *
    *
    * 2.SentinelServer
    *   Config config = new Config();
    *   config.useSentinelServers().setMasterName("aaa");
        config.useSentinelServers().addSentinelAddress("address");
        Redisson.create(config);
    *
    *
    * 3.MasterSlaveServers
    *   Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress("127.0.0.1:6379")
            .addSlaveAddress("127.0.0.1:6389", "127.0.0.1:6332", "127.0.0.1:6419")
            .addSlaveAddress("127.0.0.1:6399");
       Redisson.create(config);

    * 4.ClusterServers
    *   Config config1 = new Config();
        config1.useClusterServers().addNodeAddress("123");
        Redisson.create(config1);
    * */

    @PreDestroy
    public void stop() {
        redisson.shutdown();
    }

    /**
     * Important: when using 'put', for getting the map, we need to use getMap
     */
    public <K, V> RMap<K, V> getMap(String mapName) {
        return redisson.getMap(mapName);
    }

    /**
     * Important: when using 'put', for retrieving the value, we need to use 'get'
     */
    public void put(String mapName, String key, Object value) {
        RMap<String, Object> map = getMap(mapName);
        map.put(key, value);
    }

    /**
     * Important: 'get' is to retrieve the value by 'put'
     */
    public Object get(String mapName, String key) {
        RMap<String, Object> map = getMap(mapName);
        return map.get(key);
    }

    public Object remove(String mapName, String key) {
        RMap<String, Object> map = getMap(mapName);
        return map.remove(key);
    }

    public void remove(String mapName) {
        RMap<String, Object> map = getMap(mapName);
        map.clear();
    }

    /**
     * Important: when using putWithTTL, for getting the map, we need to use getMapCache
     */
    public <K, V> RMapCache<K, V> getMapCache(String mapName) {
        return redisson.getMapCache(mapName);
    }

    /**
     * Important: when using putWithTTL, for retrieving the value, we need to use getFromMapCache
     */
    public void putWithTTL(String mapName, String key, Object value, long timeout, TimeUnit timeUnit) {
        RMapCache<String, Object> map = redisson.getMapCache(mapName);
        map.put(key, value, timeout, timeUnit);
    }

    /**
     * Important: getFromMapCache is to retrieve the value by putWithTTL
     */
    public Object getFromMapCache(String mapName, String key) {
        RMapCache<String, Object> mapCache = getMapCache(mapName);
        return mapCache.get(key);
    }

    public boolean isKeyLive(String mapName, String key) { // used for the keys which are put into map with ttl
        RMapCache<String, Object> map = getMapCache(mapName);
        return ! (map.remainTimeToLive(key) == -2) ;
    }

    public RLock getLock(String resourceName) {
        return redisson.getLock(resourceName);
    }

    public void unlock(RLock lock) {
        lock.unlock();
    }

    public void setAtomicLong(String key, long value) {
        RAtomicLong atomicLong = redisson.getAtomicLong(key);
        atomicLong.set(value);
    }

    public long getAtomicLong(String key) {
        RAtomicLong atomicLong = redisson.getAtomicLong(key);
        return atomicLong.get();
    }
}
