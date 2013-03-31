/**
 * Jul 25, 2012
 */
package com.fs.commons.impl.mail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.mail.MimeMessageWrapper;

/**
 * @author wu
 * 
 */
public class MimeMessageImpl extends MimeMessageWrapper {
	private MimeMessageHelper helper;

	/** */
	public MimeMessageImpl(MimeMessage mi) {
		super.target = mi;
		this.helper = new MimeMessageHelper(mi);
	}

	/* */
	@Override
	public MimeMessageWrapper setTo(String to) {
		try {
			this.helper.setTo(to);
		} catch (MessagingException e) {
			throw new FsException(e);
		}
		return this;

	}

	protected InternetAddress address(String add) {
		InternetAddress rt;
		try {
			rt = new InternetAddress(add);
		} catch (AddressException e) {
			throw new FsException(e);
		}

		return rt;
	}

	/* */
	@Override
	public MimeMessageWrapper setFrom(String from) {
		try {
			this.helper.setFrom(from);
		} catch (MessagingException e) {
			throw new FsException(e);
		}
		return this;

	}

	/* */
	@Override
	public MimeMessageWrapper setText(String text) {
		return this.setText(text, false);
	}

	@Override
	public MimeMessageWrapper setText(String text, boolean isHtml) {
		try {
			this.helper.setText(text, isHtml);
		} catch (MessagingException e) {
			throw new FsException(e);
		}
		return this;

	}

	@Override
	public String getContentAsText() {
		try {
			Object ctx = this.helper.getMimeMessage().getContent();
			return ctx.toString();
		} catch (IOException e) {
			throw new FsException(e);
		} catch (MessagingException e) {
			throw new FsException(e);
		}

	}

	/*
	 * Mar 31, 2013
	 */
	@Override
	public MimeMessageWrapper setSubject(String subject) {
		try {
			this.target.setSubject(subject);
		} catch (MessagingException e) {
			throw new FsException(e);
		}
		return this;
	}

}
