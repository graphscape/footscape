/**
 *  
 */
package com.fs.webcomet.api.support;

import com.fs.commons.api.config.Configuration;
import com.fs.webcomet.api.CometProtocolI;

/**
 * @author wu
 * 
 */
public abstract class CometProtocolSupport implements CometProtocolI {

	protected String name;
	protected Configuration cfg;
	protected String configId;

	public CometProtocolSupport(String name, Configuration cfg) {
		this.name = name;
		this.cfg = cfg;
		this.configId = cfg.getId();
	}

	@Override
	public String getName() {
		return this.name;
	}

}
