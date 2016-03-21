package org.beansugar.sample.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: archmagece
 * @Since: 2013-12-23 10:24
 */
public class RedisPublisher {

	// This should be a global singleton
	private final JedisPool jedisPool;

	public RedisPublisher(String host, int port, int timeout, String authKey){
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		jedisPool = new JedisPool(poolConfig, host, port, timeout, authKey);
	}

	ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

	public void publish(){
		newFixedThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Jedis jedis = jedisPool.getResource();
					try {
						jedis.publish("CC", new Date().toString());
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						jedisPool.returnResource(jedis);
					}
				}
			}
		});
	}
}
