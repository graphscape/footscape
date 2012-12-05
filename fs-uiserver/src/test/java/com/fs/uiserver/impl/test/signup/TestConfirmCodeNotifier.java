/**
 * 
 */
package com.fs.uiserver.impl.test.signup;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.engine.api.HandleContextI;
import com.fs.uiserver.TestHelperI;
import com.fs.uiserver.api.signup.ConfirmCodeNotifierI;

/**
 * @author wuzhen
 * 
 */
public class TestConfirmCodeNotifier extends ConfigurableSupport implements
		ConfirmCodeNotifierI {

	@Override
	public void notify(HandleContextI hc, String email, String confirmCode) {
		System.out.println(TestConfirmCodeNotifier.class.getName()
				+ ".notify,email:" + email + ",confirmCode:" + confirmCode);
		TestHelperI th = this.container.find(TestHelperI.class);
		th.sendConfirmCode(email, confirmCode);

	}

}
