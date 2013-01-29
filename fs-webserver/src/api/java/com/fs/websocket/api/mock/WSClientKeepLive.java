/**
 *  Jan 29, 2013
 */
package com.fs.websocket.api.mock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wuzhen
 * 
 */
public class WSClientKeepLive implements WSClientWrapper.KeepLiveI {

	private Timer timer;

	public WSClientKeepLive() {
		this.timer = new Timer("ws-client-keep-live-timer");
	}

	@Override
	public void scheduleKeepLiveTask(final TimerTask tt,int delay) {

		this.timer.schedule(tt, delay);
	}

}
