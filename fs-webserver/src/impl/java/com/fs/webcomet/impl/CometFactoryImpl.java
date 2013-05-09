/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webcomet.impl;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.webcomet.api.CometFactoryI;
import com.fs.webcomet.api.CometManagerI;
import com.fs.webcomet.api.CometProtocolI;

/**
 * @author wu
 * 
 */
public class CometFactoryImpl extends ConfigurableSupport implements CometFactoryI {

	protected Map<String, CometManagerI> managers;

	protected Map<String, CometProtocolI> protocolMap;

	public CometFactoryImpl() {
		this.protocolMap = new HashMap<String, CometProtocolI>();
		this.managers = new HashMap<String, CometManagerI>();

	}

	@Override
	public CometManagerI addManager(ActiveContext ac, String protocol, String name) {
		// create a
		CometProtocolI cp = this.getProtocol(protocol, true);
		CometManagerI rt = cp.createManager(ac, name);
		String key = toKey(protocol, name);
		this.managers.put(key, rt);

		return rt;
	}

	private String toKey(String protocol, String name) {
		String key = protocol + "(" + name + ")";
		return key;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public CometManagerI getManager(String protocol, String name, boolean force) {
		//
		String key = toKey(protocol, name);
		CometManagerI rt = this.managers.get(key);
		if (rt == null && force) {
			throw new FsException("no manager found:" + key);
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.webcomet.api.CometFactoryI#addProtocol(com.fs.webcomet.api.
	 * CometProtocolI)
	 */
	@Override
	public void addProtocol(CometProtocolI cp) {
		String key = cp.getName();
		CometProtocolI old = this.protocolMap.get(key);
		if (old != null) {
			throw new FsException("duplicated");
		}
		this.protocolMap.put(key, cp);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.webcomet.api.CometFactoryI#getProtocol(java.lang.String,
	 * boolean)
	 */
	@Override
	public CometProtocolI getProtocol(String name, boolean force) {
		CometProtocolI rt = this.protocolMap.get(name);
		if (rt == null && force) {
			throw new FsException("not found:" + name + ",all:" + this.protocolMap.keySet());
		}
		return rt;
	}

}
