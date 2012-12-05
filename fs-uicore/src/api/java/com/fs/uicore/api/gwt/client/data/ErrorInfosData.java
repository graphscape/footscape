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
public class ErrorInfosData extends UiData {
	private List<ErrorInfoData> errorInfoList = new ArrayList<ErrorInfoData>();

	public List<ErrorInfoData> getErrorInfoList() {
		return this.errorInfoList;
	}

	public boolean hasError() {
		return !this.errorInfoList.isEmpty();
	}

	public ErrorInfosData add(ErrorInfoData ei) {
		this.errorInfoList.add(ei);
		return this;
	}

	/* */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ErrorInfosData)) {
			return false;
		}
		return ObjectUtil.nullSafeEquals(this.errorInfoList,
				((ErrorInfosData) obj).errorInfoList);
	}

	/* */
	@Override
	public String toString() {
		return this.errorInfoList.toString();

	}

}
