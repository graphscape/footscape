/**
 *  Jan 15, 2013
 */
package com.fs.uicore.api.gwt.client.html5;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author wuzhen
 * 
 */
public final class ErrorJSO extends AbstractJSO {

	protected ErrorJSO() {

	}

	public final DefaultJSO getData() {
		Object obj = this.getProperty("data");
		if (obj == null) {
			return null;
		}
		JavaScriptObject js = (JavaScriptObject) obj;
		DefaultJSO rt = js.cast();

		return rt;

	}
}
