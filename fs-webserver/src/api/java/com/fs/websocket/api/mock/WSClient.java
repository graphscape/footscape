/**
 *  Dec 28, 2012
 */
package com.fs.websocket.api.mock;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;

/**
 * @author wuzhen TODO move to web socket
 */
public abstract class WSClient {

	public abstract String getName();

	public abstract WSClient connect();

	public abstract void addHandler(Path p, HandlerI<MessageContext> mh);

	public abstract void addHandler(Path p, boolean strict, HandlerI<MessageContext> mh);

	public abstract void sendMessage(MessageI msg);

	public abstract void close();

}
