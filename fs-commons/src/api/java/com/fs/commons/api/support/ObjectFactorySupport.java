/**
 *  Dec 28, 2012
 */
package com.fs.commons.api.support;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.factory.ObjectCreatorI;
import com.fs.commons.api.factory.ObjectFactoryI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class ObjectFactorySupport<T> implements ObjectFactoryI<T> {

	public Map<Class<?>, ObjectCreatorI<?>> creatorMap = new HashMap<Class<?>, ObjectCreatorI<?>>();

	@Override
	public <X extends T> X create(Class<X> cls) {
		ObjectCreatorI<?> oc = this.creatorMap.get(cls);
		if (oc == null) {
			throw new FsException("no creator for :" + cls);
		}

		return (X) oc.newObject();

	}

	@Override
	public <X extends T> X create(Class<X> cls, String cfgId) {
		X rt = this.create(cls);
		if (!(rt instanceof ConfigurableI)) {
			throw new FsException("object:" + rt + " not support:"
					+ ConfigurableI.class.getName());
		}
		Configuration cfg = Configuration.properties(cfgId);
		((ConfigurableI) rt).configure(cfg);
		return rt;
	}

	@Override
	public <X extends T> void regsiterCreator(Class<X> cls, ObjectCreatorI<X> oc) {
		this.creatorMap.put(cls, oc);
	}

}
