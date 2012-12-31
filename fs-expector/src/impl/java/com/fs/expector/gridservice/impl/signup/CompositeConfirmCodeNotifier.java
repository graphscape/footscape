/**
 * 
 */
package com.fs.expector.gridservice.impl.signup;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.expector.gridservice.api.signup.ConfirmCodeNotifierI;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wuzhen
 * 
 */
public class CompositeConfirmCodeNotifier extends ConfigurableSupport implements ConfirmCodeNotifierI {

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

	}

	@Override
	public void notify(TerminalMsgReceiveEW req, MessageContext hc, String email, String confirmCode) {

		String ccnN = (String) req.getMessage().getPayload("confirmCodeNotifier");
		ConfirmCodeNotifierI ccn = this.container.finder(ConfirmCodeNotifierI.class).name(ccnN).find(false);

		if (ccn == null) {
			hc.getResponse()
					.getErrorInfos()
					.add(new ErrorInfo("payloads.property['confirmCodeNotifier']",
							"no this confirm code notifier:" + ccnN));
			return;
		}
		ccn.notify(req, hc, email, confirmCode);

	}

}
