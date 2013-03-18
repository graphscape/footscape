/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.list;

import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class ListImpl extends LayoutSupport implements ListI {

	protected TableWrapper table;

	protected boolean vertical;

	protected TRWrapper firstTRForHorizental;

	public ListImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		super(c, name, DOM.createDiv(), pts);
		table = new TableWrapper();
		this.elementWrapper.append(table);
		this.vertical = (Boolean) this.getProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);
		if (!this.vertical) {
			this.firstTRForHorizental = this.table.addTr();
		}
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
		if (this.vertical) {
			TRWrapper tr = this.table.addTr();
			TDWrapper td = tr.addTd();
			td.append(ceo.getElement());
			ceo.setProperty("externalParentElement", tr.getElement());
		} else {
			TDWrapper td = this.firstTRForHorizental.addTd();
			td.append(ceo.getElement());
			ceo.setProperty("externalParentElement", td.getElement());
		}
	}
	@Override
	protected void onRemoveChild(Element ele, WidgetI cw) {
		Element epe = (Element)cw.getProperty("externalParentElement");
		epe.removeFromParent();
		super.onRemoveChild(ele, cw);
	}

}
