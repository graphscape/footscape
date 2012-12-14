/**
 * Jun 14, 2012
 */
package com.fs.commons.api.config;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.api.wrapper.PropertiesWrapper;

/**
 * @author wuzhen
 * 
 */
public class Configuration extends PropertiesWrapper {

	private static final String BOOTUP_PROPERTEIS_RES = "/boot/bootup.properties";
	private static final String PROPERTIES_PROVIDER_CLASS = ConfigurationProviderI.class
			.getName() + ".PROPERTIES.class";

	protected ConfigurationProviderI provider;

	protected String id;

	protected String[] names;

	/**
	 * @param pts
	 */
	public Configuration(String id, ConfigurationProviderI provider) {
		this(id, provider, PropertiesWrapper.empty());
	}

	public Configuration(String id, ConfigurationProviderI provider,
			PropertiesWrapper pw) {
		super(id + ".properties");
		this.id = id;
		this.provider = provider;
		if (this.id != null) {
			this.names = this.id.split("\\.");
		}

		this.setProperties(pw);

	}

	public String getName() {
		return (this.names == null || this.names.length == 0) ? null
				: this.names[this.names.length - 1];

	}

	public Configuration getPropertyAsConfiguration(String key, boolean force) {
		String cid = this.getProperty(key, force);
		return this.provider.getConfiguration(cid);

	}

	public static ConfigurationProviderI getPropertiesProvider() {
		PropertiesWrapper pw = PropertiesWrapper.load(BOOTUP_PROPERTEIS_RES);//
		ConfigurationProviderI rt = pw
				.getPropertyAsNewInstance(PROPERTIES_PROVIDER_CLASS);
		return rt;

	}

	// prefix.1.keySuffix=key
	// prefix.1.valueSuffix=value
	// key=value
	public PropertiesI<String> parseAsProperties(String prefix,
			String keySufix, String valueSuffix) {
		Map<String, String> keyMap = new HashMap<String, String>();
		Map<String, String> valueMap = new HashMap<String, String>();

		for (String key : this.keyListStartWith(prefix)) {

			String value = this.getProperty(key);
			if (key.endsWith(valueSuffix)) {
				String key2 = key.substring(0,
						key.length() - valueSuffix.length());
				valueMap.put(key2, value);

			}
			if (key.endsWith(keySufix)) {
				String key2 = key
						.substring(0, key.length() - keySufix.length());
				keyMap.put(key2, value);

			}
		}
		PropertiesI<String> rt = new MapProperties<String>();
		for (Map.Entry<String, String> e : keyMap.entrySet()) {
			String key = e.getKey();
			String key2 = e.getValue();
			String value = valueMap.get(key);
			rt.setProperty(key2, value);

		}
		return rt;

	}

	public static Configuration properties(String id) {

		ConfigurationProviderI pcp = getPropertiesProvider();
		Configuration rt = pcp.getConfiguration(id);// ID
		return rt;

	}

	/**
	 * @return
	 */
	public static Configuration nullConfig() {

		return new Configuration(null, new ConfigurationProviderI() {

			@Override
			public Configuration getConfiguration(String id) {

				return null;
			}

			@Override
			public void add(Configuration cfg) {
				throw new FsException("not allowed.");
			}
		});
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

}
