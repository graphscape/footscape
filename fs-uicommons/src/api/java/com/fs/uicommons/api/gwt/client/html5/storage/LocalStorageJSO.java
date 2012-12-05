/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 1, 2012
 */
package com.fs.uicommons.api.gwt.client.html5.storage;

import com.fs.uicore.api.gwt.client.UiException;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author wu
 *         <p>
 *         http://www.w3schools.com/html/html5_webstorage.asp
 */
public final class LocalStorageJSO extends JavaScriptObject {

	protected LocalStorageJSO() {

	}

	public static LocalStorageJSO getInstance() {
		return getInstance(false);
	}

	public static LocalStorageJSO getInstance(boolean force) {
		LocalStorageJSO rt = getInstanceInternal();
		if (rt == null && force) {
			throw new UiException("not support Storage ");
		}
		return rt;
	}

	private native static LocalStorageJSO getInstanceInternal()/*-{
																
																if(typeof(Storage)!=="undefined")
																{
																return localStorage;								
																
																}else{
																return null;
																}
																
																}-*/;

	public native String getValue(String key)/*-{
												return localStorage[key];																		
												}-*/;

	/**
	 * Dec 1, 2012
	 */
	public native void setValue(String key, String value)/*-{
															localStorage[key]=value;
															}-*/;

}
