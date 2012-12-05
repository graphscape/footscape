/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.room;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.groupchat.api.gwt.client.Role;

/**
 * @author wu
 * 
 */
public interface RoomModelI extends ModelI {

	public static final String A_SEND = "send";

	public static final String A_JOIN = "join";

	public static final Location L_VIEW_MANAGER = Location
			.valueOf("viewManager");

	public static final Location L_MESSAGE_EDITING = Location
			.valueOf("messageEditing");

	public static final Location L_CONFIGURED = Location.valueOf("configured");// TODO
																				// receive
																				// config
																				// stanza

	public static final Location L_JOINED = Location.valueOf("joined");// you
																		// joined.

	public static final Location L_BARE_JID = ModelI.L_DEFAULT;

	public static final Location L_NICK = Location.valueOf("nick");

	public Jid getRoomJid();

	public Jid.Bare getRoomBareJid();

	public String getMessageEditing();

	public void setMessageEditing(String s);

	public OccupantModel addOccupant(String nick, Role role, Jid jid);

	public OccupantModel getOccupantModelByNick(String nick, boolean force);

	public String getNick();

	public String getManager();

	public void setManager(String manager);

	public MessageModel addMessage(Jid from, Jid to, String content);
	
	public List<MessageModel> getMessageModelList();

}
