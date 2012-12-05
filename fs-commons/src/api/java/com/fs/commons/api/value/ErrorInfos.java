/**
 * Jul 17, 2012
 */
package com.fs.commons.api.value;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.lang.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ErrorInfos implements ValueI {
	private List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();

	public List<ErrorInfo> getErrorInfoList() {
		return this.errorInfoList;
	}

	public boolean hasError() {
		return !this.errorInfoList.isEmpty();
	}

	public ErrorInfos addAll(ErrorInfos eis) {
		for (ErrorInfo ei : eis.getErrorInfoList()) {
			this.add(ei);
		}
		return this;
	}

	public ErrorInfos add(String msg) {
		return this.add(new ErrorInfo(msg));
	}

	public ErrorInfos add(ErrorInfo ei) {
		this.errorInfoList.add(ei);
		return this;
	}

	/* */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ErrorInfos)) {
			return false;
		}
		return ObjectUtil.nullSafeEquals(this.errorInfoList,
				((ErrorInfos) obj).errorInfoList);
	}

	/* */
	@Override
	public String toString() {
		return this.errorInfoList.toString();

	}

}
