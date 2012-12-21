/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client.data.basic;

import com.fs.uicore.api.gwt.client.data.BasicData;

/**
 * @author wu
 * 
 */
public class IntegerData extends BasicData<Integer> {

	/** */
	protected IntegerData(Integer t) {
		super(t);

	}

	public static IntegerData valueOf(Integer v) {
		return new IntegerData(v);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof IntegerData)) {
			return false;
		}

		return super.equals((IntegerData) obj);

	}
}
