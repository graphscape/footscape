/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.webcomet.api.support;

import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.util.StringUtil;
import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.CometListenerI;

/**
 * @author wu
 * 
 */
public class CometListenerAdaptor implements CometListenerI {

	protected static final Logger LOG = LoggerFactory.getLogger(CometListenerAdaptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.websocket.api.WsListenerI#onMessage(com.fs.websocket.api.WebSocketI
	 * , java.io.Reader)
	 */
	@Override
	public void onMessage(CometI ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.api.CometListenerI#onConnect(com.fs.webcomet.api.CometI)
	 */
	@Override
	public void onConnect(CometI ws) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.api.CometListenerI#onMessage(com.fs.webcomet.api.CometI, java.lang.String)
	 */
	@Override
	public void onMessage(CometI ws, String ms) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.api.CometListenerI#onException(com.fs.webcomet.api.CometI, java.lang.Throwable)
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.api.CometListenerI#onClose(com.fs.webcomet.api.CometI, int, java.lang.String)
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		// TODO Auto-generated method stub
		
	}

}
