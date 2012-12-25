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
public class Session extends AuthedNode {

	public static final String PK_CLIENTID = "clientId";

	public static final String PK_IS_ANONYMOUS = "isAnonymous";

	public Session() {
		super(NodeTypes.SESSION);
	}

	public String getClientId() {
		return this.getPropertyAsString(PK_CLIENTID);
	}

	public void setClientId(String sid) {
		this.setProperty(PK_CLIENTID, sid);
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(NodeConfigurations cfs) {
		AuthedNode.config(cfs.addConfig(NodeTypes.SESSION, Session.class)
				.field(PK_CLIENTID)
				.field(PK_IS_ANONYMOUS));
	}

}
