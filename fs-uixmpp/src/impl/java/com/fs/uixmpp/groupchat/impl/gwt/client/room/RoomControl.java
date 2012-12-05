/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.room;

import java.util.List;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.builder.MessageBuilder;
import com.fs.uixmpp.core.api.gwt.client.builder.MucPresenceBuilder;
import com.fs.uixmpp.core.api.gwt.client.event.StanzaReceivedEvent;
import com.fs.uixmpp.core.api.gwt.client.wrapper.PresenceWrapper;
import com.fs.uixmpp.core.api.gwt.client.wrapper.StanzaWrapper;
import com.fs.uixmpp.groupchat.api.gwt.client.Constants;
import com.fs.uixmpp.groupchat.api.gwt.client.room.MessageModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.OccupantModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.wrapper.MucOwnerIqWrapper;
import com.fs.uixmpp.groupchat.api.gwt.client.wrapper.XUserItemWrapper;
import com.fs.uixmpp.groupchat.api.gwt.client.wrapper.XUserWrapper;

/**
 * @author wu
 * 
 */
public class RoomControl extends ControlSupport implements RoomControlI {

	public static final String MESSGE_EDITING = "MESSAGE_EDITOR";

	public static final String ROOM = "ROOM";

	public RoomControl(String name) {
		super(name);
		this.localMap.put(RoomModelI.A_SEND, true);//
		this.localMap.put(RoomModelI.A_JOIN, true);//

		this.addActionProcessor(RoomModelI.A_JOIN, new JoinAP());
		this.addActionProcessor(RoomModelI.A_SEND, new SendAP());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#doAttach()
	 */
	@Override
	protected void doAttach() {

		super.doAttach();
		this.getXmpp().addHandler(StanzaReceivedEvent.TYPE,
				new HandlerI<StanzaReceivedEvent>() {

					@Override
					public void handle(StanzaReceivedEvent e) {
						RoomControl.this.handleStanzaReceivedEvent(e);
					}
				});
	}

	@Override
	public RoomModelI getModel() {
		return (RoomModelI) this.model;
	}

	protected XmppControlI getXmpp() {
		XmppControlI rt = this.getClient(true).getChild(XmppControlI.class,
				true);//

		return rt;// TODO
	}

	// join

	public Jid getJid() {
		return this.getModel().getRoomJid();
	}

	public void handleStanzaReceivedEvent(StanzaReceivedEvent e) {
		StanzaI s = e.getStanza();

		StanzaWrapper sw = new StanzaWrapper(s);
		PresenceWrapper pw = PresenceWrapper.valueOf(sw);
		if (pw != null) {
			this.onPresence(pw);
		}

		if (!sw.isType(StanzaI.T_GROUPCHAT)) {
			return;
		}
		if (!sw.isBare(this.getJid().getBare())) {
			return;//
		}

		if (!sw.isName(StanzaI.N_MESSAGE)) {
			return;//
		}

		String content = s.getChild("body", true).getText();

		this.getModel().addMessage(s.getFrom(), s.getTo(), content);
	}

	/**
	 * <code>
	 * <presence to="admin@thinkpad/testing" xmlns="jabber:client" from="room1@muc.thinkpad/user2thinkpad">
			<priority>5</priority>
			<c xmlns="http://jabber.org/protocol/caps" ver="caps-b75d8d2b25" node="http://psi-im.org/caps" ext="cs ep-notify html" />

			<x xmlns="http://jabber.org/protocol/muc#user">
				<item role="participant" jid="user2@thinkpad" affiliation="none" nick="user2thinkpad" />
			</x>
		</presence>
		</code>
	 * 
	 * This is other form,no jid? in item element,this is when the joining
	 * user;Only the owner knowns the participant's jid? is not "owner".
	 * <p>
	 * <code>
		
		<presence from="room1@muc.thinkpad/user1thinkpad" xmlns="jabber:client" to="admin@thinkpad/testing">
			<priority>5</priority>
			<c node="http://psi-im.org/caps" xmlns="http://jabber.org/protocol/caps" ver="caps-b75d8d2b25" ext="cs ep-notify html"/>

			<x xmlns="http://jabber.org/protocol/muc#user">
				<item role="moderator" affiliation="owner" nick="user1thinkpad"/>
			</x>
		</presence> 
		 
		</code>
	 */
	protected void onPresence(PresenceWrapper pw) {
		// TODO filter?
		XUserWrapper uw = XUserWrapper.valueOf(pw);
		if (uw == null) {
			return;
		}
		if (uw.hasStateCode(Constants.S_201)) {
			// Note if you is the first join jid to the room,you is the role of
			// moderator,otherwise the role is participant..
			this.configureRoom();// this id is the owner,to be configured.
			// new RoomCreatedEvent(this).dispatch();
		}
		if (uw.hasStateCode(Constants.S_110)) {
			// the
			// new YouJoinEvent(this).dispatch();
			this.getModel().setValue(RoomModelI.L_JOINED, true);//

		}

		/**
		
		 */
		// other's presence.//add to occupantMap.
		boolean isUnava = StanzaI.T_UNAVAILABLE
				.equals(pw.getStanza().getType());

		List<XUserItemWrapper> iL = uw.getItemList();
		RoomModelI rom = this.getModel();
		for (XUserItemWrapper iw : iL) {
			String nick = iw.getNick();

			OccupantModel om = rom.getOccupantModelByNick(nick, false);

			Jid jid = iw.getJid();
			if (jid == null) {
				// you join

			}
			if (om == null) {
				om = rom.addOccupant(nick, iw.getRole(), iw.getJid());
			}

			if (isUnava) {// unavailable,one exit the room
				om.setExit(true);
			} else {// one enter the room
				om.setExit(false);//
			}
		}

	}

	//
	protected void configureRoom() {
		// TODO receive the result and set the owner.
		MucOwnerIqWrapper iw = MucOwnerIqWrapper.create(this.getXmpp(), this
				.getModel().getRoomJid().getBare());
		iw.send();
	}

	/**
	 * 
	 * Oct 25, 2012
	 */
	@Override
	public String send() {

		RoomModelI rm = this.getModel();

		String body = rm.getMessageEditing();
		if (body == null) {
			body = "TODO log message";
		}
		this.send(body);
		return body;
	}

	/*
	 * Oct 25, 2012
	 */
	@Override
	public void send(String body) {
		RoomModelI rm = this.getModel();

		XmppControlI xs = this.getXmpp();

		MessageBuilder mb = new MessageBuilder(StanzaI.T_GROUPCHAT, xs);//
		mb.to(rm.getRoomJid().bare());// TODO bare check?

		mb.body(body);
		mb.setNick(rm.getNick());
		mb.send();//
	}

}
