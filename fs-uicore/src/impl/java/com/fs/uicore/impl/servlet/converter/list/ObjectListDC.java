/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet.converter.list;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;

/**
 * @author wu
 * 
 */
public class ObjectListDC extends ConverterSupport<ObjectListData, List> {

	/** */
	public ObjectListDC(ConverterI.FactoryI fa) {
		super(ObjectListData.class, List.class, fa);

	}

	/* */
	@Override
	public List<Object> convert(ObjectListData f) {
		List<Object> rt = new ArrayList<Object>();
		for (int i = 0; i < f.size(); i++) {
			UiData da = f.get(i);
			Object value = null;
			if (da != null) {

				ConverterI dc = this.factory.getConverter(da.getClass(),
						Object.class, false);
				value = dc.convert(da);
			}
			rt.add(value);
		}
		return rt;

	}

}
