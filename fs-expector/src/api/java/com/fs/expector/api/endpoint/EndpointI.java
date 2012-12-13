/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.api.endpoint;

import com.fs.commons.api.message.MessageI;
import com.fs.expector.api.MemberI;

/**
 * @author wu
 * 
 */
public interface EndpointI extends MemberI {

	public void sendMessage(MessageI msg);

	// public void addListener();

}
