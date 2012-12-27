/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class Account extends NodeWrapper {

	public static final String PASSWORD = "password";

	public static final String NICK = "nick";

	public static final String ISANONYMOUS = "isAnonymous";

	/**
	 * @param pts
	 */
	public Account() {
		super(NodeTypes.ACCOUNT);
	}

	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(NodeTypes.ACCOUNT, Account.class)
				.field(PASSWORD).field(NICK).field(ISANONYMOUS);

	}

	public Account applayFrom(SignupRequest sr, SignupConfirm sc) {

		return this;
	}

	public void setPassword(String password) {
		this.setProperty(PASSWORD, password);
	}

	public String getPassword() {
		return (String) this.target.getProperty(PASSWORD);
	}

	/**
	 * Oct 29, 2012
	 */
	public String getNick() {
		//
		return (String) this.target.getProperty(NICK);

	}

	/**
	 * Nov 2, 2012
	 */
	public void setNick(String nick2) {
		this.setProperty(NICK, nick2);
	}

	public void setIsAnonymous(boolean an) {
		this.setProperty(ISANONYMOUS, an);
	}

	public boolean getIsAnonymous() {
		return (Boolean) this.getProperty(ISANONYMOUS);
	}

}
