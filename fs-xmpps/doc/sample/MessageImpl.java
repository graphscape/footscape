/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 14, 2012
 */
package com.fs.xmpps.impl.stanza;

import com.fs.commons.api.lang.FsException;
import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.session.stanza.MessageI;
import com.fs.xmpps.impl.StanzaImpl;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu <br>
 *         <code>
 * <message to="room5@muc.fstest.com" xmlns="jabber:client" type="groupchat" id="aad7a">
<body>hello</body>
<nick xmlns="http://jabber.org/protocol/nick">User1</nick>
</message>
</code>
 */
public class MessageImpl extends StanzaImpl implements MessageI {

	/**
	 * @param name
	 * @param ci
	 */
	public MessageImpl(XmppSession ci) {
		super("message", ci);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.stanza.MessageI#setBody(java.lang.String)
	 */
	@Override
	public void setBody(String msg) {
		ElementI body = this.getOrAddChild("body");
		body.setText(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.stanza.MessageI#setGroupChat(boolean)
	 */
	@Override
	public void setGroupChat(boolean gc) {
		if (gc) {
			this.setType(StanzaI.T_GROUPCHAT);
		} else {
			throw new FsException("TODO");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.stanza.MessageI#setNick(java.lang.String)
	 */
	@Override
	public void setNick(String nick) {
		ElementI ne = this.getOrAddChild("nick");
		ne.setXmlns("http://jabber.org/protocol/nick");//
		ne.setText(nick);
	}

}
