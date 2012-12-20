/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.test.mock.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.gridservice.commons.impl.test.mock.MockEventDriveClient;

/**
 * @author wu
 * 
 */
public class MockClientChatGroup {

	private static final Logger LOG = LoggerFactory
			.getLogger(MockClientChatGroup.class);

	protected MockEventDriveClient client;

	protected String groupId;

	protected String myParticipantId;

	protected Map<String, MockParticipant> participantMap;

	protected BlockingQueue<MessageI> messageQueue;

	protected BlockingQueue<MockParticipant> joinQueue;

	protected BlockingQueue<MockParticipant> exitQueue;

	protected Semaphore joinWait;

	protected Semaphore exitWait;

	public MockClientChatGroup(String groupId, MockEventDriveClient mc) {
		this.client = mc;
		this.groupId = groupId;
		this.participantMap = new HashMap<String, MockParticipant>();
		this.exitQueue = new LinkedBlockingQueue<MockParticipant>();
		this.joinQueue = new LinkedBlockingQueue<MockParticipant>();
		this.messageQueue = new LinkedBlockingQueue<MessageI>();
		mc.addListener("/gchat/join", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatGroup.this.handleJoinMessage(t);
			}
		});
		mc.addListener("/gchat/exit", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatGroup.this.handleExitMessage(t);
			}
		});
		mc.addListener("/gchat/exit/success", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatGroup.this.handleExitMessage(t);
			}
		});

		mc.addListener("/gchat/message", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatGroup.this.handleMessageMessage(t);
			}
		});

	}

	public BlockingQueue<MockParticipant> getExitQueue() {
		return exitQueue;
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

	public String getAccId() {
		return this.client.getClient().getAccountId();//
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
		this.joinQueue.add(mp);//
		LOG.info(this.getAccId() + ",participant join:" + mp
				+ ",now all participants:" + this.participantMap);
		// is me?
		if (ObjectUtil.nullSafeEquals(aid, this.client.getClient()
				.getAccountId())) {
			this.myParticipantId = pid;
			this.joinWait.release();
		}

	}

	public MockParticipant waitNewExit(long timeout, TimeUnit unit) {
		MockParticipant rt;
		try {
			rt = this.exitQueue.poll(timeout, unit);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
		if (rt == null) {
			throw new FsException("timeout for waiting exit");
		}
		return rt;

	}

	public MockParticipant waitNewJoin(long timeout, TimeUnit unit) {
		MockParticipant rt;
		try {
			rt = this.joinQueue.poll(timeout, unit);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
		if (rt == null) {
			throw new FsException("timeout for waiting new join");
		}
		return rt;

	}

	public BlockingQueue<MockParticipant> getJoinQueue() {
		return joinQueue;
	}

	protected void handleExitMessage(MessageI msg0) {
		// nested.
		String path = msg0.getHeader("path");
		if (path.endsWith("exit/success")) {
			// is me?
			this.myParticipantId = null;//
			this.exitWait.release();
			return;
		}
		String gid = msg0.getHeader("groupId", true);

		MessageI msg = (MessageI) msg0.getPayload("message", true);//

		String pid = (String) msg.getPayload("participantId", true);

		MockParticipant mp = this.participantMap.remove(pid);//
		if (mp == null) {
			throw new FsException("no this participant:" + pid + " for client:"
					+ this.client.getClient().getAccountId()
					+ " all participant:" + this.participantMap);
		}
		this.exitQueue.add(mp);//
	}

	protected void handleMessageMessage(MessageI msg0) {
		//
		String gid = msg0.getHeader("groupId", true);
		String pid = msg0.getHeader("participantId");
		if(pid == null){
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

	public Future<Boolean> join() throws Exception {

		MessageI msg = new MessageSupport();
		msg.setHeader("path", "/gchat/join");
		msg.setHeader("groupId", groupId);

		this.joinWait = new Semaphore(0);
		final FutureTask<Boolean> rt = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// TODO Auto-generated method stub
						MockClientChatGroup.this.joinWait.acquire();
						return true;
					}
				});
		new Thread() {
			@Override
			public void run() {
				rt.run();
			}
		}.start();

		this.client.getClient().sendMessage(msg);
		return rt;

	}

	public Future<Boolean> exit() throws Exception {

		MessageI msg = new MessageSupport();
		msg.setHeader("path", "/gchat/exit");
		msg.setHeader("groupId", groupId);

		this.exitWait = new Semaphore(0);
		final FutureTask<Boolean> rt = new FutureTask<Boolean>(
				new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						// TODO Auto-generated method stub
						MockClientChatGroup.this.exitWait.acquire();
						return true;
					}
				});
		new Thread() {
			@Override
			public void run() {
				rt.run();
			}
		}.start();

		this.client.getClient().sendMessage(msg);
		return rt;

	}

	public void sendMessage(String text) throws Exception {
		if (!this.isJoined()) {
			throw new FsException("you have not join the group:" + this.groupId);
		}
		MessageI msg = new MessageSupport();
		msg.setHeader("path", "/gchat/message");
		msg.setHeader("format", "message");
		msg.setHeader("groupId", this.groupId);

		MessageI msg2 = new MessageSupport();
		msg2.setHeader("format", "text");//
		msg2.setPayload("text", text);

		msg.setPayload("message", msg2);

		this.client.getClient().sendMessage(msg);

	}

	/**
	 * Dec 19, 2012
	 */
	public MessageI receiveMessage(long timeout, TimeUnit unit) {
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

}
