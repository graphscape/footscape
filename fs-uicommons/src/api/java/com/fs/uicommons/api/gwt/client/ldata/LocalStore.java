/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package com.fs.uicommons.api.gwt.client.ldata;

import com.fs.uicommons.api.gwt.client.html5.storage.LocalStorageJSO;

/**
 * @author wu
 * 
 */
public class LocalStore {

	private static LocalStore ME = new LocalStore();

	private LocalStorageJSO lsj = LocalStorageJSO.getInstance();

	public static LocalStore getInstance() {
		return ME;
	}

	protected String getValue(String key) {
		return this.lsj.getValue(key);
	}

	public LocalData getData(String key) {
		LocalData rt = new LocalData(key, this);
		return rt;
	}

	/**
	 * Dec 9, 2012
	 */
	protected void setValue(String key, String value) {
		this.lsj.setValue(key, value);
	}
}
