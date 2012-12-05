/**
 * Jun 29, 2012
 */
package com.fs.commons.api.value.basic;

import com.fs.commons.api.value.BasicValue;

/**
 * @author wuzhen
 * 
 */
public class StringValue extends BasicValue<String> {

	/**
	 * @param t
	 */
	protected StringValue(String t) {
		super(t);
	}

	public static StringValue valueOf(String v) {
		return new StringValue(v);
	}
}
