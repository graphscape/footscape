/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.testsupport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.SPIManagerI;

/**
 * @author wu
 * 
 */
public class ContainerAndBridgeServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(ContainerAndBridgeServlet.class);

	private HttpClient httpclient;

	private boolean trace = true;

	// if let all the header pass though,there will be a exception
	// reported by apache http client request.
	// TODO a good enough proxy http client.
	private static Set<String> allowHeaderKeyPrefix = new HashSet<String>();
	static {
		allowHeaderKeyPrefix.add("content-type");
		allowHeaderKeyPrefix.add("accept-language");
		allowHeaderKeyPrefix.add("x-fs-");
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ClientConnectionManager ccm = new ThreadSafeClientConnManager();

		this.httpclient = new DefaultHttpClient(ccm, null);

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		String uriS = req.getRequestURI();

		URI uri;
		try {
			uri = new URI("http://localhost:8080" + uriS);
		} catch (URISyntaxException e) {
			throw new ServletException(e);
		}

		HttpPost post = new HttpPost(uri);

		for (Enumeration<String> e = req.getHeaderNames(); e.hasMoreElements();) {
			String key = e.nextElement();
			if (!this.allowHeader(key)) {// NOTE

				continue;
			}
			String value = req.getHeader(key);
			post.setHeader(key, value);//
		}

		HttpEntity reqE = new InputStreamEntity(req.getInputStream(), req.getContentLength());
		post.setEntity(reqE);

		HttpResponse response = httpclient.execute(post);

		//

		OutputStream os = res.getOutputStream();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		this.writeResponse(response, os, bos);
		// header?

		int scode = response.getStatusLine().getStatusCode();
		if (200 != scode) {
			LOG.error("status code is not 200:" + scode);
		}

		String s = new String(bos.toByteArray());
		// String path = req.getHeader(UiRequest.PATH);
		// String sid = req.getHeader(UiRequest.SESSION_ID);
		// LOG.debug("sid:" + sid + ",path:" + path + ",payloads:" + s);//

	}

	private boolean allowHeader(String key) {
		for (String k : allowHeaderKeyPrefix) {
			if (key.toLowerCase().startsWith(k)) {
				return true;
			}

		}
		return false;

	}

	private void writeResponse(HttpResponse response, OutputStream... oss) throws IOException {
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		while (true) {
			byte[] buf = new byte[1024];// TODO
			int len = is.read(buf);
			if (len == -1) {
				break;
			}
			for (OutputStream os : oss) {
				os.write(buf, 0, len);
			}
		}

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		//
		super.init(config);
		//
		String res = config.getInitParameter("container");
		if (res == null) {
			throw new ServletException("please config the container to load");
		}
		SPIManagerI.FACTORY.get().load(res);
	}

}
