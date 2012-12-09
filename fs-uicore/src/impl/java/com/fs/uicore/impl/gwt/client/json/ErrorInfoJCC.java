/**
 * Jul 17, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.impl.gwt.client.support.PropertiesJCCSupport;

/**
 * @author wu
 *         <p>
 *         <code>
 * ["_O",{"_ERROR_INFO_S":["_ES",[["_E",{"message":["_S","validate failed"],"detail":["_L",[]],"source":["_S","payloads.property['nick']!=null"]}]]]}]
 * </code>
 */
public class ErrorInfoJCC extends PropertiesJCCSupport<ErrorInfoData> {

	public static final String CODE = "code";

	public static final String MESSAGE = "message";

	public static final String DETAIL = "detail";

	/** */
	public ErrorInfoJCC(FactoryI f) {
		super("E", ErrorInfoData.class, f);

	}

	/* */
	@Override
	protected ErrorInfoData convert(ObjectPropertiesData l) {
		StringData code = (StringData) l.getProperty(CODE);
		StringData message = (StringData) l.getProperty(MESSAGE);
		ObjectListData dl = (ObjectListData) l.getProperty(DETAIL);

		ErrorInfoData rt = new ErrorInfoData(code == null ? null
				: code.getValue(), message == null ? null : message.getValue());// TODO
																				// null
		for (int i = 0; i < dl.size(); i++) {
			StringData sd = (StringData) dl.get(i);
			rt.getDetail().add(sd.getValue());// TODO null?
		}

		return rt;

	}

	/* */
	@Override
	protected ObjectPropertiesData convert(ErrorInfoData t) {
		ObjectPropertiesData rt = new ObjectPropertiesData();
		rt.setProperty(MESSAGE, StringData.valueOf(t.getMessage()));
		rt.setProperty(CODE, StringData.valueOf(t.getCode()));
		ObjectListData ld = new ObjectListData();
		for (String d : t.getDetail()) {
			ld.add(StringData.valueOf(d));
		}

		rt.setProperty(DETAIL, ld);

		return rt;

	}

}
