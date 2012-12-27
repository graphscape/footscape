/**
 * 
 */
package com.fs.expector.gridservice.impl.signup;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.engine.api.HandleContextI;
import com.fs.expector.gridservice.api.signup.ConfirmCodeNotifierI;

/**
 * @author wuzhen
 * 
 */
public class ResponseConfirmCodeNotifier extends ConfigurableSupport implements
		ConfirmCodeNotifierI {

	@Override
	public void notify(HandleContextI hc, String email, String confirmCode) {

		hc.getResponse().setPayload("confirmCode", confirmCode);

	}

}
