/**
 * 
 */
package com.fs.uiserver.api.signup;

import com.fs.engine.api.HandleContextI;

/**
 * @author wuzhen
 * 
 */
public interface ConfirmCodeNotifierI {

	public void notify(HandleContextI hc, String email, String confirmCode);

}
