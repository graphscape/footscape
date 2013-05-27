/**
 *  
 */
package com.fs.commons.impl.components;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.components.ComponentFactoryI;
import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class ComponentFactoryImpl implements ComponentFactoryI {

	private static final Logger LOG = LoggerFactory.getLogger(ComponentFactoryImpl.class);

	private ContainerI container;

	private Map<String, Class> impClsMap = new HashMap<String, Class>();

	public ComponentFactoryImpl(ContainerI c) {
		this.container = c;
	}

	@Override
	public Class getComponentClass(String key, boolean force) {

		Class rt = this.impClsMap.get(key);
		if (rt == null && force) {
			throw new FsException("no component type with key:" + key + ",all:" + this.impClsMap);
		}
		return rt;

	}

	@Override
	public void addComponentClass(Class key, Class cls) {
		this.addComponentClass(key.getName(), cls);
	}

	@Override
	public void addComponentClass(String key, Class cls) {
		this.addComponentClass(key, cls, false);
	}

	@Override
	public void addComponentClass(String key, Class cls, boolean replace) {
		Class old = this.getComponentClass(key, false);

		if (null != old) {
			if (replace) {
				LOG.warn("replace type:" + key + ",old:" + old + ",new:" + cls);
			} else {

				throw new FsException("component already exist:" + key + ",cls:" + old);
			}
		}

		this.impClsMap.put(key, cls);

	}

	@Override
	public <T> T newComponent(SPI pl, Configuration cfg) {
		Class<T> cls = cfg.getPropertyAsClass("class", false);
		T rt = null;
		if (cls == null) {
			String type = cfg.getProperty("type", true);
			cls = this.getComponentClass(type, true);
		}

		rt = this.newComponent(pl, cls);

		if (rt instanceof ConfigurableI) {
			((ConfigurableI) rt).configure(cfg);

		}
		return rt;
	}

	@Override
	public <T> T newComponent(SPI pl, Configuration cfg, Class<T> cls) {
		T rt = this.newComponent(pl, cls);

		if (rt instanceof ConfigurableI) {
			((ConfigurableI) rt).configure(cfg);

		}
		return rt;
	}

	@Override
	public <T> T newComponent(SPI pl, Class<T> cls) {
		if (cls.isInterface()) {
			String key = cls.getName();
			cls = this.getComponentClass(key, false);
			if (cls == null) {
				throw new FsException("there is no implementation class regestered for interface:" + cls);
			}
		}
		T rt = (T) ClassUtil.newInstance(cls);
		if (rt instanceof ContainerI.AwareI) {
			ContainerI.AwareI ca = (ContainerI.AwareI) rt;
			ca.setContainer(this.container);
		}

		return rt;
	}

}
