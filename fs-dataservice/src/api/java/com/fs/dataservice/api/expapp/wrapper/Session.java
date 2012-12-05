/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.expapp.wrapper;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.expapp.AuthedNode;

/**
 * @author wu
 * 
 */
public class Session extends AuthedNode {

	public static final NodeType TYPE = NodeType.valueOf("SESSION");

	public static final String PK_LOCALE = "locale";

	public Session() {
		super(TYPE);
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(TYPE, Session.class).addField(PK_LOCALE);

	}

	public String getLocale() {
		return (String) this.getProperty(PK_LOCALE);
	}

	public void setLocale(String lc) {
		this.setProperty(PK_LOCALE, lc);
	}

}
