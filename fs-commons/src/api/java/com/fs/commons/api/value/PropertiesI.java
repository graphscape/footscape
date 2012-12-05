/**
 * Jun 22, 2012
 */
package com.fs.commons.api.value;

import java.util.List;
import java.util.Map;

/**
 * @author wu
 * 
 */
public interface PropertiesI<T> extends ValueI {
	public void setProperty(String key, T value);

	public void setProperty(Map.Entry<String, T> entry);

	public T getProperty(String key);

	public T getProperty(String key, boolean force);

	public boolean getPropertyAsBoolean(String key, boolean def);

	public List<String> keyList();

	public void setProperties(Map<String, T> map);

	public void setProperties(Object... keyValues);

	public void setProperties(PropertiesI<T> pts);

	public Map<String, T> getAsMap();

	public PropertiesI<T> convert(String[] from, boolean[] force, String[] to);

	public boolean isContainsSameProperties(Object... kvs);

	public boolean isContainsSameProperties(PropertiesI<T> kvs);

}
