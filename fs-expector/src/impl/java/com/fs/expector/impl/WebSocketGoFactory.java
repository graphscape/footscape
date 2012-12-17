/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.expector.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.expector.api.EventDispatcherI;
import com.fs.expector.api.data.EventGd;
import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.wrapper.WsMsgReceiveEW;
import com.fs.expector.impl.gobject.WebSoketGoImpl;
import com.fs.expector.impl.support.FacadeAwareConfigurableSupport;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.WsManagerI;

/**
 * @author wu
 * 
 */
public class WebSocketGoFactory extends FacadeAwareConfigurableSupport
		implements WsListenerI {

	protected static String PK_WSGO = "_webSocketGo";
	protected EventDispatcherI eventEngine;

	protected CodecI messageCodec;

	protected DgQueueI<EventGd> global;

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		//
		this.messageCodec = this.container.find(CodecI.FactoryI.class, true)
				.getCodec(MessageI.class);
		// listen to the wsmanagerI
		WsFactoryI wf = this.container.find(WsFactoryI.class, true);
		WsManagerI wsm = wf.getManager("default", true);// TODO new wsm
		wsm.addListener(this);
		
		this.global = this.facade.getGlogalEventQueue();//
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		WebSocketGoI wso = new WebSoketGoImpl(ws, this.messageCodec);
		setWso(ws, wso);
		this.facade.getWebSocketGridedObjectManager().addGridedObject(wso);// register
																			// the
																			// web
																			// socket
																			// to
																			// Grid.
		
		wso.sendReady();//
	}

	public static void setWso(WebSocketI ws, WebSocketGoI wso) {
		ws.setProperty(PK_WSGO, wso);
	}

	public static WebSocketGoI getWso(WebSocketI ws) {
		return (WebSocketGoI) ws.getProperty(PK_WSGO);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String ms) {
		//
		JSONArray js = (JSONArray) JSONValue.parse(ms);
		MessageI msg = (MessageI) this.messageCodec.decode(js);
		String path = msg.getHeader("path");
		String wsId = getWso(ws).getId();// assign the ws id.
		WsMsgReceiveEW ew = WsMsgReceiveEW.valueOf(path, wsId, msg);
		// send to global event queue
		this.global.offer(ew.getTarget());

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		//

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		//

	}

}
