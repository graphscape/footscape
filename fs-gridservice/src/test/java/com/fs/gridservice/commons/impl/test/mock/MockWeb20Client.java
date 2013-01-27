/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.gridservice.commons.impl.test.mock;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fs.gridservice.commons.api.mock.MockClientWrapper;

/**
 * @author wu
 *         <p>
 *         TODO
 */
public class MockWeb20Client {

	protected MockClientWrapper websocket;

	public String connect() {
		// todo
		return null;
	}

	public void test() throws Exception {
		URIBuilder builder = new URIBuilder();
		String host = "localhost";
		builder.setScheme("http").setHost(host).setPort(8080)
				.setPath("/ROOT/testsevlet/do").setParameter("q", "httpclient");
		URI uri = builder.build();
		uri = new URI("http://localhost:8080/testapp/testsevlet/do");
		HttpGet httpget = new HttpGet(uri);

		HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		Reader r = new InputStreamReader(is);
		StringBuffer sb = this.read(r);
	}

	public StringBuffer read(Reader r) throws Exception {
		StringBuffer rt = new StringBuffer();
		while (true) {
			int i = r.read();
			if (i < 0) {
				break;
			}
			rt.append((char) i);
		}
		return rt;
	}
}
