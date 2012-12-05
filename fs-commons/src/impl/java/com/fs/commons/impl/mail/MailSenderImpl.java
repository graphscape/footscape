/**
 * Jul 25, 2012
 */
package com.fs.commons.impl.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.mail.MailSenderI;
import com.fs.commons.api.mail.MimeMessageWrapper;

/**
 * @author wu
 * 
 */
public class MailSenderImpl extends ConfigurableSupport implements MailSenderI {
	private JavaMailSenderImpl sender;

	/* */
	@Override
	public MimeMessageWrapper createMimeMessage() {
		MimeMessage mi = this.sender.createMimeMessage();
		MimeMessageWrapper rt = new MimeMessageImpl(mi);
		return rt;

	}

	/* */
	@Override
	public void send(MimeMessageWrapper mm) {
		this.sender.send(mm.getTarget());//
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		sender = new JavaMailSenderImpl();
		String host = this.config.getProperty("host", true);
		sender.setHost(host);

	}

}
