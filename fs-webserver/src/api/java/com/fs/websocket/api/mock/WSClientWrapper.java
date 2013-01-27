/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 26, 2013
 */
package com.fs.websocket.api.mock;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public class WSClientWrapper {

	protected WSClient target;

	protected String name;

	protected PropertiesI<Object> properties;

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

	public void init(PropertiesI<Object> pts) {
		this.properties = pts;
	}

	public WSClient getTarget() {
		return this.target;
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
	}

	public void addHandler(Path p, HandlerI<MessageContext> mh) {
		this.target.addHandler(p, mh);
	}

	public void addHandler(Path p, boolean strict, HandlerI<MessageContext> mh) {
		this.target.addHandler(p, strict, mh);
	}

	protected void onMessage(MessageContext msg) {

	}

}
