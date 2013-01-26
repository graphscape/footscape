/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 26, 2013
 */
package com.fs.websocket.api.mock;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;

/**
 * @author wu
 * 
 */
public class WsClientWrapper {

	protected WSClient target;
	
	protected String name;

	public WsClientWrapper(WSClient t) {
		this.target = t;
		this.name = t.getName();
		t.addHandler(Path.ROOT, new HandlerI<MessageContext>() {

			@Override
			public void handle(MessageContext sc) {
				WsClientWrapper.this.onMessage(sc);
			}
		});
	}

	public WSClient getTarget() {
		return this.target;
	}

	public void connect() {
		this.target.connect();
	}

	public void close() {
		this.target.close();
	}
	
	
	protected void sendMessage(MessageI msg){
		this.target.sendMessage(msg);
	}
	protected void onMessage(MessageContext msg) {

	}

}
