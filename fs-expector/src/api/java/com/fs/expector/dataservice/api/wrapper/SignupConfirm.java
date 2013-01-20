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
public class SignupConfirm extends NodeWrapper {

	public static final String PK_SIGNUPREQUESTID = "signupMessageId";

	public SignupConfirm() {
		super(NodeTypes.SIGNUP_CONFIRM);
	}

	public static void config(DataSchema cfs) {

		cfs.addConfig(NodeTypes.SIGNUP_CONFIRM, SignupConfirm.class).field(
				PK_SIGNUPREQUESTID);

	}

	public void setSignupMessageId(String srid) {
		this.setProperty(PK_SIGNUPREQUESTID, srid);
	}

	public String getSignupMessageId() {
		return (String) this.getProperty(PK_SIGNUPREQUESTID);
	}

}
