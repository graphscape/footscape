/**
 * Jun 29, 2012
 */
package com.fs.commons.api.value.basic;

import com.fs.commons.api.value.BasicValue;

/**
 * @author wuzhen
 * 
 */
public class IntegerValue extends BasicValue<Integer> {

	/**
	 * @param t
	 */
	protected IntegerValue(Integer t) {
		super(t);
	}

	public static IntegerValue valueOf(Integer v) {
		return new IntegerValue(v);
	}
}
