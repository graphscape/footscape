/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.rooms;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomControlI;

/**
 * @author wu
 * 
 */
public interface RoomsControlI extends ControlI {

	public Jid.Bare getRoomBareJid(String rname);// room1@domain

	public RoomControlI getRoomControl(String rname, boolean force);// room1=>control.

	public void join();

	public void join(String rname);

}
