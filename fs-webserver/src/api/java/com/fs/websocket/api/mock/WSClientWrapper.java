/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 26, 2013
 */
package com.fs.websocket.api.mock;

import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public class WSClientWrapper {

	public static interface KeepLiveI {

		public static Path PATH = Path.valueOf("/control/keepLive");

		public void scheduleKeepLiveTask(TimerTask tt, int delay);

	}

	protected WSClient target;

	protected String name;

	protected PropertiesI<Object> properties;

	private ReentrantLock syncSendLock = new ReentrantLock();

	protected BlockingQueue<MessageI> syncResponseQueue;

	protected KeepLiveI keepLive;

	protected TimerTask keepLiveTask;

	protected int keepLiveTime = 20 * 1000;

	public WSClientWrapper(WSClient t) {
		this.target = t;
		this.name = t.getName();

		t.addHandler(Path.ROOT, new HandlerI<MessageContext>() {

			@Override
			public void handle(MessageContext sc) {
				WSClientWrapper.this.onMessage(sc);
			}
		});
	}

	public MessageI syncSendMessage(MessageI req) {
		this.syncSendLock.lock();
		try {
			this.syncResponseQueue = new LinkedBlockingQueue<MessageI>();
			this.sendMessage(req);
			MessageI rt = this.syncResponseQueue.poll(10, TimeUnit.SECONDS);
			if (rt == null) {
				throw new FsException("timeout for waiting response for req:" + req
						+ ",please check idleTime:" + this.target.getIdleTime());
			}
			this.syncResponseQueue = null;
			return rt;

		} catch (InterruptedException e) {
			throw new FsException(e);
		} finally {
			this.syncSendLock.unlock();
		}
	}

	public int getIdleTime() {
		return this.target.getIdleTime();
	}

	public void init(PropertiesI<Object> pts) {
		this.properties = pts;
	}

	public WSClient getTarget() {
		return this.target;
	}

	public void keepLive(KeepLiveI kl) {
		this.keepLive = kl;
	}

	public WSClientWrapper connect() {
		this.target.connect();
		return this;
	}

	public WSClientWrapper close() {
		this.target.close();
		return this;
	}

	public void sendMessage(MessageI msg) {
		this.target.sendMessage(msg);
		if (this.keepLive == null) {
			return;
		}
		if (this.keepLiveTask != null) {
			this.keepLiveTask.cancel();
		}

		this.keepLiveTask = new TimerTask() {

			@Override
			public void run() {
				WSClientWrapper.this.sendKeepLiveMessage();
			}
		};

		this.keepLive.scheduleKeepLiveTask(this.keepLiveTask, this.keepLiveTime);
	}

	protected void sendKeepLiveMessage() {
		MessageI rt = new MessageSupport(KeepLiveI.PATH.toString());// not
																	// response?
		rt.setHeader("silence", "true");
		this.sendMessage(rt);
	}

	public void addHandler(Path p, HandlerI<MessageContext> mh) {
		this.target.addHandler(p, mh);
	}

	public void addHandler(Path p, boolean strict, HandlerI<MessageContext> mh) {
		this.target.addHandler(p, strict, mh);
	}

	private final void onMessage(MessageContext msg) {

		BlockingQueue<MessageI> que = syncResponseQueue;
		if (que == null) {
			return;
		}
		MessageI res = msg.getRequest();
		try {
			que.put(res);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

	}

}
