/**
 *  Jan 15, 2013
 */
package com.fs.uicore.api.gwt.client.html5;

/**
 * @author wuzhen
 * 
 */
public final class ErrorJSO extends AbstractJSO {

	protected ErrorJSO() {

	}

	public final String getData() {
		return (String) this.getProperty("data");
	}
}
