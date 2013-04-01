/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.test.cases;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fs.webserver.impl.test.TestServlet;
import com.fs.webserver.impl.test.cases.support.TestBase;
import com.sun.mail.iap.Protocol;

/**
 * @author wu
 *         http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals
 *         .html#d5e43
 */
public class WebServerTest extends TestBase {

	public void test() throws Exception {
		this.doTest(false);
		this.doTest(true);
	}

	private void doTest(boolean ssl) throws Exception {
		// URIBuilder builder = new URIBuilder();
		String host = "localhost";
		String prot = ssl ? "https" : "http";
		int port = ssl ? 8443 : 8080;
		URI uri = new URI(prot + "://localhost:" + port + "/testapp/testsevlet/do");
		HttpGet httpget = new HttpGet(uri);
		HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		Reader r = new InputStreamReader(is);
		StringBuffer sb = this.read(r);
		Assert.assertEquals(TestServlet.RESPONSE, sb.toString());//
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
