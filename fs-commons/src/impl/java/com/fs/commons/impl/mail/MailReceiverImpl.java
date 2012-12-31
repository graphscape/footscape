/**
 * Jul 26, 2012
 */
package com.fs.commons.impl.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.mail.MailReceiverI;
import com.fs.commons.api.mail.MimeMessageWrapper;

/**
 * @author wu
 * 
 */
public class MailReceiverImpl implements MailReceiverI {

	protected String protocol;

	protected String host;

	protected String user;

	protected String password;

	protected MailReceiverImpl(String host, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
	}

	/* */

	/* */
	@Override
	public void executeInStore(CallbackI<Store, Object> cb) {
		Properties pts = new Properties();
		pts.setProperty("mail.store.protocol", protocol);
		Session se = Session.getInstance(pts);
		try {
			Store st = se.getStore(this.protocol);

			st.connect(host, user, password);
			try {
				cb.execute(st);
			} finally {
				st.close();
			}
		} catch (MessagingException e) {
			throw new FsException(e);

		}

	}

	/* */
	@Override
	public void executeInFolder(final String folder,
			final CallbackI<FolderContext, Boolean> cb) {
		this.executeInStore(new CallbackI<Store, Object>() {

			@Override
			public Object execute(Store i) {
				try {
					Folder f = i.getFolder(folder);
					f.open(Folder.READ_WRITE);// TODO

					try {
						cb.execute(new FolderContext(f, i));
					} finally {
						f.close(true);// TODO
					}
				} catch (MessagingException e) {
					throw new FsException(e);
				}
				return null;

			}
		});
	}

	/* */
	@Override
	public void forEachMessage(final String folder,
			final CallbackI<MailContext, Boolean> each) {
		this.executeInFolder(folder, new CallbackI<FolderContext, Boolean>() {

			@Override
			public Boolean execute(FolderContext fc) {

				try {
					Folder f = fc.getFolder();

					Message[] ms = f.getMessages();
					for (int i = 0; i < ms.length; i++) {
						Message msg = f.getMessage(i + 1);
						if (msg.getFlags().contains(Flag.DELETED)) {
							continue;
						}
						;// NOTE

						MimeMessageWrapper mw = new MimeMessageImpl(
								(MimeMessage) msg);

						each.execute(new MailContext(mw, fc));
					}
				} catch (MessagingException e) {
					throw new FsException(e);
				}

				return null;

			}
		});
	}

	/* */
	@Override
	public void forEachFolder(final CallbackI<FolderContext, Boolean> each) {

		this.executeInStore(new CallbackI<Store, Object>() {

			@Override
			public Object execute(Store i) {
				try {
					Folder root = i.getDefaultFolder();//
					Folder[] fs = root.list();
					for (Folder f : fs) {
						each.execute(new FolderContext(f, i));
					}
				} catch (MessagingException e) {
					throw new FsException(e);
				}

				return null;

			}
		});
	}

	@Override
	public Future<Integer> waitMessageCountEvent(final String folder,
			long timeout) {// TODO
		final int old = this.getMailCount(folder);

		FutureTask<Integer> ft = new FutureTask<Integer>(
				new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						while (true) {
							int rt = MailReceiverImpl.this.getMailCount(folder);
							if (rt != old) {
								return rt;

							}
							Thread.sleep(1000);//
						}
					}
				});
		new Thread(ft).start();
		return ft;
	}

	public int getMailCount(String folder) {
		List<Object> ms = this.processEachMessage(folder,
				new CallbackI<MailContext, Object>() {

					@Override
					public Object execute(MailContext i) {

						return null;

					}
				});//
		return ms.size();
	}

	/* */
	@Override
	public <T> List<T> processEachMessage(String folder,
			final CallbackI<MailContext, T> each) {
		final List<T> rtL = new ArrayList<T>();

		this.forEachMessage(folder, new CallbackI<MailContext, Boolean>() {

			@Override
			public Boolean execute(MailContext i) {

				T t = each.execute(i);
				rtL.add(t);
				return true;

			}
		});
		return rtL;

	}

}
