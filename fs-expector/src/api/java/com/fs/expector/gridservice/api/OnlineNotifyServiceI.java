/**
 *  Jan 6, 2013
 */
package com.fs.expector.gridservice.api;

import com.fs.commons.api.message.MessageI;

/**
 * @author wuzhen
 * 
 */
public interface OnlineNotifyServiceI {

	public void tryNotifyAccount(String accId, MessageI msg);

	public void tryNotifyAccount(String accId, String path);
	
	public void tryNotifyExpMessageCreated(String accId2, String expId1, String expId2);
}
