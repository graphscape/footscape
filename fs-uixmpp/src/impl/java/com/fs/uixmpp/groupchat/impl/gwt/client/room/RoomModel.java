/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.room;

import java.util.List;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.Jid.Bare;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.Role;
import com.fs.uixmpp.groupchat.api.gwt.client.room.MessageModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.OccupantModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;

/**
 * @author wu
 * 
 */
public class RoomModel extends ModelSupport implements RoomModelI {

	private XmppModelI xmpp;

	/**
	 * @param name
	 */
	public RoomModel(String name, XmppModelI xmpp, Bare rbare, String nick) {
		super(name);
		this.xmpp = xmpp;
		ControlUtil.addAction(this, A_JOIN);
		ControlUtil.addAction(this, A_SEND);
		this.setValue(RoomModelI.L_BARE_JID, rbare);
		this.setValue(L_NICK, nick);
	}

	@Override
	public Jid getRoomJid() {
		Jid.Bare bare = this.getRoomBareJid();
		String nick = this.getNick();

		return Jid.valueOf(bare, nick);
	}

	@Override
	public String getMessageEditing() {

		return (String) this.getValue(L_MESSAGE_EDITING);
	}

	@Override
	public void setMessageEditing(String s) {
		this.setValue(L_MESSAGE_EDITING, s);
	}

	@Override
	public OccupantModel getOccupantModelByNick(String nick, boolean force) {
		return this.getChild(OccupantModel.class, nick, force);

	}

	@Override
	public String getNick() {
		return this.getValue(String.class, L_NICK);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public OccupantModel addOccupant(String nick, Role role, Jid jid) {
		//
		OccupantModel rt = new OccupantModel(nick, role, jid);
		rt.setName(nick);
		rt.parent(this);

		return rt;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public Bare getRoomBareJid() {
		//
		return (Bare) this.getDefaultValue();
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public String getManager() {
		//
		return (String) this.getValue(RoomModelI.L_VIEW_MANAGER);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void setManager(String manager) {
		this.setValue(RoomModelI.L_VIEW_MANAGER, manager);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public MessageModel addMessage(Jid from, Jid to, String content) {
		//
		MessageModel rt = new MessageModel("unknow", this.xmpp, from, to,
				content);
		this.child(rt);
		return rt;
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public List<MessageModel> getMessageModelList() {

		//
		return this.getChildList(MessageModel.class);

	}

}
