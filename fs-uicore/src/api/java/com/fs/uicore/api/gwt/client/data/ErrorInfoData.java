/**
 * Jul 17, 2012
 */
package com.fs.uicore.api.gwt.client.data;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ErrorInfoData extends UiData {

	private String message;

	private String source;
	
	private List<String> detail;

	public ErrorInfoData(String msg, String source) {
		this.message = msg;
		this.source = source;
		this.detail = new ArrayList<String>();

	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the detail
	 */
	public List<String> getDetail() {
		return detail;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ErrorInfoData)) {
			return false;
		}
		ErrorInfoData ei = (ErrorInfoData) o;
		return ObjectUtil.nullSafeEquals(ei.message, this.message)
				&& ObjectUtil.nullSafeEquals(ei.detail, ei.detail);
	}

	/* */
	@Override
	public String toString() {
		return this.message + ",detail:" + this.detail;

	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

}
