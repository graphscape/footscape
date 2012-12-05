/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client;

/**
 * @author wu
 * 
 */
public class UiException extends RuntimeException {

	public UiException() {
		this(null);
	}

	public UiException(String msg) {
		this(msg, null);
	}

	public UiException(String msg, Throwable t) {
		super(msg, t);
	}

}
