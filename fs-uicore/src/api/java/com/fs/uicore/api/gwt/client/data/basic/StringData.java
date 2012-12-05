/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client.data.basic;

import com.fs.uicore.api.gwt.client.data.BasicData;

/**
 * @author wu
 * 
 */
public class StringData extends BasicData<String> {

	protected StringData(String d) {
		super(d);
	}

	/**
	 * @param value
	 * @return
	 */
	public static StringData valueOf(String value) {
		if (value == null) {
			return null;
		}
		return new StringData(value);

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof StringData)) {
			return false;
		}

		return super.isEquals((StringData) obj);

	}

}
