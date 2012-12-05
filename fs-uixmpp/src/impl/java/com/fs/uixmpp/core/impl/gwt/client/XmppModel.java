/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 9, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;

/**
 * @author wu
 * 
 */
public class XmppModel extends ModelSupport implements XmppModelI {

	public XmppModel(String name) {
		super(name);

	}

	@Override
	protected void doAttach() {
		super.doAttach();

	}

	@Override
	public Jid getJid() {
		return (Jid) this.getValue(L_JID);
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public boolean isConnected() {
		//
		return this.getValue(Boolean.class, L_CONNECTED, false);
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public void connected(boolean connected) {
		this.setValue(L_CONNECTED, connected);
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public boolean isResourceBand() {
		return this.getValue(Boolean.class, L_RESOURCE_BAND, false);
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public void resourceBand(boolean band) {
		this.setValue(L_RESOURCE_BAND, band);
	}

}
