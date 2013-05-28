/**
 *  Dec 12, 2012
 */
package com.fs.webcomet.impl.mock;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.AClientSupport;
import com.fs.commons.api.value.PropertiesI;
import com.fs.webcomet.impl.ajax.AjaxCometServlet;
import com.fs.webcomet.impl.ajax.AjaxMsg;
import com.fs.webcomet.impl.mock.handlers.ClosedHandler;
import com.fs.webcomet.impl.mock.handlers.ConnectedHandler;
import com.fs.webcomet.impl.mock.handlers.DefaultClientHandler;
import com.fs.webcomet.impl.mock.handlers.ErrorHandler;
import com.fs.webcomet.impl.mock.handlers.MessageHandler;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockAjaxClientImpl extends AClientSupport {

	private static final Logger LOG = LoggerFactory.getLogger(MockAjaxClientImpl.class);

	protected Semaphore connected;

	protected HttpClient httpclient;

	protected String sid;

	protected Map<Path, ClientAjaxHandler> handlers;

	protected ClientAjaxHandler defaultHandler;

	private Timer heartBeatTimer;

	public MockAjaxClientImpl(PropertiesI<Object> pts) {
		super(pts);
		this.connected = new Semaphore(0);
		ClientConnectionManager cm = new PoolingClientConnectionManager();
		this.httpclient = new DefaultHttpClient(cm);
		this.handlers = new HashMap<Path, ClientAjaxHandler>();
		this.handlers.put(AjaxMsg.CONNECT.getSubPath("success"), new ConnectedHandler(this));
		this.handlers.put(AjaxMsg.CLOSE.getSubPath("success"), new ClosedHandler(this));
		this.handlers.put(AjaxMsg.MESSAGE, new MessageHandler(this));
		this.handlers.put(AjaxMsg.ERROR, new ErrorHandler(this));

		this.defaultHandler = new DefaultClientHandler(this);
		this.heartBeatTimer = new Timer();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.client.AClientI#connect()
	 */
	@Override
	public AClientI connect() {
		if (this.sid != null) {
			throw new FsException("connected already!");
		}
		AjaxMsg am = new AjaxMsg(AjaxMsg.CONNECT);
		this.doRequest(am);
		// wait the connect is ok.
		try {
			this.connected.acquire();
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.client.AClientI#close()
	 */
	@Override
	public void close() {
		AjaxMsg am = new AjaxMsg(AjaxMsg.CLOSE);

		this.doRequest(am);
	}

	public void assertConnected() {
		if (!this.isConnected()) {
			throw new FsException("no sid");
		}
	}

	public boolean isConnected() {
		return null != this.sid;
	}

	protected void doRequest(AjaxMsg am) {

		try {
			HttpPost req = new HttpPost(uri);
			// req.addHeader(AjaxCometServlet.HK_ACTION, "message");
			// req.addHeader(AjaxCometServlet.HK_SESSION_ID, this.sid);

			req.setHeader("Content-Type", "application/json; charset=UTF-8");
			// only one element,but also in array.
			if (this.sid != null) {
				req.setHeader(AjaxCometServlet.HK_SESSION_ID, this.sid);
			}

			JSONObject json = new JSONObject();
			json.putAll(am.getAsMap());
			JSONArray arr = new JSONArray();
			arr.add(json);

			StringEntity entity = new StringEntity(arr.toJSONString());
			req.setEntity(entity);

			// execute is here
			final HttpResponse res = httpclient.execute(req);
			StatusLine sl = res.getStatusLine();
			int scode = sl.getStatusCode();
			if (scode != 200) {
				throw new FsException("status code error,code:" + scode + ",reason:" + sl.getReasonPhrase()+",uri:"+this.uri);
			}
			// process response,
			InputStream is = res.getEntity().getContent();
			Reader r = new InputStreamReader(is);
			JSONArray jsa = (JSONArray) JSONValue.parse(r);
			for (int i = 0; i < jsa.size(); i++) {
				JSONObject amS = (JSONObject) jsa.get(i);

				AjaxMsg am2 = new AjaxMsg(amS);

				this.onAjaxMsg(am2);
			}

			// timer for heart beat.
			long delay = 0;// no need to delay more time,unless some error
							// situation.
			// TODO cancle the old task if not executed.
			this.heartBeatTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					MockAjaxClientImpl.this.sendHeartBeat();
				}
			}, delay);
		} catch (Exception e) {
			throw new FsException(e);
		}
	}

	/**
	 * 
	 */
	protected void sendHeartBeat() {
		AjaxMsg am = new AjaxMsg(AjaxMsg.HEART_BEAT);
		this.doRequest(am);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.support.AClientSupport#sendMessage(java.lang.String)
	 */
	@Override
	protected void sendMessage(String msg) {
		this.assertConnected();
		AjaxMsg am = new AjaxMsg(AjaxMsg.MESSAGE);
		am.setProperty(AjaxMsg.PK_TEXTMESSAGE, msg);
		this.doRequest(am);
	}

	/**
	 * 
	 */
	public void closedByServer() {
		this.sid = null;
	}

	/**
	 * @param sid2
	 */
	public void conected(String sid2) {
		this.sid = sid2;
		this.connected.release();
	}

	/**
	 * 
	 */
	public void errorFromServer() {

	}

	public void messageFromServer(String msg) {
		this.onMessage(msg);
	}

}
