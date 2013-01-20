/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 29, 2012
 */
package com.fs.dataservice.core.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class ElasticTimeFormat {
	protected static final DateFormat dateFormat;
	static {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Nov 29, 2012
	 */
	public static String format(Date date) {
		//
		return dateFormat.format(date);

	}

	/**
	 * Nov 29, 2012
	 */
	public static Date parse(String s) {
		//
		if (s == null) {
			return null;
		}
		s = s.replace("Z", "+0000");// NOTE
		try {
			return dateFormat.parse(s);
		} catch (ParseException e) {
			throw new FsException(e);
		}
	}

}
