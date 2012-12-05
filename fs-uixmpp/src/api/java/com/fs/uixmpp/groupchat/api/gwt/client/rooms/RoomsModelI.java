/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.rooms;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.Jid.Bare;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;

/**
 * @author wu
 * 
 */
public interface RoomsModelI extends ModelI {

	public static final Location L_ROOMNAME_EDITING = Location
			.valueOf("_roomNameEidting");//

	public static final Location L_MANAGER_FOR_NEW_ROOM = Location
			.valueOf("managerForNewRoom");

	public static final Location L_NICK = Location.valueOf("nick");
	
	public static final Location L_DOMAIN = Location.valueOf("domain");//

	public static final String A_JOIN = "join";

	public String getDomain();
	
	public void setManagerForNewRoom(String manager);

	public String getRoomNameEditing(boolean force);

	public List<RoomModelI> getRoomModelList();

	public RoomModelI addRoomModel(Bare rbareId, String nick);

	public RoomModelI getRoomModelByJid(Jid.Bare jid, boolean force);

	public RoomModelI getRoomModelByRoomName(String rname, boolean force);

	public String getNick(boolean force);

}
