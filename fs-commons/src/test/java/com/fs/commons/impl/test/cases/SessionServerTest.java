/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.session.SessionI;
import com.fs.commons.api.session.SessionManagerI;
import com.fs.commons.api.session.SessionServerI;
import com.fs.commons.api.wrapper.Holder;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class SessionServerTest extends TestBase {
	public void testDateConvert() throws Exception {

		SessionServerI ss = this.container.find(SessionServerI.class, true);
		SessionManagerI sm = ss.createManager("testing");
		final Holder<SessionI> timeout = new Holder<SessionI>(null);

		sm.addTimeoutHandler(new HandlerI<SessionI>() {

			@Override
			public void handle(SessionI sc) {
				timeout.setTarget(sc);
			}
		});
		long time = 1000;
		SessionI s = sm.createSession(time);
		
		String id = s.getId();
		assertNotNull(s);
		
		s = sm.getSession(id);
		assertNotNull(s);
		s = sm.touchSession(id);
		assertNotNull(s);
		Thread.sleep(time + 100);
		s = sm.getSession(id);
		assertNull("timeout not work?", s);
	}
}
