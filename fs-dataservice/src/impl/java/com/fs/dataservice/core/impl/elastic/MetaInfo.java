/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 1, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import com.fs.commons.api.support.MapProperties;

/**
 * @author wu
 * 
 */
public class MetaInfo extends MapProperties<String> {

	public static final String PK_VERSION = "version";

	public static final String PK_OWNER = "owner";

	public static final String PK_PASSWORD = "password";

	public static final String TYPE = "meta-infos";

	public MetaInfo(String version, String owner, String password) {
		this.setProperty(PK_VERSION, version);
		this.setProperty(PK_OWNER, owner);
		this.setProperty(PK_PASSWORD, password);
	}

	public String getOwner() {
		return this.getProperty(PK_OWNER, true);
	}

	public String getPassword() {
		return this.getProperty(PK_PASSWORD, true);
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.getProperty(PK_VERSION, true);
	}

}
