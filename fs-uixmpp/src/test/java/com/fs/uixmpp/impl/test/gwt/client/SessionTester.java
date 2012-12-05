/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 22, 2012
 */
package com.fs.uixmpp.impl.test.gwt.client;

import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.SessionConfig;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.builder.MessageBuilder;
import com.fs.uixmpp.core.api.gwt.client.event.ResourceBindSuccessEvent;
import com.fs.uixmpp.core.api.gwt.client.event.StanzaReceivedEvent;

/**
 * @author wu
 * 
 */
public class SessionTester {
	// private String domain = "fstest.com";//
	private String domain = "tigase.im";//

	protected String mucDomain = "muc." + domain;

	private String username;

	private String password;

	private String userJid;

	//

	protected String room = "room8";

	protected String roomBareJid = room + "@" + mucDomain;

	protected String userRoomJid = roomBareJid + "/" + this.username;

	protected XmppControlI xs;

	public SessionTester(String username, String password) {
		this.username = username;
		this.password = password;
		this.userJid = username + "@" + domain;
	}

	public Jid getUserJid() {
		return Jid.valueOf(this.userJid);
	}

	public void start(HandlerI<ResourceBindSuccessEvent> el) {
		SessionConfig sc1 = new SessionConfig();
		sc1.setProperty(SessionConfig.K_DOMAIN, domain);
		sc1.setProperty(SessionConfig.K_RESOURCE, "testing");
		sc1.setProperty(SessionConfig.K_URL, "/http-bind");
		sc1.setProperty(SessionConfig.K_USER, username);
		sc1.setProperty(SessionConfig.K_PASS, this.password);

		xs.addHandler(StanzaReceivedEvent.TYPE,
				new HandlerI<StanzaReceivedEvent>() {

					@Override
					public void handle(StanzaReceivedEvent e) {
						SessionTester.this.onEvent(e);

					}
				});
		xs.addHandler(ResourceBindSuccessEvent.TYPE, el);//
		xs.attach();

	}

	// implement chat/room
	public void sendMessage(Jid jid, String msg) {
		MessageBuilder sb = new MessageBuilder(this.xs);
		sb.type(StanzaI.T_NORMAL);
		sb.to(jid);
		sb.body(msg);
		sb.send();
	}

	private void onEvent(StanzaReceivedEvent e) {
		System.out.println("stanza received:" + e.getStanza());

	}

}
