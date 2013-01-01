/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicore.impl.gwt.client.message;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageException;
import com.fs.uicore.api.gwt.client.message.MessageExceptionHandlerI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.CollectionHandler;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class MessageDispatcherImpl extends UiObjectSupport implements MessageDispatcherI {

	protected List<HandlerEntry> handlers;

	protected CollectionHandler<MessageData> defaultHandlers;

	protected CollectionHandler<MessageException> exceptionHandlers;

	public MessageDispatcherImpl(String name) {
		super(name);
		this.exceptionHandlers = new CollectionHandler<MessageException>();
		this.handlers = new ArrayList<HandlerEntry>();
		this.defaultHandlers = new CollectionHandler<MessageData>();

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(MessageData msg) {
		try {
			this.handleInternal(msg);
		} catch (Throwable t) {

			MessageException me = new MessageException(t, msg);
			if (this.exceptionHandlers.size() == 0) {
				logger.error("exception got when dispatch message:" + msg, t);
			} else {
				try {
					this.exceptionHandlers.handle(me);

				} catch (Throwable e2) {
					logger.error("exception's exception", e2);
				} finally {

				}
			}
		}
	}

	protected void handleInternal(MessageData t) {
		logger.info("dispatcher:" + this.getName() + ",handle msg:" + t);
		Path p = t.getPath();
		boolean match = false;
		List<HandlerEntry> hls = new ArrayList<HandlerEntry>(this.handlers);
		for (HandlerEntry he : hls) {
			match = he.tryHandle(p, t);
		}

		if (!match) {

			this.defaultHandlers.handle(t);
			if (this.defaultHandlers.size() == 0) {
				logger.info("path:" + p + " with msg:" + t + " has no handler match it in dispatcher:"
						+ this.getName() + ",all handlers:" + hls);
			}
		}

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void addHandler(Path path, MessageHandlerI mh) {
		this.addHandler(path, false, mh);
	}

	@Override
	public void addHandler(Path path, boolean strict, MessageHandlerI mh) {

		HandlerEntry he = new HandlerEntry(path, strict, mh);

		this.handlers.add(he);

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void addDefaultHandler(MessageHandlerI mh) {
		this.defaultHandlers.addHandler(mh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.message.MessageDispatcherI#
	 * addExceptionHandler
	 * (com.fs.uicommons.api.gwt.client.message.MessageExceptionHandlerI)
	 */
	@Override
	public void addExceptionHandler(MessageExceptionHandlerI eh) {
		this.exceptionHandlers.addHandler(eh);
	}

}
