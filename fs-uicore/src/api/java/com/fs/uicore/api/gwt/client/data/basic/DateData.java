/**
 * Jul 20, 2012
 */
package com.fs.uicore.api.gwt.client.data.basic;

import com.fs.uicore.api.gwt.client.data.BasicData;

/**
 * @author wu
 * 
 */
public class DateData extends BasicData<Long> {

	/** */
	protected DateData(Long t) {
		super(t);
	}

	public static DateData valueOf(Long v) {

		return v == null ? null : new DateData(v);
	}

	public String getFormated() {
		return this.value + "";// TODO
	}
}
