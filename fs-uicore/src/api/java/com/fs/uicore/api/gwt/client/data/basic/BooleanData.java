/**
 * Jul 20, 2012
 */
package com.fs.uicore.api.gwt.client.data.basic;

import com.fs.uicore.api.gwt.client.data.BasicData;

/**
 * @author wu
 * 
 */
public class BooleanData extends BasicData<Boolean> {

	/** */
	protected BooleanData(Boolean t) {
		super(t);

	}

	public static BooleanData valueOf(Boolean v) {
		return new BooleanData(v);
	}

}
