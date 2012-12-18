/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.EventDispatcherI;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.wrapper.WsMsgReceiveEW;
import com.fs.gridservice.commons.impl.gobject.WebSoketGoImpl;
import com.fs.gridservice.commons.impl.support.FacadeAwareConfigurableSupport;
import com.fs.gridservice.core.api.objects.DgQueueI;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.WsManagerI;

/**
 * @author wu
 * 
 */
public class WebSocketGoFactory extends FacadeAwareConfigurableSupport implements WsListenerI {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketGoFactory.class);
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
		this.messageCodec = this.container.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);
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
		if (LOG.isDebugEnabled()) {
			LOG.debug("onConnected,wsoId:" + wso.getId() + ",wsId:" + ws.getId());
		}
		String oid = getWso(ws).getId();

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
		if (LOG.isDebugEnabled()) {

			LOG.debug("onMessage,wsId:" + ws.getId() + ",ms:" + ms);
		}
		JSONArray js = (JSONArray) JSONValue.parse(ms);
		MessageI msg = (MessageI) this.messageCodec.decode(js);
		String path = msg.getHeader("path");
		String wsoId = getWso(ws).getId();// assign the ws id.
		WsMsgReceiveEW ew = WsMsgReceiveEW.valueOf(path, wsoId, msg);
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
