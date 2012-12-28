/**
 * 
 */
package com.fs.expector.gridservice.api.signup;

import com.fs.engine.api.HandleContextI;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wuzhen
 * 
 */
public interface ConfirmCodeNotifierI {

	public void notify(TerminalMsgReceiveEW req,HandleContextI hc, String email, String confirmCode);

}
