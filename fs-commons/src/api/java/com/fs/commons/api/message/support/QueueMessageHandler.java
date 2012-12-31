/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.message.support;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageHandlerI;

/**
 * @author wuzhen
 * 
 */
public class QueueMessageHandler implements MessageHandlerI {

	private BlockingQueue<MessageContext> queue = new LinkedBlockingQueue<MessageContext>();

	@Override
	public void handle(MessageContext sc) {
		this.queue.offer(sc);
	}

	public BlockingQueue<MessageContext> getQueue() {
		return this.queue;
	}

	public MessageContext poll(long timeout) {
		try {
			return this.queue.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
	}

	public MessageContext take() {
		try {
			return this.queue.take();
		} catch (InterruptedException e) {
			throw new FsException();
		}
	}
}
