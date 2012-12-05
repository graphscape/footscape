/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.expapp.wrapper;

import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.expapp.AuthedNode;
import com.fs.dataservice.api.expapp.NodeTypes;

/**
 * @author wu
 * 
 */
public class Login extends AuthedNode {

	public static final String PK_SESSION_ID = "sessionId";

	public static final String PK_IS_ANONYMOUS = "isAnonymous";

	public Login() {
		super(NodeTypes.LOGIN);
	}

	public String getSessionId() {
		return this.getPropertyAsString(PK_SESSION_ID);
	}

	public void setSessionId(String sid) {
		this.setProperty(PK_SESSION_ID, sid);
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(NodeConfigurations cfs) {
		AuthedNode.config(cfs.addConfig(NodeTypes.LOGIN, Login.class)
				.field(PK_SESSION_ID)
				.field(PK_IS_ANONYMOUS));
	}

}
