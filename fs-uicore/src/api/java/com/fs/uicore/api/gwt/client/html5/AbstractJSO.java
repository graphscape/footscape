/**
 *  Jan 15, 2013
 */
package com.fs.uicore.api.gwt.client.html5;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author wuzhen
 * 
 */
public class AbstractJSO extends JavaScriptObject {

	protected AbstractJSO() {

	}

	public final String toLongString() {
		String rt = "{";
		for (String key : this.keyArray()) {
			Object v = this.getProperty(key);
			rt += key + "=" + v + ",";
		}
		rt += "}";
		return rt;
	}

	public final native Object getProperty(String key)
	/*-{
		return this[key];
	}-*/;

	public final native String[] keyArray()
	/*-{
		var rt = [];
	  	for(var key in this){
	     	rt.push(key);
	  	}
	  	return rt;
		
	}-*/;
}
