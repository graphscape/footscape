/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.expapp.wrapper;

import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.api.expapp.NodeTypes;

/**
 * @author wu
 * 
 */
public class SignupConfirm extends NodeWrapper {

	public static final String PK_SIGNUPREQUESTID = "signupRequestId";

	public SignupConfirm() {
		super(NodeTypes.SIGNUP_CONFIRM);
	}

	public static void config(NodeConfigurations cfs) {

		cfs.addConfig(NodeTypes.SIGNUP_CONFIRM, SignupConfirm.class).field(
				PK_SIGNUPREQUESTID);

	}

	public void setSignupRequestId(String srid) {
		this.setProperty(PK_SIGNUPREQUESTID, srid);
	}

	public String getSignupRequestId() {
		return (String) this.getProperty(PK_SIGNUPREQUESTID);
	}

}
