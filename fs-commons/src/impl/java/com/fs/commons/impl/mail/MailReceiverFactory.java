/**
 * Jul 26, 2012
 */
package com.fs.commons.impl.mail;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.mail.MailReceiverI;

/**
 * @author wu
 * 
 */
public class MailReceiverFactory extends ConfigurableSupport implements
		MailReceiverI.FactoryI {

	/* */
	@Override
	public MailReceiverI create(String server, String user, String password) {

		return new IMAPMailReceiverImpl(server, user, password);

	}

}
