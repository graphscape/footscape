/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 9, 2012
 */
package com.fs.uixmpp.muc.impl.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppI;
import com.fs.uixmpp.core.api.gwt.client.builder.MessageBuilder;
import com.fs.uixmpp.core.api.gwt.client.builder.MucPresenceBuilder;
import com.fs.uixmpp.core.api.gwt.client.event.MessageEvent;
import com.fs.uixmpp.core.api.gwt.client.event.StanzaReceivedEvent;
import com.fs.uixmpp.core.api.gwt.client.wrapper.PresenceWrapper;
import com.fs.uixmpp.core.api.gwt.client.wrapper.StanzaWrapper;
import com.fs.uixmpp.muc.api.gwt.client.Constants;
import com.fs.uixmpp.muc.api.gwt.client.MessageItem;
import com.fs.uixmpp.muc.api.gwt.client.Occupant;
import com.fs.uixmpp.muc.api.gwt.client.RoomI;
import com.fs.uixmpp.muc.api.gwt.client.RoomManagerI;
import com.fs.uixmpp.muc.api.gwt.client.event.ExitOccupantEvent;
import com.fs.uixmpp.muc.api.gwt.client.event.JoinOccupantEvent;
import com.fs.uixmpp.muc.api.gwt.client.event.RoomCreatedEvent;
import com.fs.uixmpp.muc.api.gwt.client.event.YouJoinEvent;
import com.fs.uixmpp.muc.api.gwt.client.wrapper.MucOwnerIqWrapper;
import com.fs.uixmpp.muc.api.gwt.client.wrapper.XUserItemWrapper;
import com.fs.uixmpp.muc.api.gwt.client.wrapper.XUserWrapper;

/**
 * @author wu http://xmpp.org/extensions/xep-0045.html#createroom
 */
public class RoomImpl extends UiObjectSupport implements RoomI {

	protected Jid jid;

	protected Map<String, Occupant> occupantMap;

	protected List<MessageItem> messageItemList;

	public RoomImpl(Jid jid) {
		this.jid = jid;
		this.occupantMap = new HashMap<String, Occupant>();
		this.messageItemList = new ArrayList<MessageItem>();
	}

	@Override
	public Jid getJid() {// room's bare jid+nick name.
		return jid;
	}

	public String getNick() {
		return this.jid.getResource();//
	}

	@Override
	public void join() {
		// see MucModule.join
		MucPresenceBuilder pb = new MucPresenceBuilder(this.getXmppSession());
		pb.to(this.jid);// send presence to
		// pb.setPassword(pw);
		pb.send();
		// add listener to client
		this.getEventBus(true).addHandler(StanzaReceivedEvent.TYPE,
				new HandlerI<StanzaReceivedEvent>() {

					@Override
					public void handle(StanzaReceivedEvent e) {
						RoomImpl.this.onEvent(e);
					}
				});
	}

	// TODO use common filter/listener.
	protected void onEvent(StanzaReceivedEvent e) {
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
		MessageItem mi = MessageItem.valueOf(s.getFrom(), s.getTo(), content);
		this.messageItemList.add(mi);
		new MessageEvent(mi, this).dispatch();

	}

	protected void onPresence(PresenceWrapper pw) {
		XUserWrapper uw = XUserWrapper.valueOf(pw);
		if (uw == null) {
			return;
		}
		if (uw.hasStateCode(Constants.S_201)) {
			// Note if you is the first join jid to the room,you is the role of
			// moderator,otherwise the role is participant..
			this.configureRoom();// this id is the owner,to be configured.
			new RoomCreatedEvent(this).dispatch();
		}
		if (uw.hasStateCode(Constants.S_110)) {
			// the
			new YouJoinEvent(this).dispatch();
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
		 */
		// other's presence.//add to occupantMap.
		boolean isUnava = StanzaI.T_UNAVAILABLE
				.equals(pw.getStanza().getType());

		List<XUserItemWrapper> iL = uw.getItemList();
		for (XUserItemWrapper iw : iL) {
			String nick = iw.getNick();
			Occupant op = new Occupant(nick, iw.getRole(), iw.getJid());
			if (isUnava) {// unavailable,one exit the room
				Occupant oc = this.occupantMap.remove(nick);//
				if (oc == null) {
					// TODO
				}
				new ExitOccupantEvent(op, this).dispatch();
			} else {// one enter the room
				this.occupantMap.put(nick, op);
				new JoinOccupantEvent(op, this).dispatch();
			}
		}

	}

	//
	protected void configureRoom() {
		//TODO receive the result and set the owner.
		MucOwnerIqWrapper iw = MucOwnerIqWrapper.create(this.getXmppSession(),
				this.getJid().getBare());
		iw.send();
	}

	protected Jid.Bare getUserBare() {
		XmppI xs = this.getXmppSession();
		Jid.Bare rt = xs.getJid().getBare();
		// return xs.get
		return rt;
	}

	protected RoomManagerI getRoomManager() {
		return (RoomManagerI) this.getParent();
	}

	protected XmppI getXmppSession() {
		return this.getRoomManager().getXmppClient();
	}

	@Override
	public void send(String body) {

		XmppI xs = this.getXmppSession();
		MessageBuilder mb = new MessageBuilder(StanzaI.T_GROUPCHAT, xs);//
		mb.to(this.jid.bare());// TODO bare check?
		mb.body(body);
		mb.setNick(this.getNick());
		mb.send();//
		/*
		 * this code is from Room in JaxmppCore: Message msg = Message.create();
		 * msg.setTo(JID.jidInstance(this.jid.toString()));
		 * msg.setType(StanzaType.groupchat); msg.setBody(body);
		 * 
		 * this.writer.write(msg);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.muc.api.gwt.client.RoomI#isOwner()
	 */
	@Override
	public boolean isOwner() {
		
		return false;// TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.muc.api.gwt.client.RoomI#getOccupantList()
	 */
	@Override
	public List<Occupant> getOccupantList() {
		List<Occupant> rt = new ArrayList<Occupant>();
		rt.addAll(this.occupantMap.values());
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uixmpp.muc.api.gwt.client.RoomI#getOccupantByNick(java.lang.String
	 * )
	 */
	@Override
	public Occupant getOccupantByNick(String nick, boolean force) {
		Occupant rt = this.occupantMap.get(nick);
		if (force && rt == null) {
			throw new UiException("force:" + nick);
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.muc.api.gwt.client.RoomI#getMessageList()
	 */
	@Override
	public List<MessageItem> getMessageList() {
		return this.messageItemList;
	}

}