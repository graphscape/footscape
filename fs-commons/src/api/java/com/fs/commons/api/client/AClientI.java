/**
 *  
 */
package com.fs.commons.api.client;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;

/**
 * @author wu
 * 
 */
public interface AClientI {

	public String getName();

	public AClientI connect();

	public void addHandler(Path p, HandlerI<MessageContext> mh);

	public void addHandler(Path p, boolean strict, HandlerI<MessageContext> mh);

	public void sendMessage(MessageI msg);

	public void close();

	public int getIdleTime();

}
