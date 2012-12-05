/**
 * 
 */
package com.fs.uiserver.impl.signup;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.mail.MailSenderI;
import com.fs.commons.api.mail.MimeMessageWrapper;
import com.fs.engine.api.HandleContextI;
import com.fs.uiserver.api.signup.ConfirmCodeNotifierI;

/**
 * @author wuzhen
 * 
 */
public class EmailConfirmCodeNotifier extends ConfigurableSupport implements ConfirmCodeNotifierI {

	@Override
	public void notify(HandleContextI hc, String email, String confirmCode) {
		// TODO background worker to send mail/sms.
		// TODO send a message for background worker
		MailSenderI ms = this.container.find(MailSenderI.class, true);
		MimeMessageWrapper mw = ms.createMimeMessage();
		mw.setFrom("signup@footsight.com");// TODO config
		mw.setTo(email);
		mw.setText("confirm code: " + confirmCode
				+ " \nCopy the confirm code and continue signup process.");
		// generate confirm code
		ms.send(mw);//
	}

}
