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

	private String code;// error code?

	private List<String> detail;

	public ErrorInfoData(String code, String message) {
		this.code = code;
		this.message = message;
		this.detail = new ArrayList<String>();

	}

	public boolean isCode(String code){
		return this.code.equals(code);
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
		return "code:" + this.code + ",msg:" + this.message + ",detail:"
				+ this.detail;

	}

	/**
	 * @return the source
	 */
	public String getCode() {
		return code;
	}

}
