/**
 * 
 */
package com.fs.expector.gridservice.impl.test.signup;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.engine.api.HandleContextI;
import com.fs.expector.gridservice.api.TestHelperI;
import com.fs.expector.gridservice.api.signup.ConfirmCodeNotifierI;

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
