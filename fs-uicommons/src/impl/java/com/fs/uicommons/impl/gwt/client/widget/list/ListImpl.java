/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.list;

import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.google.gwt.user.client.DOM;

/**
 * @author wuzhen
 * 
 */
public class ListImpl extends LayoutSupport implements ListI {

	protected TableWrapper table;

	public ListImpl(String name) {
		super(name, DOM.createDiv());
		table = new TableWrapper();
		this.elementWrapper.append(table);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.list.ListI#getSize()
	 */
	@Override
	public int getSize() {
		return this.getChildWidgetList().size();
	}

	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		TRWrapper tr = this.table.addTr();
		TDWrapper td = tr.addTd();
		td.append(ceo.getElement());
	}
}
