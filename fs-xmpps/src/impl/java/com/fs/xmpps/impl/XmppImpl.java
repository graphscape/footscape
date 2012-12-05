/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpps.impl;

import java.util.concurrent.Future;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.xmpps.api.Jid;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.XmppI;
import com.fs.xmpps.api.XmppSessionI;
import com.fs.xmpps.api.wrapper.RegisterWrapper;

/**
 * @author wu
 * 
 */
public class XmppImpl extends ConfigurableSupport implements XmppI {

	private String host;

	private String domain;

	@Override
	public XmppSessionI open() {

		XmppSession rtx = new XmppSession(host, domain);
		Future<XmppSessionI> frt = rtx.start();//

		XmppSessionI rt = FutureUtil.get(frt);

		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.xmppclient.api.XmppConnectionI.FactoryI#execute(com.fs.commons
	 * .api.callback.CallbackI)
	 */
	@Override
	public <T> T execute(CallbackI<XmppSessionI, T> cc) {
		XmppSessionI c = this.open();
		try {
			return cc.execute(c);
		} finally {
			c.close();
		}
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public void register(final String username, final String password) {
		//
		this.execute(new CallbackI<XmppSessionI, Object>() {

			@Override
			public Object execute(XmppSessionI i) {
				//
				XmppImpl.this.doRegister(i, username, password);
				return null;

			}
		});
	}

	/**
	 * Oct 26, 2012
	 */
	protected void doRegister(XmppSessionI i, String username, String password) {
		RegisterWrapper rw = RegisterWrapper.valueOf(i);
		rw.setType(StanzaI.T_SET);
		rw.setFrom(Jid.valueOf("c2s@" + this.domain));// NOTE
		rw.setTo(Jid.valueOf(domain));// NOTE
		rw.setUsername(username);
		rw.setPassword(password);
		StanzaI rt = rw.sendSync();
		rt.assertNoError();//
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.domain = cfg.getProperty("xmpp.domain");
		this.host = cfg.getProperty("xmpp.host", "localhost");
	}

}
