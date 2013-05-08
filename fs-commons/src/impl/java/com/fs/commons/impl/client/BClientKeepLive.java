/**
 *  Jan 29, 2013
 */
package com.fs.commons.impl.client;

import java.util.Timer;

import java.util.TimerTask;
import com.fs.commons.api.client.BClient;
/**
 * @author wuzhen
 * 
 */
public class BClientKeepLive implements BClient.KeepLiveI {

	private Timer timer;

	public BClientKeepLive() {
		this.timer = new Timer("bclient-keep-live-timer");
	}

	@Override
	public void scheduleKeepLiveTask(final TimerTask tt,int delay) {

		this.timer.schedule(tt, delay);
	}

}
