/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 1, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;
import com.fs.uixmpp.core.api.gwt.client.event.ConnectedEvent;
import com.fs.uixmpp.core.api.gwt.client.event.DisconnectedEvent;
import com.fs.uixmpp.core.api.gwt.client.event.ResourceBindSuccessEvent;

/**
 * @author wu Watchdog for reconnect xmpp.
 */
public class ConnectKeeper {
	private XmppControlI xmpp;

	private int maxTries = 10;

	private int tries = 0;

	public ConnectKeeper(XmppControlI xmpp) {
		this.xmpp = xmpp;
		xmpp.addHandler(DisconnectedEvent.TYPE,
				new HandlerI<DisconnectedEvent>() {

					@Override
					public void handle(DisconnectedEvent e) {
						ConnectKeeper.this.onDisconnected();
					}
				});
		xmpp.addHandler(ConnectedEvent.TYPE, new HandlerI<ConnectedEvent>() {

			@Override
			public void handle(ConnectedEvent e) {
				ConnectKeeper.this.onConnected();
			}
		});
		xmpp.addHandler(ResourceBindSuccessEvent.TYPE,
				new HandlerI<ResourceBindSuccessEvent>() {

					@Override
					public void handle(ResourceBindSuccessEvent e) {
						ConnectKeeper.this.onResourceBindingSuccess();
					}
				});
	}

	public void onResourceBindingSuccess() {
		XmppModelI xm = (XmppModelI) this.xmpp.getModel();
		xm.resourceBand(true);
	}

	public void onConnected() {
		XmppModelI xm = (XmppModelI) this.xmpp.getModel();
		xm.connected(false);
	}

	public void onDisconnected() {
		XmppModelI xm = (XmppModelI) this.xmpp.getModel();
		xm.connected(false);
		xm.resourceBand(false);

		if (this.tries > this.maxTries) {
			return;
		}

		this.xmpp.active();// try once the disconnected.

		this.tries++;

	}

}
