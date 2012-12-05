/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver;

/**
 * @author wu
 * 
 */
public interface TestHelperI {

	public void sendConfirmCode(String email, String cc);

	public String getConfirmCode(String email, boolean force);

}
