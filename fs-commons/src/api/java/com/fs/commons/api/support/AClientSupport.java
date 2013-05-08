/**
 *  Dec 12, 2012
 */
package com.fs.commons.api.support;

import java.net.URI;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public abstract class AClientSupport implements AClientI {

	private static final Logger LOG = LoggerFactory.getLogger(AClientSupport.class);

	public static String PK_URI = "uri";
	public static String PK_NAME = "name";
	public static String PK_ENGINE = "engine";
	public static String PK_CODEC = "codec";

	protected URI uri;

	protected String name;

	protected MessageServiceI engine;

	protected CodecI codec;

	protected long lastSendMessageTs;

	public AClientSupport(PropertiesI pts) {

		this.name = (String) pts.getProperty(PK_NAME, true);
		this.uri = (URI) pts.getProperty(PK_URI, true);
		this.engine = (MessageServiceI) pts.getProperty(PK_ENGINE);
		this.codec = (CodecI) pts.getProperty(PK_CODEC, true);
	}

	protected void onMessage(String message) {
		try {
			JSONArray ser = (JSONArray) JSONValue.parse(message);
			MessageI msg = (MessageI) this.codec.decode(ser);
			ResponseI res = this.engine.service(msg);

			res.assertNoError();
		} catch (Throwable t) {
			LOG.error("onWebSocketText error", t);
		}
	}

	public void sendMessage(MessageI msg) {

		JSONArray jsm = (JSONArray) this.codec.encode(msg);//
		String code = jsm.toJSONString();
		this.sendMessage(code);
		this.lastSendMessageTs = System.currentTimeMillis();

	}

	protected abstract void sendMessage(String msg);

	/*
	 * Jan 26, 2013
	 */
	@Override
	public String getName() {
		//
		return this.name;
	}

	/*
	 * Jan 26, 2013
	 */
	@Override
	public void addHandler(Path p, HandlerI<MessageContext> mh) {
		this.addHandler(p, false, mh);
	}

	@Override
	public void addHandler(Path p, boolean strict, HandlerI<MessageContext> mh) {
		this.engine.getDispatcher().addHandler(p, strict, mh);
	}

	@Override
	public int getIdleTime() {
		long now = System.currentTimeMillis();
		return (int) (now - this.lastSendMessageTs);
	}

}
