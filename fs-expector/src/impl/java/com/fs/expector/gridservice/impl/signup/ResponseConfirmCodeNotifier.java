/**
 * 
 */
package com.fs.expector.gridservice.impl.signup;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.message.MessageContext;
import com.fs.expector.gridservice.api.signup.ConfirmCodeNotifierI;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wuzhen
 * 
 */
public class ResponseConfirmCodeNotifier extends ConfigurableSupport implements ConfirmCodeNotifierI {

	@Override
	public void notify(TerminalMsgReceiveEW mr, MessageContext hc, String email, String confirmCode) {

		hc.getResponse().setPayload("confirmCode", confirmCode);

	}

}
