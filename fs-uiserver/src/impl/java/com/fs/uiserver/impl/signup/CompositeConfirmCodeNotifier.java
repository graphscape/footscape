/**
 * 
 */
package com.fs.uiserver.impl.signup;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.engine.api.HandleContextI;
import com.fs.uiserver.api.signup.ConfirmCodeNotifierI;

/**
 * @author wuzhen
 * 
 */
public class CompositeConfirmCodeNotifier extends ConfigurableSupport implements
		ConfirmCodeNotifierI {

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

	}

	@Override
	public void notify(HandleContextI hc, String email, String confirmCode) {

		String ccnN = (String) hc.getRequest()
				.getPayload("confirmCodeNotifier");
		ConfirmCodeNotifierI ccn = this.container
				.finder(ConfirmCodeNotifierI.class).name(ccnN).find(false);
		
		if (ccn == null) {
			hc.getResponse()
					.getErrorInfos()
					.add(new ErrorInfo("payloads.property['confirmCodeNotifier']",
							"no this confirm code notifier:" + ccnN));
			return;
		}
		ccn.notify(hc, email, confirmCode);

	}

}
