/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.mail.MailReceiverI;
import com.fs.commons.api.mail.MailReceiverI.FolderContext;
import com.fs.commons.api.mail.MailReceiverI.MailContext;
import com.fs.commons.api.mail.MailSenderI;
import com.fs.commons.api.mail.MimeMessageWrapper;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class MailTest extends TestBase {

	private String trash = "Trash";
	private String from = "signup@footsight.com";// TODO
	private String user = "wu@footsight.com";// TODO
	private String pass = "wu";// TODO

	public void testFolders() {
		MailReceiverI.FactoryI rf = this.container.find(
				MailReceiverI.FactoryI.class, true);

		MailSenderI ms = this.container.find(MailSenderI.class, true);
		final MailReceiverI mr = rf.create("localhost", user, pass);
		mr.forEachFolder(new CallbackI<FolderContext, Boolean>() {

			@Override
			public Boolean execute(FolderContext i) {

				try {
					System.out.println(i.getFolder().getName() + ":"
							+ i.getFolder().getMessageCount());
				} catch (MessagingException e) {
					throw new FsException(e);
				}

				return null;

			}
		});
	}

	public void testSendAndReceiveMail() {
		MailReceiverI.FactoryI rf = this.container.find(
				MailReceiverI.FactoryI.class, true);

		MailSenderI ms = this.container.find(MailSenderI.class, true);
		final MailReceiverI mr = rf.create("localhost", user, pass);
		// clean folder
		mr.executeInFolder("inbox", new CallbackI<FolderContext, Boolean>() {

			@Override
			public Boolean execute(FolderContext i) {
				try {
					Folder f1 = i.getFolder();
					Message[] ms = f1.getMessages();

					Folder f2 = i.getStore().getFolder(trash);
					if (!f2.exists()) {
						f2.create(Folder.HOLDS_MESSAGES);//
					}
					f1.copyMessages(ms, f2);// move to trash.
					for (Message m : ms) {
						m.setFlag(Flag.DELETED, true);//
					}
				} catch (MessagingException e) {
					throw new FsException(e);
				}

				return null;

			}
		});

		// message

		// add listener for new message added.
		Future<Integer> fu = mr.waitMessageCountEvent("INBOX", 5000);//
		// do send message
		MimeMessageWrapper mm = ms.createMimeMessage();
		mm.setTo(user);
		mm.setFrom(from);
		mm.setText(
				"<h>This is a html Message</h><p> Hello!<br> This is signup.</p>",
				true);
		ms.send(mm);//

		// wait the new message
		try {
			fu.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);
		} catch (ExecutionException e) {
			throw new FsException(e);
		} catch (TimeoutException e) {
			throw new FsException(e);
		}
		List<String> mws = mr.processEachMessage("INBOX",
				new CallbackI<MailContext, String>() {

					@Override
					public String execute(MailContext i) {

						String rt = i.getMessageWrapper().getContentAsText();
						return rt;

					}
				});// remove all

		System.out.println("content:" + mws);
		assertEquals("", 1, mws.size());

	}

	public void receive(MailReceiverI mr) {
		// TODO receive.

	}

}
