/**
 * Jul 27, 2012
 */
package com.fs.uiserver.impl.test.helper;

import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.mail.MailReceiverI;
import com.fs.commons.api.mail.MailReceiverI.MessageContext;

/**
 * @author wu
 * @see TemplateHelper.
 */
public class MailHelper {

	private String receiveConfirmCode(long delay, ContainerI c, String server,
			String user, String pass) {
		try {
			System.out.println("sleeping:" + delay + "ms");
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
		MailReceiverI.FactoryI rf = c.find(MailReceiverI.FactoryI.class, true);
		final MailReceiverI mr = rf.create(server, user, pass);
		// get the last message
		List<String> tL = mr.processEachMessage("INBOX",
				new CallbackI<MessageContext, String>() {

					@Override
					public String execute(MessageContext i) {
						String rt = i.getMessageWrapper().getContentAsText();

						return rt;

					}
				});
		if (tL.size() == 0) {
			throw new FsException("no mail message received in INBOX of user:"
					+ user + ",pass:" + pass + ",server:" + server + " ");
		}
		String msg = tL.get(tL.size() - 1);
		int idx = msg.indexOf("confirm code:");
		idx += "confirm code:".length();

		String m2 = msg.substring(idx);
		m2 = m2.trim();
		idx = m2.indexOf("\n");// TODO
		String m3 = m2.substring(0, idx);//
		String rt = m3.trim();
		return rt;
	}
}
