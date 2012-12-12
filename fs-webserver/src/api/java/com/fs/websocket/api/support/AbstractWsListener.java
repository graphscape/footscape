/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.websocket.api.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsListenerI;

/**
 * @author wu
 * 
 */
public class AbstractWsListener implements WsListenerI {

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractWsListener.class);

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String ms) {
		//
		if (LOG.isDebugEnabled()) {
			LOG.debug("onMessage,ws:" + ws);
		}

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		//
		if (LOG.isDebugEnabled()) {
			LOG.debug("onException,ws:" + ws);
		}

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		//
		if (LOG.isDebugEnabled()) {
			LOG.debug("onConnect,ws:" + ws);
		}

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		//
		if (LOG.isDebugEnabled()) {
			LOG.debug("onClose,ws:" + ws + ",statusCode:" + statusCode + ",reason:" + reason);
		}

	}

}
