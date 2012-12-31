/**
 * 
 */
package com.fs.expector.gridservice.impl.test.signup;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.message.MessageContext;
import com.fs.expector.gridservice.api.TestHelperI;
import com.fs.expector.gridservice.api.signup.ConfirmCodeNotifierI;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wuzhen
 * 
 */
public class TestConfirmCodeNotifier extends ConfigurableSupport implements ConfirmCodeNotifierI {

	@Override
	public void notify(TerminalMsgReceiveEW mr, MessageContext hc, String email, String confirmCode) {
		System.out.println(TestConfirmCodeNotifier.class.getName() + ".notify,email:" + email
				+ ",confirmCode:" + confirmCode);
		TestHelperI th = this.container.find(TestHelperI.class);
		th.sendConfirmCode(email, confirmCode);

	}

}
