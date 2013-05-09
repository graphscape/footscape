/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.fs.uicore.impl.gwt.client.comet.ws;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.html5.WebSocketJSO;
import com.fs.uicore.impl.gwt.client.comet.GometI;
import com.fs.uicore.impl.gwt.client.comet.GometSupport;

/**
 * @author wu
 * 
 */
public class AjaxGomet extends GometSupport {

	private String uri;

	/**
	 * @param wso
	 */
	public AjaxGomet(String uri) {
		super("ajax");
		this.uri = uri;
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void open() {

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void close() {
		//

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void send(String jsS) {
		//

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onOpen(HandlerI<GometI> handler) {
		//

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onClose(HandlerI<String> handler) {
		//

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onError(HandlerI<String> handler) {
		//

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onMessage(HandlerI<String> handler) {
		//

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public boolean isOpen() {
		//
		return false;
	}

}
