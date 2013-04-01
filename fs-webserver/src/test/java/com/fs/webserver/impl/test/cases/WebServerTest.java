/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.test.cases;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.lang.FsException;
import com.fs.webserver.impl.WebServerSPI;
import com.fs.webserver.impl.test.TestServlet;
import com.fs.webserver.impl.test.cases.support.TestBase;

/**
 * @author wu
 *         http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals
 *         .html#d5e43
 */
public class WebServerTest extends TestBase {

	public void testHttps() throws Exception {
		
		String host = "localhost";
		int port = Configuration.properties(WebServerSPI.class.getName()+".WEB_SERVER.ssl").getPropertyAsInt("port",-1);
		if(port == -1){
			throw new FsException("config not found");
		}
		URI uri = new URI("https://localhost:" + port + "/testapp/testsevlet/do");
		HttpGet httpget = new HttpGet(uri);

		TrustStrategy easyStrategy = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] certificate, String authType)
					throws CertificateException {
				return true;
			}
		};

		SSLSocketFactory sslSocketFactory = new SSLSocketFactory(easyStrategy,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https", port, sslSocketFactory));
		ClientConnectionManager ccm = new PoolingClientConnectionManager(schemeRegistry);

		HttpClient httpclient = new DefaultHttpClient(ccm);

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		Reader r = new InputStreamReader(is);
		StringBuffer sb = this.read(r);
		Assert.assertEquals(TestServlet.RESPONSE, sb.toString());//
	}

	public void testHttp() throws Exception {
		// URIBuilder builder = new URIBuilder();
		String host = "localhost";
		URI uri = new URI("http://localhost:" + 8080 + "/testapp/testsevlet/do");
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
