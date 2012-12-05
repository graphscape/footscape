/**
 * Jul 17, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;
import com.fs.uicore.impl.gwt.client.support.ListJCCSupport;

/**
 * @author wu
 * 
 */
public class ErrorInfosJCC extends ListJCCSupport<ErrorInfosData> {

	/** */
	public ErrorInfosJCC(FactoryI f) {
		super("ES", ErrorInfosData.class, f);

	}

	/* */
	@Override
	protected ErrorInfosData convert(ObjectListData l) {
		ErrorInfosData rt = new ErrorInfosData();
		for (int i = 0; i < l.size(); i++) {
			rt.add((ErrorInfoData) l.get(i));
		}
		return rt;

	}

	/* */
	@Override
	protected ObjectListData convert(ErrorInfosData t) {
		ObjectListData rt = new ObjectListData();
		for (ErrorInfoData e : t.getErrorInfoList()) {
			rt.add(e);
		}
		return rt;

	}

}
