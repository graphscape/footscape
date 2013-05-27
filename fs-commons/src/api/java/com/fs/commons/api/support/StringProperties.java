/**
 * Jun 14, 2012
 */
package com.fs.commons.api.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class StringProperties extends MapProperties<String> {

	private String res;

	public StringProperties() {
		this("null");
	}

	public StringProperties(String res) {
		this(res, new HashMap<String, String>());
	}

	public StringProperties(Map<String, String> pts) {
		this("null", pts);
	}

	public StringProperties(String res, Map<String, String> pts) {
		super(pts);
		this.res = res;
	}

	public static StringProperties empty() {
		return new StringProperties("empty");
	}

	public static StringProperties load(String resfile) {
		return load(resfile, false);
	}

	public static StringProperties load(String resfile, boolean force) {
		Properties pts = new Properties();
		InputStream is = StringProperties.class.getResourceAsStream(resfile);
		if (is == null) {
			if (force) {
				throw new FsException("res:" + resfile);
			}
		} else {
			try {
				pts.load(is);
			} catch (IOException e) {
				throw FsException.toRtE(e);
			}
		}

		StringProperties rt = new StringProperties(resfile);
		rt.setProperties(pts);
		return rt;

	}

	private static Map<String, String> toMap(Properties pts) {
		Map<String, String> rt = new HashMap<String, String>();
		for (Object ko : pts.keySet()) {
			String key = (String) ko;
			String value = pts.getProperty(key);
			rt.put(key, value);
		}
		return rt;
	}

	public StringProperties setProperties(Properties pts) {
		this.setProperties(toMap(pts));
		return this;
	}

	public long getPropertyAsLong(String key, long def) {
		String s = this.getProperty(key);
		if (s == null) {
			return def;
		}
		return Long.parseLong(s);
	}

	public boolean getPropertyAsBoolean(String key, boolean def) {
		String rt = this.getProperty(key);

		if (rt == null) {
			return def;
		}
		return Boolean.valueOf(rt);
	}

	public int getPropertyAsInt(String key, int def) {
		String s = this.getProperty(key);
		if (s == null) {
			return def;
		}
		return Integer.parseInt(s);
	}

	public <T> T getPropertyAsNewInstance(String key) {
		return this.getPropertyAsNewInstance(key, false);
	}

	public <T> T getPropertyAsNewInstance(String key, boolean force) {
		Class<T> cls = this.getPropertyAsClass(key, force);
		if (cls == null) {
			return null;
		}
		T rt = ClassUtil.newInstance(cls);

		return rt;
	}

	public <T> Class<T> getPropertyAsClass(String key) {
		return this.getPropertyAsClass(key, false);
	}

	public <T> Class<T> getPropertyAsClass(String key, boolean force) {
		String cName = this.getProperty(key, false);

		if (cName == null) {
			if (force) {
				throw new FsException("force:" + key + ", in res:" + this.res);
			}
			return null;
		}
		try {
			return ClassUtil.forName(cName);
		} catch (FsException fe) {
			if (fe.getCause() instanceof ClassNotFoundException) {
				throw new FsException("class not found for key:" + key + ",value:" + cName + ",in resource:"
						+ this.res);
			}
			throw fe;
		}
	}

	public List<String> keyListStartWith(String prefix) {
		List<String> rt = new ArrayList<String>();
		List<String> kl = this.keyList();
		for (String s : kl) {
			if (s.startsWith(prefix)) {
				rt.add(s);
			}
		}
		return rt;
	}

	// prefix.1.keySuffix=key
	// prefix.1.valueSuffix=value
	// key=value
	// sort by 1.2.3...
	public List<PropertiesI<String>> parseAsPropertiesList(final String prefix, String[] suffixs) {
		Map<String, String>[] valueMaps = new HashMap[suffixs.length];
		Set<String> kset = new HashSet<String>();// prefix.1=Map:{sufix1=value1
		for (int i = 0; i < suffixs.length; i++) {
			valueMaps[i] = new HashMap<String, String>();
		}

		for (String key : this.keyListStartWith(prefix)) {

			String value = this.getProperty(key);
			for (int i = 0; i < suffixs.length; i++) {
				String suffix = suffixs[i];
				if (key.endsWith(suffix)) {
					String key2 = key.substring(0, key.length() - suffix.length());
					kset.add(key2);// NOTE this is the key
					valueMaps[i].put(key2, value);

				}
			}
		}
		List<PropertiesI<String>> rt = new ArrayList<PropertiesI<String>>();
		// sort key set
		String[] kA = kset.toArray(new String[kset.size()]);
		Arrays.sort(kA, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {

				String m1 = o1.substring(prefix.length());
				String m2 = o2.substring(prefix.length());
				m1 = m1.substring(1, m1.length() - 1);
				m2 = m2.substring(1, m2.length() - 1);

				int i1 = Integer.parseInt(m1);
				int i2 = Integer.parseInt(m2);

				return i1 - i2;

			}

		});
		//
		for (String key2 : kA) {
			PropertiesI<String> pts = new MapProperties<String>();
			for (int i = 0; i < suffixs.length; i++) {
				String key = suffixs[i];
				String value = valueMaps[i].get(key2);
				pts.setProperty(key, value);

			}
			rt.add(pts);
		}
		return rt;

	}

}
