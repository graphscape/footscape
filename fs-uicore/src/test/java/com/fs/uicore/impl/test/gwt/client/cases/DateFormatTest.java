/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 29, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class DateFormatTest extends TestBase {

	public void testDate() {
		String dateS = "2012-11-29T11:13:02.969+0800";
		DateData date = DateUtil.parse(dateS);

	}
}
