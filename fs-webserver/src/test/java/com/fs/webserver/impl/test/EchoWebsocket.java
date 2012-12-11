/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.test;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class EchoWebsocket extends WebSocketAdapter {

	@Override
	public void onWebSocketText(String message) {

		try {
			getBlockingConnection().write(message);
		} catch (IOException e) {
			throw new FsException(e);
		}
	}
}
