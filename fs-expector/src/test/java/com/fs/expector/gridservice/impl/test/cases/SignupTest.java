/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class SignupTest extends TestBase {

	public void testSignup() throws Exception {
		MockExpectorClient client = this.cfactory.newClient();
		client.connect().get();
		String nick = "user1";
		String pass = nick;
		String email = nick + "@domain.com";
		client.signup(email, nick, pass);
		PropertiesI<Object> cre = new MapProperties<Object>();
		cre.setProperty("type", "registered");
		cre.setProperty("email", email);
		cre.setProperty("password", pass);

		client.auth(cre);
		String accId = client.getAccountId();
		assertNotNull(accId);
		String sid = client.getSessionId();
		assertNotNull(sid);

	}
}
