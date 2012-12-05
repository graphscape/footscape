/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpp.impl.test.cases;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.fs.commons.api.lang.FsException;
import com.fs.xmpp.impl.test.cases.support.TestBase;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.XmppServiceI;
import com.fs.xmpps.api.XmppSessionI;
import com.fs.xmpps.api.session.stanza.IqAuthI;
import com.fs.xmpps.api.session.stanza.IqRegisterI;

/**
 * @author wu
 * 
 */
public class IqAuthTest extends TestBase {

	public void testAddUser() {
		XmppServiceI xcf = this.container.find(XmppServiceI.class, true);
		XmppSessionI xc = xcf.open(domain);
		try {
			StanzaI get = this.createGet(xc);
			Future<StanzaI> f = get.send();
			StanzaI rt = f.get();
			System.out.println("rt:" + rt);
			StanzaI st = this.createSet(xc);
			f = st.send();
			rt = f.get();
			System.out.println("rt:" + rt);
		} catch (InterruptedException e) {
			throw new FsException(e);
		} catch (ExecutionException e) {
			throw new FsException(e);
		} finally {
			xc.close();
		}
	}

	public StanzaI createGet(XmppSessionI xc) {

		IqRegisterI ur = xc.create(IqRegisterI.class);

		ur.setType("get");//
		ur.setTo("thinkpad");//

		return ur;
	}

	// Add User by commands
	public IqAuthI createSet(XmppSessionI xc) {
		IqAuthI rt = xc.create(IqAuthI.class);
		rt.setType("set");
		rt.setFrom("c2s@thinkpad");// NOTE
		rt.setTo("sess-man@thinkpad");// NOTE
		rt.setUserName("user12");
		rt.setPassword("user12");
		return rt;
	}

}
