/**
 * Jun 14, 
 */
package com.fs.commons.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.support.StringProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class Configuration extends MapProperties<String> {
	private static final String BOOTUP_PROPERTEIS_RES = "/boot/bootup.properties";
	private static final String PROPERTIES_PROVIDER_CLASS = ConfigurationProviderI.class.getName()
			+ ".PROPERTIES.class";

	public static class Type extends com.fs.commons.api.Enum {

		/**
		 * @param name
		 */
		public Type(String name) {
			super(name);
		}

		public static Type valueOf(String vs) {
			return new Type(vs);
		}
	}

	public static Type ROOT = Type.valueOf("config");

	protected static ConfigurationProviderI PROVIDER;

	protected String id;

	protected Type type;

	protected List<String> childNameList = new ArrayList<String>();

	protected Map<String, String> configRefMap = new HashMap<String, String>();

	/**
	 * @param pts
	 */
	public Configuration(String id, Type type) {
		this(id, type, new MapProperties<String>(), new ArrayList<String>(), new HashMap<String, String>());
	}

	public Configuration(String id, Type type, PropertiesI<String> pts, List<String> childIdSet,
			Map<String, String> configRefMap) {
		this.id = id;
		this.type = type;
		this.setProperties(pts);
		this.childNameList.addAll(childIdSet);
		this.configRefMap.putAll(configRefMap);
	}

	public static ConfigurationProviderI getPropertiesProvider() {
		if (PROVIDER != null) {
			return PROVIDER;
		}
		StringProperties pw = StringProperties.load(BOOTUP_PROPERTEIS_RES);//
		ConfigurationProviderI rt = pw.getPropertyAsNewInstance(PROPERTIES_PROVIDER_CLASS);
		PROVIDER = rt;
		return rt;

	}

	public Configuration getPropertyAsConfiguration(String key, boolean force) {
		String cid = this.getProperty(key, force);
		return PROVIDER.getConfiguration(cid);

	}

	public Type getType() {
		return type;
	}

	public Configuration getChildConfiguration(String name) {
		return this.getChildConfiguration(name, false);

	}

	public Configuration getReferenedConfiguration(String name, boolean force) {
		String id = this.configRefMap.get(name);
		if (id == null) {
			if (force) {
				throw new FsException("no config ref found:" + name + ",in config:" + this);
			}
			return null;
		}
		return Configuration.properties(id);
	}

	public Configuration getReferencedOrChildConfiguration(String name, boolean allowNull) {
		Configuration rt = this.getReferenedConfiguration(name, false);
		if (rt != null) {
			return rt;
		}
		return this.getChildConfiguration(name, allowNull);
	}

	public Configuration getChildConfiguration(Type type, boolean force) {
		List<Configuration> rtL = this.getChildConfigurationList(type);
		if (rtL.isEmpty()) {
			if (force) {
				throw new FsException("no child config fount for type:" + type + ",in parent:" + this);
			}
			return null;
		} else if (rtL.size() == 1) {
			return rtL.get(0);//
		} else {
			throw new FsException("too much child for type:" + type + ",in parent:" + this);

		}

	}

	public Configuration getChildConfiguration(String name, boolean allowNull) {

		if (allowNull && !this.childNameList.contains(name)) {
			return null;
		}

		String cid = this.id + "." + name;
		Configuration rt = Configuration.properties(cid);
		return rt;
	}

	public List<Configuration> getChildConfigurationList(Type type) {
		List<Configuration> rt = new ArrayList<Configuration>();
		for (String name : this.childNameList) {
			String cid = this.id + "." + name;
			Configuration cc = Configuration.properties(cid);
			if (cc.getType().equals(type)) {
				rt.add(cc);
			}
		}
		return rt;
	}

	public List<Configuration> getChildConfigurationList() {
		List<Configuration> rt = new ArrayList<Configuration>();
		for (String name : this.childNameList) {
			String cid = this.id + "." + name;
			Configuration cc = Configuration.properties(cid);
			rt.add(cc);
		}
		return rt;
	}

	public static Configuration properties(String id) {
		return properties(id, true);
	}

	public static Configuration properties(String id, boolean cache) {

		ConfigurationProviderI pcp = getPropertiesProvider();
		Configuration rt = pcp.getConfiguration(id, cache);// ID
		return rt;

	}

	/**
	 * @return
	 */
	public static Configuration nullConfig() {

		return new Configuration(null, null);
	}

	@Override
	public String toString() {
		return "configuration:" + this.getId();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public <T> T getPropertyAsNewInstance(String key) {
		return this.getPropertyAsNewInstance(key, false);
	}

	public <T> T getPropertyAsNewInstance(String key, Class[] clss, Object[] args) {
		return this.getPropertyAsNewInstance(key, clss, args, false);
	}

	public <T> T getPropertyAsNewInstance(String key, boolean force) {
		Class<T> cls = this.getPropertyAsClass(key, force);
		if (cls == null) {
			return null;
		}
		T rt = ClassUtil.newInstance(cls);

		return rt;

	}

	public <T> T getPropertyAsNewInstance(String key, Class[] clss, Object[] args, boolean force) {
		Class<T> cls = this.getPropertyAsClass(key, force);
		if (cls == null) {
			return null;
		}
		T rt = ClassUtil.newInstance(cls, clss, args);

		return rt;
	}

	public List<String> getPropertyAsCSV(String key) {
		List<String> rt = new ArrayList<String>();

		String v = this.getProperty(key);
		if (v == null || v.trim().length() == 0) {
			return rt;
		}
		String[] vs = v.split(",");
		rt.addAll(Arrays.asList(vs));
		return rt;

	}

	public <T> Class<T> getPropertyAsClass(String key) {
		return this.getPropertyAsClass(key, false);
	}

	public <T> Class<T> getPropertyAsClass(String key, boolean force) {
		String cName = this.getProperty(key, false);

		if (cName == null) {
			if (force) {
				throw new FsException("force:" + key + ", in cfg id:" + this.id);
			}
			return null;
		}
		try {
			return ClassUtil.forName(cName);
		} catch (FsException fe) {
			if (fe.getCause() instanceof ClassNotFoundException) {
				throw new FsException("class not found for key:" + key + ",value:" + cName + ",in cfg id:"
						+ this.id);
			}
			throw fe;
		}
	}

	/**
	 * @return
	 */
	public String getName() {
		int idx = this.id.lastIndexOf('.');
		if (idx < 0) {
			return this.id;
		}
		return this.id.substring(idx + 1);
	}

	/**
	 * @param string
	 * @param i
	 * @return
	 */
	public int getPropertyAsInt(String key, int i) {
		String v = this.getProperty(key);
		if (v == null) {
			return i;
		}
		return Integer.parseInt(v);
	}
}
