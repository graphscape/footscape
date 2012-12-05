/**
 * Jul 13, 2012
 */
package com.fs.webserver.impl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wu
 * 
 */
public class DateUtil {
	private static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd.HH.mm.ss.SSS");

	public static String getNowFormated() {
		return FORMAT.format(new Date());
	}
}
