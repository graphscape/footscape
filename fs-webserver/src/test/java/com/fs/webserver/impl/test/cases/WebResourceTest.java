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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fs.webserver.impl.test.cases.support.TestBase;

/**
 * @author wu
 *         http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals
 *         .html#d5e43
 */
public class WebResourceTest extends TestBase {
	public static final String FILE1 = "file1 content\n";
	public static final String FILE2 = "file2 content\n";

	public void xxtestWebResourceToBeAddedNotFound() {
		// TODO

	}

	public void testWebResourceAddedByTestSPI() throws Exception {
		// see com.fs.webserver.impl.test.TestSPI.java
		String file1 = getHttp("/testapp/testres/file1.txt");
		Assert.assertEquals(FILE1, file1);//
		String file2 = getHttp("/testapp/testres/folder1/file2.txt");
		Assert.assertEquals(FILE2, file2);//

	}

	public static String getHttp(String path) throws Exception {
		URIBuilder builder = new URIBuilder();
		String host = "localhost";
		builder.setScheme("http").setHost(host).setPort(8080).setPath(path)
				.setParameter("q", "httpclient");
		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);

		HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		Reader r = new InputStreamReader(is);
		StringBuffer sb = read(r);
		return sb.toString();
	}

	public static StringBuffer read(Reader r) throws Exception {
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
