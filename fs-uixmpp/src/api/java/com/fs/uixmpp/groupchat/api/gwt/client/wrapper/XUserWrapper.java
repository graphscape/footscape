/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 25, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.ElementWrapper;
import com.fs.uixmpp.core.api.gwt.client.wrapper.PresenceWrapper;

/**
 * @author wu
 * 
 */
public class XUserWrapper extends ElementWrapper {

	/**
	 * This is the response when the join request a room not exist and then
	 * created a new one for you. see MucModule.java in tigase<code>
	 * 
	 * <presence to="admin@thinkpad/testing" xmlns="jabber:client" id="Tr9bj"
	 * from="room1@muc.thinkpad/admin">
	 * 		<x xmlns="http://jabber.org/protocol/muc#user">
	 * 			<item role="moderator" jid="admin@thinkpad" affiliation="owner" nick="admin"/>
	 * 			<status code="110"/>
	 * 			<status code="201"/>
	 * 		</x>
	 * </presence>
	 * 110:Inform user that presence refers to itself
	 * 201:Inform user that a new room has been created

or:
<presence to="admin@thinkpad/testing" xmlns="jabber:client" from="room1@muc.thinkpad/user2thinkpad">
	<priority>5</priority>
	<c xmlns="http://jabber.org/protocol/caps" ver="caps-b75d8d2b25" node="http://psi-im.org/caps" ext="cs ep-notify html" />

	<x xmlns="http://jabber.org/protocol/muc#user">
		<item role="participant" jid="user2@thinkpad" affiliation="none" nick="user2thinkpad" />
	</x>
</presence>
	 * </code>
	 * 
	 * @param e
	 */
	public XUserWrapper(ElementI ele) {
		super(ele);
	}

	public static XUserWrapper valueOf(PresenceWrapper pw) {
		ElementWrapper ew = pw.getChild("x",
				"http://jabber.org/protocol/muc#user", false);
		if (ew == null) {
			return null;
		}
		return new XUserWrapper(ew.getElement());
	}

	public List<XUserItemWrapper> getItemList() {
		List<XUserItemWrapper> rt = new ArrayList<XUserItemWrapper>();

		List<ElementI> eL = this.element.getChildList("item");
		for (ElementI e : eL) {
			XUserItemWrapper iw = new XUserItemWrapper(e);
			rt.add(iw);
		}
		return rt;
	}

	public int[] getStateCodeArray() {
		List<ElementI> sel = this.element.getChildList("status");
		int[] rt = new int[sel.size()];
		for (int i = 0; i < sel.size(); i++) {
			ElementI e = sel.get(i);
			rt[i] = e.getAttributeAsInt("code");
		}
		return rt;
	}

	public boolean hasStateCode(int scode) {
		int[] scodes = this.getStateCodeArray();
		for (int i = 0; i < scodes.length; i++) {
			if (scodes[i] == scode) {
				return true;
			}
		}
		return false;
	}

}
