/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.xmpps.api;

import com.fs.commons.api.callback.CallbackI;

/**
 * @author wu
 * 
 */
public interface XmppI {
	
	public XmppSessionI open();

	public <T> T execute(CallbackI<XmppSessionI, T> cc);

	public void register(String username, String password);

}
