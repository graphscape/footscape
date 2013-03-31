/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 31, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk.password;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface PasswordResetViewI extends ViewI {

	public static final String FK_PFID = "pfId";

	public static final String FK_NEWPASSWORD = "newPassword";

	public void setPfId(String pfId);

	public String getPfId();
	
	public String getNewPassword();
	
}
