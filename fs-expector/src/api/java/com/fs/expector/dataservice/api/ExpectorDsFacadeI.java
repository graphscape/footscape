/**
 *  
 */
package com.fs.expector.dataservice.api;

import com.fs.expector.dataservice.api.wrapper.Account;

/**
 * @author wu
 * 
 */
public interface ExpectorDsFacadeI {

	public int getOverflowConnectedExpCount(String expId1);

	public int getConnectedExpCount(String expId);

	public String getIconByAccountId(String accId1);

	public Account getAccountByEmail(String email);

	public Account updatePassword(String aid, String pass);

}
