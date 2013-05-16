/**
 *  
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.api.util.ImageUrl;

import junit.framework.TestCase;

/**
 * @author wu
 * 
 */
public class ImageUrlTest extends TestCase {

	public void testParseDataImageUrl() {
		String pro = "data";
		String format = "image/png";
		String encode = "base64";
		String data = "iVBORw0KGgoAAAANSUhEUgAAAAYAAAAGCAYAAADgzO9IAAAABGdBTUEAALGPC/xhBQAAAAlwSFlzAAAOxAAADsQBlSsOGwAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAAvSURBVBhXYzx//vx/BiyACSZ297UyXBrEZiSoA6QcWRfcKJCEsuhduHEoEshuAACJUg25FTs06wAAAABJRU5ErkJggg==";

		String str = pro + ":" + format + ";" + encode + "," + data;

		ImageUrl diu = new ImageUrl(pro, format, encode, data);
		ImageUrl diu2 = ImageUrl.parse(str, true);
		assertEquals(diu, diu2);

	}
}
