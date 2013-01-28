/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.test.benchmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageHandlerI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.mock.MockClientWrapper;
import com.fs.gridservice.commons.impl.test.mock.chat.MockParticipant;
import com.fs.websocket.api.mock.WSClient;

/**
 * @author wu
 * 
 */
public class GChatClientWrapper extends MockClientWrapper {

	private static final Logger LOG = LoggerFactory.getLogger(GChatClientWrapper.class);

	public static final String JOIN_AT_CONNECT = "joinAtConnect";

	public static final String GROUPID = "groupId";

	protected String myParticipantId;

	protected Map<String, MockParticipant> participantMap;

	protected BlockingQueue<MessageI> messageQueue;

	protected BlockingQueue<MockParticipant> joinQueue;

	protected BlockingQueue<MockParticipant> exitQueue;

	protected Semaphore joinWait;

	protected Semaphore exitWait;

	public GChatClientWrapper(WSClient target) {
		super(target);

		this.participantMap = new HashMap<String, MockParticipant>();
		this.messageQueue = new LinkedBlockingQueue<MessageI>();
		this.joinQueue = new LinkedBlockingQueue<MockParticipant>();
		this.exitQueue = new LinkedBlockingQueue<MockParticipant>();

		this.addHandler(Path.valueOf("/gchat/join"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext t) {
				GChatClientWrapper.this.handleJoinMessage(t.getRequest());
			}
		});
		this.addHandler(Path.valueOf("/gchat/exit"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext t) {
				GChatClientWrapper.this.handleExitMessage(t.getRequest());
			}
		});
		this.addHandler(Path.valueOf("/gchat/exit/success"), new MessageHandlerI() {

			@Override
			public void handle(MessageContext t) {
				GChatClientWrapper.this.handleExitSuccessMessage(t.getRequest());
			}
		});

		this.addHandler(Path.valueOf("/gchat/message"), true,new MessageHandlerI() {

			@Override
			public void handle(MessageContext t) {
				GChatClientWrapper.this.handleMessageMessage(t.getRequest());
			}
		});

	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public GChatClientWrapper connect() {
		super.connect();

		if (this.properties.getPropertyAsBoolean(JOIN_AT_CONNECT, false)) {
			this.join();
		}
		return this;
	}

	public String getGroupId() {
		return (String) this.properties.getProperty(GROUPID, true);
	}

	public Map<String, MockParticipant> participantIdSet() {
		return this.participantMap;
	}

	public boolean containsAccountId(String accId) {
		return !this.getParticipantByAccountId(accId).isEmpty();
	}

	public List<MockParticipant> getParticipantByAccountId(String accId) {
		List<MockParticipant> rt = new ArrayList<MockParticipant>();
		for (MockParticipant p : this.participantMap.values()) {
			if (accId.equals(p.getAccountId())) {
				rt.add(p);
			}
		}
		return rt;
	}

	protected void handleJoinMessage(MessageI msg0) {
		// nested.
		String gid = msg0.getHeader("groupId", true);

		MessageI msg = (MessageI) msg0.getPayload("message", true);//

		String pid = (String) msg.getPayload("participantId", true);
		String aid = msg.getString("accountId", true);//
		String role = msg.getString("role", true);//
		String tid = msg.getString("terminalId", true);//

		MockParticipant mp = new MockParticipant();
		mp.setAccountId(aid);
		mp.setId(pid);
		this.participantMap.put(pid, mp);//
		LOG.info(this.getAccountId() + ",participant join:" + mp + ",now all participants:"
				+ this.participantMap);
		// is me?
		if (ObjectUtil.nullSafeEquals(aid, this.getAccountId())) {
			this.myParticipantId = pid;
			this.joinWait.release();
		}
		this.joinQueue.offer(mp);//

	}

	protected void handleExitSuccessMessage(MessageI msg0) {
		//TODO String gid = msg0.getHeader("groupId", true);
		this.myParticipantId = null;//
		this.exitWait.release();
	}

	protected void handleExitMessage(MessageI msg0) {
		// nested.
		String path = msg0.getHeader(MessageI.HK_PATH);
		String gid = msg0.getHeader("groupId", true);

		MessageI msg = (MessageI) msg0.getPayload("message", true);//

		String pid = (String) msg.getPayload("participantId", true);

		MockParticipant mp = this.participantMap.remove(pid);//
		if (mp == null) {
			throw new FsException("no this participant:" + pid + " for client:" + this.getAccountId()
					+ " all participant:" + this.participantMap);
		}
		this.exitQueue.offer(mp);
	}

	protected void handleMessageMessage(MessageI msg0) {
		//
		String gid = msg0.getHeader("groupId", true);
		String pid = msg0.getHeader("participantId");
		if (pid == null) {
			throw new FsException("BUG.");
		}
		MessageI msg = (MessageI) msg0.getPayload("message", true);//
		String text = (String) msg.getPayload("text", true);

		System.out.println("pid:" + pid + ",say:" + text);
		try {
			this.messageQueue.put(msg);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

	}

	public void sendChatMessage(String text) {
		if (!this.isJoined()) {
			throw new FsException("you have not join the group:" + this.getGroupId());
		}
		MessageI msg = new MessageSupport();
		msg.setHeader(MessageI.HK_PATH, "/gchat/message");
		msg.setHeader("format", "message");
		msg.setHeader("groupId", this.getGroupId());

		MessageI msg2 = new MessageSupport();
		msg2.setHeader("format", "text");//
		msg2.setPayload("text", text);

		msg.setPayload("message", msg2);

		this.sendMessage(msg);

	}

	public List<MessageI> drainMessages() {
		List<MessageI> rt = new ArrayList<MessageI>();
		this.messageQueue.drainTo(rt);
		return rt;
	}

	/**
	 * Dec 19, 2012
	 */
	public MessageI acquireNextMessage(long timeout, TimeUnit unit) {
		MessageI rt = this.tryAcquireNextMessage(timeout, unit);
		if (rt == null) {
			throw new FsException("timeout for next message");
		}
		return rt;

	}

	public MessageI tryAcquireNextMessage(long timeout, TimeUnit unit) {
		try {
			return this.messageQueue.poll(timeout, unit);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
	}

	/**
	 * @return
	 */
	public boolean isJoined() {
		return this.myParticipantId != null;
	}

	/**
	 * @return
	 */
	public List<String> getAccIdList() {
		List<String> rt = new ArrayList<String>();
		for (MockParticipant p : this.participantMap.values()) {
			rt.add(p.getAccountId());
		}
		return rt;
	}

	public void join() {
		MessageI msg = new MessageSupport();
		msg.setHeader(MessageI.HK_PATH, "/gchat/join");
		msg.setHeader("groupId", this.getGroupId());

		this.joinWait = new Semaphore(0);

		this.sendMessage(msg);
		try {
			if (!this.joinWait.tryAcquire(10, TimeUnit.SECONDS)) {
				throw new FsException("timeout to wait join,gid:" + this.getGroupId());
			}
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public GChatClientWrapper close() {
		//

		this.tryExit();
		super.close();
		return this;
	}

	private void tryExit() {

		if (this.myParticipantId == null) {
			LOG.warn("already exit or not join.");
		}

		this.exit();

	}

	public void exit() {

		MessageI msg = new MessageSupport();
		msg.setHeader(MessageI.HK_PATH, "/gchat/exit");
		msg.setHeader("groupId", this.getGroupId());

		this.exitWait = new Semaphore(0);

		this.sendMessage(msg);
		try {
			if (!this.exitWait.tryAcquire(10, TimeUnit.SECONDS)) {
				throw new FsException("timeout to wait join,gid:" + this.getGroupId());
			}
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
	}

	public MockParticipant acquireNextJoin(int timeout, TimeUnit unit) {
		try {
			MockParticipant rt = this.joinQueue.poll(timeout, unit);
			if (rt == null) {
				throw new FsException("timeout to wait next join");
			}
			return rt;
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

	}

	public MockParticipant acquireNextExit(int timeout, TimeUnit unit) {
		try {
			MockParticipant rt = this.exitQueue.poll(timeout, unit);
			if (rt == null) {
				throw new FsException("timeout to wait next exit");
			}
			return rt;
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

	}

}
