/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.fs.uicore.impl.gwt.client.comet.ajax;

import java.util.HashMap;
import java.util.Map;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.endpoint.Address;
import com.fs.uicore.api.gwt.client.scheduler.SchedulerI;
import com.fs.uicore.api.gwt.client.support.CollectionHandler;
import com.fs.uicore.impl.gwt.client.comet.GometI;
import com.fs.uicore.impl.gwt.client.comet.GometSupport;
import com.fs.uicore.impl.gwt.client.comet.ajax.handlers.ClosedHandler;
import com.fs.uicore.impl.gwt.client.comet.ajax.handlers.ConnectedHandler;
import com.fs.uicore.impl.gwt.client.comet.ajax.handlers.DefaultClientHandler;
import com.fs.uicore.impl.gwt.client.comet.ajax.handlers.ErrorHandler;
import com.fs.uicore.impl.gwt.client.comet.ajax.handlers.MessageHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class AjaxGomet extends GometSupport {

	// See serverlet in webserver
	public static final String HK_SESSION_ID = "x-fs-ajax-sessionId";

	public static final String EVT_ONOPEN = "onopen";

	public static final String EVT_ONCLOSE = "onclose";

	public static final String EVT_ONERROR = "onerror";

	public static final String EVT_ONMESSAGE = "onmessage";

	private Address uri;

	private Resource resource;

	private JsonCallback jcb;

	protected String sid;

	protected Map<Path, ClientAjaxHandler> handlers;

	protected ClientAjaxHandler defaultHandler;

	// private Timer heartBeatTimer;

	private Map<String, CollectionHandler> handlersMap;

	private SchedulerI scheduler;

	private int requests;

	/**
	 * @param wso
	 */
	public AjaxGomet(ContainerI c, Address uri) {
		super("ajax");
		this.scheduler = c.get(SchedulerI.class, true);
		this.uri = uri;
		this.resource = new Resource(this.uri.getUri());

		jcb = new JsonCallback() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				//
				// TODO
				AjaxGomet.this.onRequestFailure(method, exception); //
			}

			@Override
			public void onSuccess(Method method, JSONValue response) {
				//

				AjaxGomet.this.onRequestSuccess(method, response);
				//
			}
		};
		//
		this.handlers = new HashMap<Path, ClientAjaxHandler>();
		this.handlers.put(AjaxMsg.CONNECT.getSubPath("success"), new ConnectedHandler(this));
		this.handlers.put(AjaxMsg.CLOSE.getSubPath("success"), new ClosedHandler(this));
		this.handlers.put(AjaxMsg.MESSAGE, new MessageHandler(this));
		this.handlers.put(AjaxMsg.ERROR, new ErrorHandler(this));

		this.defaultHandler = new DefaultClientHandler(this);
		// this.heartBeatTimer = new Timer();
		this.handlersMap = new HashMap<String, CollectionHandler>();
		this.handlersMap.put(EVT_ONCLOSE, new CollectionHandler());
		this.handlersMap.put(EVT_ONOPEN, new CollectionHandler());
		this.handlersMap.put(EVT_ONERROR, new CollectionHandler());
		this.handlersMap.put(EVT_ONMESSAGE, new CollectionHandler());

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void open() {
		AjaxMsg am = new AjaxMsg(AjaxMsg.CONNECT);
		this.doRequest(am);
	}

	protected void doRequest(AjaxMsg am) {
		this.requests++;
		JSONArray jsa = new JSONArray();
		jsa.set(0, am.getAsJsonObject());
		String text = jsa.toString();
		Method m = this.resource.post().text(text);
		// Content-Type: text/plain; charset=ISO-8859-1
		m.header("Content-Type", "application/json; charset=UTF-8");
		m.header("Content-Length", "" + len(text));
		m.header("x-fs-debug", "debug:" + am.getPath());
		// session id is in request header.
		if (this.sid != null) {
			m.header(HK_SESSION_ID, this.sid);//
		}
		m.send(jcb);
	}

	private int len(String text) {
		return text.getBytes().length;
	}

	/**
	 * @param method
	 * @param response
	 */
	protected void onRequestSuccess(Method method, JSONValue response) {

		this.requests--;
		try {

			JSONArray jsa = (JSONArray) response;
			for (int i = 0; i < jsa.size(); i++) {
				JSONObject amS = (JSONObject) jsa.get(i);

				AjaxMsg am2 = new AjaxMsg(amS);

				this.onAjaxMsg(am2);
			}
		} finally {
			this.checkToScheduleHeartBeat();
		}
	}

	private void checkToScheduleHeartBeat() {
		if (this.requests > 0) {
			return;
		}
		// only for the last request,
		// no request for now, so do a new request
		// immediately.
		// each request finish,should schedule a new request immediately.
//		if (!this.isOpen()) {
//			// if not open for some error,how to do?
//			return;
//		}
		this.scheduler.scheduleTimer(0, new HandlerI<Object>() {

			@Override
			public void handle(Object t) {
				AjaxGomet.this.sendHeartBeat();
			}
		});
		//

	}

	/**
	 * @param method
	 * @param exception
	 */
	protected void onRequestFailure(Method method, Throwable exception) {
		this.requests--;
		String data = exception.getMessage();
		this.dispatch(EVT_ONERROR, data);
	}

	/**
	 * @param am2
	 */
	private void onAjaxMsg(AjaxMsg am2) {
		Path path = am2.getPath();
		ClientAjaxHandler hdl = this.handlers.get(path);

		if (hdl == null) {
			hdl = this.defaultHandler;
		}
		ClientAjaxMsgContext amc = new ClientAjaxMsgContext();
		amc.am = am2;
		hdl.handle(amc);

	}

	public void assertConnected() {
		if (!this.isConnected()) {
			throw new UiException("no sid");
		}
	}

	public boolean isConnected() {
		return null != this.sid;
	}

	/**
	 * Only send if connected,else stop the heart beat.
	 */
	protected boolean sendHeartBeat() {

		AjaxMsg am = new AjaxMsg(AjaxMsg.HEART_BEAT);
		this.doRequest(am);
		return true;
	}

	public void closeByError() {
		this.doClose();
	}

	/**
	 * 
	 */
	public void closedByServer() {
		this.doClose();
	}

	public void doClose() {
		this.sid = null;
		this.dispatch(EVT_ONCLOSE, "");
	}

	/**
	 * @param sid2
	 */
	public void conected(String sid2) {
		this.sid = sid2;
		this.dispatch(EVT_ONOPEN, this);
	}

	/**
	 * 
	 */
	public void errorFromServer(String error, String msg) {
		this.dispatch(EVT_ONERROR, error + "," + msg);
		if (AjaxMsg.ERROR_CODE_SESSION_NOTFOUND.equals(error)) {//
			this.closeByError();
		}
	}

	public void messageFromServer(String msg) {
		this.dispatch(EVT_ONMESSAGE, msg);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void close() {
		//
		AjaxMsg am = new AjaxMsg(AjaxMsg.CLOSE);
		this.doRequest(am);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void send(String jsS) {
		//
		AjaxMsg am = new AjaxMsg(AjaxMsg.MESSAGE);
		am.setProperty(AjaxMsg.PK_TEXTMESSAGE, jsS);
		this.doRequest(am);
	}

	protected void dispatch(String event, Object data) {
		CollectionHandler hs = this.getEventHandlers(event);
		hs.handle(data);
	}

	protected CollectionHandler getEventHandlers(String event) {

		CollectionHandler hs = this.handlersMap.get(event);
		if (hs == null) {
			throw new UiException("no this event:" + event);
		}
		return hs;
	}

	public void addEventHandler(String event, HandlerI h) {
		CollectionHandler hs = this.getEventHandlers(event);
		hs.addHandler(h);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onOpen(HandlerI<GometI> handler) {
		//
		this.addEventHandler(EVT_ONOPEN, handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onClose(HandlerI<String> handler) {
		//
		this.addEventHandler(EVT_ONCLOSE, handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onError(HandlerI<String> handler) {
		//
		this.addEventHandler(EVT_ONERROR, handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onMessage(HandlerI<String> handler) {
		//
		this.addEventHandler(EVT_ONMESSAGE, handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public boolean isOpen() {
		//
		return this.sid != null;
	}

}
