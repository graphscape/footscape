/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet.converter.list;

import java.util.List;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;

/**
 * @author wu
 * 
 */
public class ObjectListCD extends ConverterSupport<List, ObjectListData> {

	/** */
	public ObjectListCD(ConverterI.FactoryI fa) {
		super(List.class, ObjectListData.class, fa);

	}

	/* */
	@Override
	public ObjectListData convert(List t) {
		ObjectListData rt = new ObjectListData();

		for (Object o : t) {
			UiData value = null;
			if (o != null) {
				ConverterI dc = this.factory.getConverter(o.getClass(),
						UiData.class, false);
				value = (UiData) dc.convert(o);
			}
			rt.add(value);
		}
		return rt;

	}

}
