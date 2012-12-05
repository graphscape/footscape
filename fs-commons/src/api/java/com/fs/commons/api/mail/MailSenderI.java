/**
 * Jul 24, 2012
 */
package com.fs.commons.api.mail;

/**
 * @author wu
 * 
 */
public interface MailSenderI {

	public MimeMessageWrapper createMimeMessage();

	public void send(MimeMessageWrapper mm);

}
