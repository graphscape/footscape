/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpps.api;

/**
 * @author wu Server 2 server connector
 */
public interface XmppSessionI {

	public void close();
	
	public XmppSessionI auth(String username, String password);

	public XmppSessionI bind(String res);

	// TODO FutureI
	public StanzaI createStanza(String name);

}
