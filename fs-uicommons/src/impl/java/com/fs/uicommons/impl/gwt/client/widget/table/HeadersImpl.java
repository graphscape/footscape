/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.HeaderI;
import com.fs.uicommons.impl.gwt.client.widget.table.support.TableHelper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class HeadersImpl extends TableHelper implements TableI.HeadersI {

	protected Map<String, HeaderI> headerMap = new HashMap<String, HeaderI>();

	/** */
	public HeadersImpl(ContainerI c, TableImpl t) {
		super(c, DOM.createTHead(), t);
	}

	public HeaderI getHeader(String name) {
		return this.headerMap.get(name);
	}

	/* */
	@Override
	public HeaderI createHeader(String name) {
		HeaderI old = this.getHeader(name);
		if (old != null) {
			throw new UiException("duplicated header:" + name);
		}
		HeaderI rt = new HeaderImpl(this.container, this.table, name);
		this.headerMap.put(name, rt);
		return rt;

	}

	/* */
	@Override
	public List<HeaderI> createHeader(String[] names) {
		List<HeaderI> rt = new ArrayList<HeaderI>();
		for (String name : names) {
			rt.add(this.createHeader(name));
		}
		return rt;

	}

}
