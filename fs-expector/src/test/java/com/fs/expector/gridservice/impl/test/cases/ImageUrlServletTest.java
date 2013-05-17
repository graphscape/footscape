/**
 * All right is from Author of the file,to be explained in comming days.
 * May 17, 2013
 */
package com.fs.expector.gridservice.impl.test.cases;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fs.commons.api.util.ImageUrl;
import com.fs.expector.dataservice.api.ExpectorDsFacadeI;
import com.fs.expector.gridservice.impl.test.cases.support.TestBase;
import com.fs.webcomet.api.WebCometComponents;

/**
 * @author wu
 * 
 */
public class ImageUrlServletTest extends TestBase {

	/**
	 * @param p
	 */
	public ImageUrlServletTest() {
		super(WebCometComponents.AJAX);
	}

	public void testResImage() throws Exception {
		ImageUrl iu = ImageUrl.parse(ExpectorDsFacadeI.IMG_PROTOCOL_RES + ":" + ";"
				+ ",/open/resource/imgs/user_icon.png", true);

		String imgS = URLEncoder.encode(iu.toString());

		String host = "localhost";
		URI uri = new URI("http://localhost:" + 8080 + "/gs/imgs/" + imgS);//

		HttpGet httpget = new HttpGet(uri);
		HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = httpclient.execute(httpget);
		StatusLine sline = response.getStatusLine();
		int scode = sline.getStatusCode();

		assertEquals("" + sline.getReasonPhrase(), 200, scode);//
		Header ctype = response.getFirstHeader("Content-Type");
		assertNotNull(ctype);
		assertEquals("image/png", ctype.getValue());
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

	}

}
