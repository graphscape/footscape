/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client.data.basic;

import com.fs.uicore.api.gwt.client.data.BasicData;

/**
 * @author wu
 * 
 */
public class LongData extends BasicData<Long> {

	/** */
	protected LongData(Long t) {
		super(t);

	}

	public static LongData valueOf(Long v) {
		return new LongData(v);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof LongData)) {
			return false;
		}

		return super.isEquals((LongData) obj);

	}
}
