package org.beansugar.sample.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;

/**
 * @Author: archmagece
 * @Since: 2013-12-23 10:32
 */
@Slf4j
public class JedisPubSubLocal extends JedisPubSub {

	@Override
	public void onMessage(String channel, String message) {
		log.debug("onMessage");
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		log.debug("onPMessage");
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		log.debug("onSubscribe");
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		log.debug("onUnsubscribe");
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		log.debug("onPUnsubscribe");
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		log.debug("onPSubscribe");
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
