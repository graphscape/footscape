/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.test.mock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;

/**
 * @author wu
 * 
 */
public class MockClientChatGroup {

	protected MockEventDriveClient client;

	protected String groupId;

	protected boolean joined;

	protected Set<String> participantIdSet;

	protected BlockingQueue<MessageI> messageQueue;

	public MockClientChatGroup(String groupId, MockEventDriveClient mc) {
		this.client = mc;
		this.groupId = groupId;
		this.participantIdSet = new HashSet<String>();
		this.messageQueue = new LinkedBlockingQueue<MessageI>();
		mc.addListener("/gchat/status/join", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatGroup.this.handleJoinMessage(t);
			}
		});
		mc.addListener("/gchat/message", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatGroup.this.handleMessageMessage(t);
			}
		});

	}

	protected void handleJoinMessage(MessageI msg) {
		//
		String pid = (String) msg.getPayload("participantId", true);
		this.participantIdSet.add(pid);//
		// is me?
		if (ObjectUtil.nullSafeEquals(pid, this.client.getClient().getAccountId())) {
			this.joined = true;
		}

	}

	protected void handleMessageMessage(MessageI msg) {
		//
		String pid = (String) msg.getPayload("participantId", true);
		String text = (String) msg.getPayload("text", true);

		System.out.println("pid:" + pid + ",say:" + text);
		try {
			this.messageQueue.put(msg);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

	}

	public void join() throws Exception {
		MessageI msg = new MessageSupport();
		msg.setHeader("path", "/gchat/join");
		msg.setHeader("groupId", groupId);

		this.client.getClient().sendMessage(msg);

	}

	public void sendMessage(String text) throws Exception {
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

}
