/**
 * Jul 26, 2012
 */
package com.fs.commons.impl.mail;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import javax.mail.Folder;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.mail.MailReceiverI;
import com.fs.commons.api.wrapper.Holder;

/**
 * @author wu
 * 
 */
public class IMAPMailReceiverImpl extends MailReceiverImpl implements
		MailReceiverI {

	public IMAPMailReceiverImpl(String host, String user, String password) {
		super(host, user, password);
		super.protocol = "imap";//
	}

	// TODO not work
	private Future<Integer> TODO_waitMessageCountEvent(final String folder,
			long timeout) {// TODO
		// timeout
		final Holder<MessageCountEvent> eH = new Holder<MessageCountEvent>(null);
		final Semaphore eventOk = new Semaphore(0);

		final Holder<Future<Integer>> fH = new Holder<Future<Integer>>(null);
		final Semaphore futureOk = new Semaphore(0);
		// add listener
		// in a new thread?
		new Thread() {
			@Override
			public void run() {
				IMAPMailReceiverImpl.this.executeInFolder(folder,
						new CallbackI<FolderContext, Boolean>() {

							@Override
							public Boolean execute(FolderContext fc) {
								Folder i = fc.getFolder();
								i.addMessageCountListener(new MessageCountListener() {

									@Override
									public void messagesAdded(
											MessageCountEvent messagecountevent) {
										eH.setTarget(messagecountevent);
										eventOk.release();
									}

									@Override
									public void messagesRemoved(
											MessageCountEvent messagecountevent) {
										eH.setTarget(messagecountevent);
										eventOk.release();
									}
								});
								// future avalible
								// future
								FutureTask<Integer> rt = new FutureTask<Integer>(
										new Callable<Integer>() {

											@Override
											public Integer call()
													throws Exception {
												eventOk.acquireUninterruptibly();//
												return eH.getTarget()
														.getMessages().length;
											}
										});
								// run future
								fH.setTarget(rt);//
								futureOk.release();// NOTE

								rt.run();
								// wait ?

								return false;

							}
						});
			}
		}.start();
		futureOk.acquireUninterruptibly();
		// block
		return fH.getTarget();//
	}
}
