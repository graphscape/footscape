/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl.terminal.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.gridservice.commons.api.support.FacadeAwareConfigurableSupport;
import com.fs.gridservice.commons.impl.terminal.TerminalFactory;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.WsManagerI;

/**
 * @author wu
 * 
 */
public class TerminalFactoryWebSocketListener extends FacadeAwareConfigurableSupport implements WsListenerI {

	private static final Logger LOG = LoggerFactory.getLogger(TerminalFactoryWebSocketListener.class);

	protected TerminalFactory<WebSocketI> tfactory;

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		//
		WsFactoryI wf = this.container.find(WsFactoryI.class, true);
		WsManagerI wsm = wf.getManager("default", true);// TODO new wsm
		wsm.addListener(this);
		this.tfactory = new WebSocketTerminalFactory(this.container, this.facade);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {

		this.tfactory.onConnect(ws);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		//
		LOG.error("exception got for ws:" + ws.getId(), t);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {

		this.tfactory.onClose(ws);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String ms) {
		this.tfactory.onMessage(ws, ms);
	}

}
