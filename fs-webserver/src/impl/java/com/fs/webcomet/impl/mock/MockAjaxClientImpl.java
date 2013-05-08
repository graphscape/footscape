/**
 *  Dec 12, 2012
 */
package com.fs.webcomet.impl.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.Semaphore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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

	protected Semaphore connected;

	protected Semaphore closed;

	protected HttpClient httpclient;

	public MockAjaxClientImpl(PropertiesI<Object> pts) {
		super(pts);
		this.httpclient = new DefaultHttpClient();
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
			httpget.addHeader(AjaxCometServlet.HK_ACTION, "connect");

			HttpResponse response = httpclient.execute(httpget);
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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.support.AClientSupport#sendMessage(java.lang.String)
	 */
	@Override
	protected void sendMessage(String msg) {
		// TODO Auto-generated method stub

	}

}
