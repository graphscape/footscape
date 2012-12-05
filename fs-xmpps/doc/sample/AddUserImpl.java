/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 9, 2012
 */
package com.fs.xmpps.impl.stanza;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.session.stanza.AddUserI;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu
 * 
 */
public class AddUserImpl extends CommandImpl implements AddUserI {

	/**
	 * @param name
	 * @param ci
	 */
	public AddUserImpl(XmppSession ci) {
		super(ci);

		this.setAttribute("type", "set");//
		// iq.setXMLNS("jabber:client");//
		this.setAttribute("from", "sess-man@thinkpad");//

		this.setAttribute("to", "thinkpad");//
		// iq.setAttribute("to", "thinkpad");//

		ElementI cmd = this.addChild("command");

		cmd.setAttribute("xmlns", "http://jabber.org/protocol/commands");
		cmd.setAttribute("node", "http://jabber.org/protocol/admin#add-user");
		cmd.setAttribute("action", "execute");
		// cmd.setAttribute("sessionid", value)

		ElementI x = cmd.addChild("x");
		x.setAttribute("xmlns", "jabber:x:data");
		x.setAttribute("type", "submit");
		// this.addFieldValue(x, "FORM_TYPE",
		// "http://jabber.org/protocol/admin");
		String username = "user22";
		this.addFieldValue(x, "accountjid", username + "@thinkpad");
		this.addFieldValue(x, "password", username);
		this.addFieldValue(x, "password-verify", username);
		this.addFieldValue(x, "email", username + "@thinkpad");
		this.addFieldValue(x, "given_name", "User 22");
		this.addFieldValue(x, "surname", "One");

	}

	protected ElementI addFieldValue(ElementI x, String var, String value) {
		ElementI f = x.addChild("field");
		f.setAttribute("var", var);
		ElementI v = x.addChild("value");
		v.setText(value);//
		return x;

	}

}
