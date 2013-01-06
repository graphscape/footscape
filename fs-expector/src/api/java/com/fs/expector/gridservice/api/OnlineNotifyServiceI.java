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
}
