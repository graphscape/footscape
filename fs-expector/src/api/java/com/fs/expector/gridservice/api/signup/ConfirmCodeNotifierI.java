/**
 * 
 */
package com.fs.expector.gridservice.api.signup;

import com.fs.commons.api.message.MessageContext;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wuzhen
 * 
 */
public interface ConfirmCodeNotifierI {

	public void notify(TerminalMsgReceiveEW req,MessageContext hc, String email, String confirmCode);

}
