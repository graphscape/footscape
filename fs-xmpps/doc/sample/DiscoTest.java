/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 12, 2012
 */
package com.fs.xmpp.impl.test.cases;

import com.fs.xmpp.impl.test.cases.support.ResourceTestBase;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.XmppSessionI;
import com.fs.xmpps.api.session.stanza.IqDiscoI;

/**
 * @author wu
 * 
 */
public class DiscoTest extends ResourceTestBase {
	// NOTE if no domain configured connection will close at start.

	public void testDisc() {


		IqDiscoI st = this.xc.create(IqDiscoI.class);
		st.setType("get");//
		st.setTo(domain);//
		StanzaI rt = st.sendSync();
		rt.assertNoError();
	}

}
