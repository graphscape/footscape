/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.jetty.websocket;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.webserver.api.WebSocketI;

/**
 * @author wuzhen
 * 
 */
public class WebSocketFactoryImpl extends ConfigurableSupport implements
		WebSocketI.FactoryI {

	@Override
	public void addWebSocket(WebSocketI ws) {
		if (this.isAttached()) {
			throw new FsException("not supported for online add");
		}
		
	}

}
