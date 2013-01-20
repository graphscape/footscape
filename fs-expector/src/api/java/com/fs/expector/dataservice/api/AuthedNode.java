/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public abstract class AuthedNode extends NodeWrapper {

	public static final String ACCOUNT_ID = "accountId";

	public static final String[] FNS = new String[] { ACCOUNT_ID };

	/**
	 * @param ntype
	 * @param pts
	 */
	public AuthedNode(NodeType ntype) {
		super(ntype);
	}

	protected static void config(NodeMeta nf) {
		nf.field(ACCOUNT_ID);
	}

	public String getAccountId() {
		return (String) this.getProperty(ACCOUNT_ID);
	}

	public void setAccountId(String value) {
		this.setProperty(ACCOUNT_ID, value);
	}

}
