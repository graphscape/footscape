/**
 *  
 */
package com.fs.webcomet.api;

import com.fs.commons.api.context.ContextI;

/**
 * @author wu
 * 
 */
public interface CometI extends ContextI {
	
	public String getProtocol();
	
	public String getId();

	public void sendMessage(String msg);

	public void addListener(CometListenerI ln);
}
