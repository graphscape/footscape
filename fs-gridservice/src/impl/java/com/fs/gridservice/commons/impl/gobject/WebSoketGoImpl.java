/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl.gobject;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.support.GridedObjectSupport;
import com.fs.websocket.api.WebSocketI;

/**
 * @author wu
 * 
 */
public class WebSoketGoImpl extends GridedObjectSupport implements WebSocketGoI {
	private static final Logger LOG = LoggerFactory.getLogger(WebSoketGoImpl.class);
	protected WebSocketI target;

	protected CodecI messageCodec;

	protected String terminalId;

	protected String clientId;

	/**
	 * @param ws
	 */
	public WebSoketGoImpl(WebSocketI ws, CodecI messageCodec) {
		this.target = ws;
		this.messageCodec = messageCodec;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void sendTextMessage(String msg) {
		//
		MessageI ms = new MessageSupport() {
		};
		ms.setPayload(msg);//
		this.sendMessage(ms);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void sendMessage(MessageI msg) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendMessage,wsoId:" + this.id + ",msg:" + msg);
		}
		JSONArray js = (JSONArray) this.messageCodec.encode(msg);
		String value = JSONValue.toJSONString(js);

		this.target.sendMessage(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.commons.api.gobject.WebSocketGoI#sendReady()
	 */
	@Override
	public void sendReady(String sourceMsgId, String termId, String clientId) {

		this.terminalId = termId;
		this.clientId = clientId;
		MessageI msg = new MessageSupport();
		msg.setHeader(MessageI.HK_SOURCE_ID, sourceMsgId);
		msg.setHeader(MessageI.HK_PATH, P_SERVER_IS_READY.toString());
		msg.setPayload("terminalId", termId);
		msg.setPayload("clientId", clientId);
		
		this.sendMessage(msg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.gobject.WebSocketGoI#getTerminalId(boolean
	 * )
	 */
	@Override
	public String getTerminalId(boolean b) {
		if (this.terminalId == null && b) {
			throw new FsException("no terminal binding for websocket:" + this.getId());
		}
		return this.terminalId;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public String getClientId(boolean force) {
		if (this.clientId == null && force) {
			throw new FsException("no client binding for websocket:" + this.getId());
		}
		return this.clientId;
	}

}
