/**
 * Jul 25, 2012
 */
package com.fs.commons.api.mail;

import javax.mail.internet.MimeMessage;

/**
 * @author wu
 * 
 */
public abstract class MimeMessageWrapper {

	protected MimeMessage target;

	public abstract MimeMessageWrapper setTo(String to);

	public abstract MimeMessageWrapper setFrom(String from);

	public abstract MimeMessageWrapper setText(String text);

	public abstract MimeMessageWrapper setText(String text, boolean isHtml);

	public abstract String getContentAsText();
	
	public abstract MimeMessageWrapper setSubject(String subject);

	public MimeMessage getTarget() {
		return target;
	}

}
