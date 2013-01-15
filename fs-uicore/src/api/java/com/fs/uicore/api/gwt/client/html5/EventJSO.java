/**
 *  Jan 15, 2013
 */
package com.fs.uicore.api.gwt.client.html5;

/**
 * @author wuzhen
 * 
 */
public final class EventJSO extends AbstractJSO {

	protected EventJSO() {

	}

	public native String getData()
	/*-{
		return this.data;
	}-*/;
}
