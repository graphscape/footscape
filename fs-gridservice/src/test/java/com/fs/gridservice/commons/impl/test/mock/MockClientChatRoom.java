/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.test.mock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;

/**
 * @author wu
 * 
 */
public class MockClientChatRoom {

	protected MockEventDriveClient client;

	protected String groupId;

	protected boolean joined;

	protected Set<String> participantIdSet;

	protected BlockingQueue<MessageI> messageQueue;

	public MockClientChatRoom(String groupId, MockEventDriveClient mc) {
		this.client = mc;
		this.groupId = groupId;
		this.participantIdSet = new HashSet<String>();
		this.messageQueue = new LinkedBlockingQueue<MessageI>();
		mc.addListener("/gchat/status/join", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatRoom.this.handleJoinMessage(t);
			}
		});
		mc.addListener("/gchat/message", new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				MockClientChatRoom.this.handleMessageMessage(t);
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
		msg.setPayload("chatGroupId", groupId);

		this.client.getClient().sendMessage(msg);

	}

	public void sendMessage(String text) throws Exception {
		MessageI msg = new MessageSupport();
		msg.setHeader("path", "/gchat/message");
		msg.setPayload("groupId", this.groupId);
		msg.setPayload("text", text);
		this.client.getClient().sendMessage(msg);

	}

}
