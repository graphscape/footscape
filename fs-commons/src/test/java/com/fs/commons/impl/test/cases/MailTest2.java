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
public class MailTest2 extends TestBase {

	private String from = "waitee3@gmail.com";// TODO
	private String to = "waitee3@gmail.com";// TODO

	public void testFolders() {
		
		MailSenderI ms = this.container.find(MailSenderI.class, true);
		
		// do send message
		MimeMessageWrapper mm = ms.createMimeMessage();
		mm.setTo(to);
		mm.setFrom(from);
		mm.setText(
				"<h>This is a html Message</h><p> Hello!<br> This is signup.</p>",
				true);
		ms.send(mm);//

	}

}
