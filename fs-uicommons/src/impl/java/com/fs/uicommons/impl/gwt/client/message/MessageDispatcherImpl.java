/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.message;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicommons.api.gwt.client.message.MessageHandlerI;
import com.fs.uicommons.api.gwt.client.message.support.CollectionHandler;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class MessageDispatcherImpl extends UiObjectSupport implements
		MessageDispatcherI {

	protected Map<Path, CollectionHandler<MessageData>> handlers;

	protected CollectionHandler<MessageData> defaultHandlers;

	public MessageDispatcherImpl(String name) {
		super(name);
		this.handlers = new HashMap<Path, CollectionHandler<MessageData>>();
		this.defaultHandlers = new CollectionHandler<MessageData>();

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(MessageData t) {
		logger.info("dispatcher:" + this.getName() + ",handle msg:" + t);
		String path = t.getHeader("path", true);
		Path p = Path.valueOf(path, '/');
		boolean match = false;
		for (Map.Entry<Path, CollectionHandler<MessageData>> en : this.handlers
				.entrySet()) {
			Path pi = en.getKey();

			if (pi.isSubPath(p, true)) {
				match = true;
				CollectionHandler<MessageData> h = en.getValue();
				h.handle(t);
			}

		}

		if (!match) {

			this.defaultHandlers.handle(t);
			if (this.defaultHandlers.size() == 0) {
				logger.info("path:" + path + " with msg:" + t
						+ " has no handler match it in dispatcher:"
						+ this.getName() + ",all handlers:" + this.handlers);
			}
		}

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void addHandler(Path path, MessageHandlerI mh) {
		CollectionHandler<MessageData> hs = this.handlers.get(path);
		if (hs == null) {
			hs = new CollectionHandler<MessageData>();
			this.handlers.put(path, hs);
		}

		hs.addHandler(mh);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void addDefaultHandler(MessageHandlerI mh) {
		this.defaultHandlers.addHandler(mh);
	}

}
