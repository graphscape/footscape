/**
 * Jun 23, 2012
 */
package com.fs.commons.impl.codec.json;

import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.codec.support.PropertiesJCSSupport;

/**
 * @author wu
 * 
 */
public class ObjectPropertiesJCS extends PropertiesJCSSupport<PropertiesI> {

	/** */
	public ObjectPropertiesJCS(FactoryI f) {
		super("O", PropertiesI.class, f);
	}

	/* */
	@Override
	public PropertiesI convert(PropertiesI pts) {
		return pts;
	}

}
