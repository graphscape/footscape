/**
 * Jul 14, 2012
 */
package com.fs.uicore.api.gwt.client.commons;

import java.util.List;
import java.util.Map;

import com.fs.uicore.api.gwt.client.core.UiCallbackI;

/**
 * @author wu
 * 
 */
public interface UiPropertiesI<T> {

	public static interface PropertyResolverI<T> extends
			UiCallbackI<UiPropertiesI<T>, T> {

	}

	public List<String> keyList();

	public void setProperties(UiPropertiesI<T> pts);

	public T getProperty(String pname);

	public void setProperty(String key, T value);

	public T getProperty(String key, boolean force);

	public T getProperty(String key, T def);

	public Map<String, T> getAsMap();
	
	public void setProperties(Map<String,T> map);

}
