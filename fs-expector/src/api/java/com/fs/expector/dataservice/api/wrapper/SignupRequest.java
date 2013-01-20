/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class SignupRequest extends NodeWrapper {

	public static final String PK_EMAIL = "email";
	public static final String PK_NICK = "nick";
	public static final String PK_PASSWORD = "password";
	public static final String PK_CONFIRM_CODE = "confirmCode";

	public static final String[] FNS = new String[] { PK_EMAIL, PK_NICK,
			PK_PASSWORD };

	public SignupRequest() {
		super(NodeTypes.SIGNUP_REQUEST);
	}

	public static void config(DataSchema cfs) {

		cfs.addConfig(NodeTypes.SIGNUP_REQUEST, SignupRequest.class)
				.field(PK_EMAIL).field(PK_NICK).field(PK_PASSWORD)
				.field(PK_CONFIRM_CODE);

	}

	public void setPassword(String value) {
		this.setProperty("password", value);
	}

	public void setEmail(String email) {
		this.setProperty("email", email);
	}

	public String getEmail() {
		return (String) this.getProperty("email");

	}

	public void setNick(String nick) {
		this.setProperty("nick", nick);
	}

	public String getPassword() {
		return (String) this.getProperty("password");
	}

	public String getConfirmCode() {
		return (String) this.getPropertyAsString(PK_CONFIRM_CODE);
	}

	public void setConfirmCode(String confirmCode) {
		this.setProperty(PK_CONFIRM_CODE, confirmCode);
	}

	/**
	 * Oct 28, 2012
	 */
	public String getNick() {
		//
		return (String) this.getProperty("nick");
	}

}
