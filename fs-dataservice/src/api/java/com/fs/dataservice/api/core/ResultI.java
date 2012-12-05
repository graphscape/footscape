/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.api.core;

import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;

public interface ResultI<R extends ResultI<R, T>, T> extends
		PropertiesI<Object> {

	public static final String PK_DEFAULT = "_default";

	public static final String PK_ERRORINFOS = "_errorInfos";

	public T get(String key, boolean force);

	public T get(boolean force);

	public void set(T value);

	public ErrorInfos getErrorInfo();

	public boolean hasError();

	public DataServiceI getDataService();

	public R assertNoError();

	public <X> X cast();
}