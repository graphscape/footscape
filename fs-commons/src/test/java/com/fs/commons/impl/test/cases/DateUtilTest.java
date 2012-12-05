/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 29, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.Date;

import junit.framework.TestCase;

import com.fs.commons.api.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateUtilTest extends TestCase {

	public void testDate() {
		String dateS = "2012-11-29T11:13:02.969+0800";
		Date date = DateUtil.parse(dateS);

	}
}
