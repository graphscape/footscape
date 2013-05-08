/**
 *  Dec 12, 2012
 */
package com.fs.webcomet.impl.mock;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.AClientSupport;
import com.fs.commons.api.value.PropertiesI;
import com.fs.webcomet.impl.ajax.AjaxCometServlet;

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

	protected Semaphore request;

	protected HttpClient httpclient;

	protected String sid;

	protected ExecutorService resExecutor;

	public MockAjaxClientImpl(PropertiesI<Object> pts) {
		super(pts);
		this.request = new Semaphore(2);
		ClientConnectionManager cm = new PoolingClientConnectionManager();
		this.httpclient = new DefaultHttpClient(cm);
		this.resExecutor = Executors.newFixedThreadPool(2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.client.AClientI#connect()
	 */
	@Override
	public AClientI connect() {
		try {

			HttpGet httpget = new HttpGet(uri);
			//httpget.addHeader(AjaxCometServlet.HK_ACTION, "connect");

			HttpResponse response = httpclient.execute(httpget);
			Header sidH = null;// response.getFirstHeader(AjaxCometServlet.HK_SESSION_ID);
			if (sidH == null) {
				throw new FsException("connection failed, response without a sid");
			}

			this.sid = sidH.getValue();
			// TODO ?
			HttpEntity entity = response.getEntity();

			InputStream is = entity.getContent();
			Reader r = new InputStreamReader(is);

		} catch (Exception e) {
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
		HttpGet httpget = new HttpGet(uri);
		//httpget.addHeader(AjaxCometServlet.HK_ACTION, "close");
		//httpget.addHeader(AjaxCometServlet.HK_SESSION_ID, this.sid);

		try {
			HttpResponse res = httpclient.execute(httpget);
			this.sid = null;

		} catch (Exception e) {
			throw new FsException(e);
		}
	}

	public void assertConnected() {
		if (!this.isConnected()) {
			throw new FsException("no sid");
		}
	}

	public boolean isConnected() {
		return null != this.sid;
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
		// try {
		// this.request.acquire();
		// } catch (InterruptedException e1) {
		// throw new FsException(e1);
		// }

		try {
			HttpPost req = new HttpPost(uri);
			//req.addHeader(AjaxCometServlet.HK_ACTION, "message");
			//req.addHeader(AjaxCometServlet.HK_SESSION_ID, this.sid);

			StringBuffer sb = new StringBuffer();

			sb.append("[");
			sb.append("\"");
			sb.append(JSONValue.escape(msg));
			sb.append("\"");
			sb.append("]");

			StringEntity entity = new StringEntity(sb.toString());
			req.setEntity(entity);
			final HttpResponse res = httpclient.execute(req);
			this.resExecutor.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {

					MockAjaxClientImpl.this.waitResponse(res);
					return null;
				}
			});

		} catch (Exception e) {
			throw new FsException(e);
		}
		// finally {
		// this.request.release();
		// }
	}

	/**
	 * @param res
	 * @return
	 */
	protected void waitResponse(HttpResponse res) throws Exception {
		// TODO another thread
		InputStream is = res.getEntity().getContent();
		Reader r = new InputStreamReader(is);
		JSONArray jsa = (JSONArray) JSONValue.parse(r);
		for (int i = 0; i < jsa.size(); i++) {
			String msgS = (String) jsa.get(i);
			this.onMessage(msgS);
		}
		
		

	}

}
