package org.beansugar.sample.redis;

import redis.clients.jedis.Jedis;

/**
 * @Author: archmagece
 * @Since: 2013-12-23 10:29
 */
public class RedisSubscriber {

	private final String host;
	private final String auth;
	private final String channelName;

	public RedisSubscriber(){
		this("localhost", null, "test");
	}
	public RedisSubscriber(String host){
		this(host, null, "test");
	}

	public RedisSubscriber(String host, String auth){
		this(host, auth, "test");
	}

	public RedisSubscriber(String host, String auth, String channelName){
		this.host = host;
		this.auth = auth;
		this.channelName = channelName;
	}

	public void subscriber() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Jedis subscriberJedis = new Jedis(host);
				subscriberJedis.auth(auth);
				try {
					subscriberJedis.subscribe(new JedisPubSubLocal(), channelName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
