/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 14, 2012
 */
package com.fs.xmpp.impl.test.cases;

import java.util.Date;

import com.fs.xmpp.impl.test.cases.support.ResourceTestBase;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.session.stanza.IqMucOwnerI;
import com.fs.xmpps.api.session.stanza.MessageI;
import com.fs.xmpps.api.session.stanza.PresenceI;

/**
 * @author wu
 * 
 */
public class MucTest extends ResourceTestBase {

	protected String mucDomain = "muc." + super.domain;

	protected String room = "room8";

	protected String roomBareJid = room + "@" + mucDomain;

	protected String userRoomJid = roomBareJid + "/" + this.username;

	public void testMuc() {

		PresenceI st = this.xc.create(PresenceI.class);
		st.setTo(userRoomJid);//
		st.send();

		//
		IqMucOwnerI cfg = this.xc.create(IqMucOwnerI.class);
		cfg.setTo(roomBareJid);
		cfg.send();

		MessageI ms = this.xc.create(MessageI.class);
		ms.setTo(roomBareJid);//
		ms.setGroupChat(true);
		ms.setNick(this.username);
		ms.setBody("hello:" + new Date());

		StanzaI rt = ms.sendSync();

		rt.assertNoError();
	}
}
