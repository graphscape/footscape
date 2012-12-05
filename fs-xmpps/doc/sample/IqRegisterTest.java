/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpp.impl.test.cases;

import com.fs.commons.api.value.ErrorInfos;
import com.fs.xmpp.impl.test.cases.support.TestBase;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.XmppServiceI;
import com.fs.xmpps.api.XmppSessionI;
import com.fs.xmpps.api.session.stanza.IqAuthI;
import com.fs.xmpps.api.session.stanza.IqBindI;
import com.fs.xmpps.api.session.stanza.IqRegisterI;

/**
 * @author wu
 * 
 */
public class IqRegisterTest extends TestBase {

	private String username = "user19";
	private String password = username;
	private String resource = "testing";

	public void testAddUser() {
		XmppServiceI xcf = this.container.find(
				XmppServiceI.class, true);
		XmppSessionI xc = xcf.open(domain);
		try {
			{
				StanzaI get = this.createGet(xc);
				StanzaI rt = get.sendSync();
				ErrorInfos eis = rt.getErrorInfos();

				System.out.println("rt:" + rt);
				assertFalse("failed get,response:" + rt, eis.hasError());

			}

			if (true) {
				StanzaI st = this.createRegister(xc);
				StanzaI rt = st.sendSync();
				System.out.println("rt:" + rt);
				ErrorInfos eis = rt.getErrorInfos();
				assertFalse("failed register,response:" + rt, eis.hasError());
			}
			{
				StanzaI st = this.createAuth(xc);
				StanzaI rt = st.sendSync();
				System.out.println("rt:" + rt);
				ErrorInfos eis = rt.getErrorInfos();
				assertFalse("failed auth,response:" + rt, eis.hasError());
			}
			{
				StanzaI st = this.createBinding(xc);

				StanzaI rt = st.sendSync();
				System.out.println("rt:" + rt);
				ErrorInfos eis = rt.getErrorInfos();
				assertFalse("failed auth,response:" + rt, eis.hasError());

			}
			{
				StanzaI st = this.createRegisterRemove(xc);
				//
				// remove has no response?

				st.send();
				//
				// System.out.println("rt:" + rt);
				// ErrorInfos eis = rt.getErrorInfos();
				// assertFalse("failed remove,response:"+rt,eis.hasError());
			}

		} finally {
			xc.close();
		}
	}

	public StanzaI createGet(XmppSessionI xc) {

		IqRegisterI ur = xc.create(IqRegisterI.class);

		ur.setType("get");//
		ur.setTo(domain);//

		return ur;
	}

	// Binding resource
	public IqBindI createBinding(XmppSessionI xc) {
		IqBindI rt = xc.create(IqBindI.class);
		rt.setType("set");
		rt.setFrom("c2s@" + domain);// NOTE
		rt.setTo(domain);// NOTE
		rt.setResource(resource);// Resource
		return rt;
	}

	// Add User by commands
	public IqRegisterI createRegister(XmppSessionI xc) {
		IqRegisterI rt = xc.create(IqRegisterI.class);
		rt.setType("set");
		rt.setFrom("c2s@" + domain);// NOTE
		rt.setTo(domain);// NOTE
		rt.setUserName(username);
		rt.setPassword(password);
		rt.setEmail("user15@email.com");
		return rt;
	}

	// Add User by commands
	public IqAuthI createAuth(XmppSessionI xc) {
		IqAuthI rt = xc.create(IqAuthI.class);
		rt.setType("set");
		rt.setFrom("c2s@" + domain);// NOTE
		rt.setTo(domain);// NOTE
		rt.setUserName(username);
		rt.setPassword(password);
		return rt;
	}

	public IqRegisterI createRegisterRemove(XmppSessionI xc) {
		IqRegisterI rt = xc.create(IqRegisterI.class);
		rt.setType("set");
		rt.setFrom("c2s@" + domain);// NOTE
		rt.setTo(domain);// NOTE
		rt.setRemove(true);
		return rt;
	}

}
