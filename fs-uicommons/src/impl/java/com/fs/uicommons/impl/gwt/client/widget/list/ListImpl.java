/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fs.uicommons.api.gwt.client.UiCommonsConstants;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
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

	protected Comparator comparator;

	protected List<WidgetI> childList;
	
	public ListImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		super(c, name, DOM.createDiv(), pts);
		
		this.childList = new ArrayList<WidgetI>();
		table = new TableWrapper();
		this.elementWrapper.append(table);
		this.vertical = (Boolean) this.getProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);
		this.comparator = (Comparator) this.getProperty(ListI.PK_COMPARATOR, null);

		String cname = "vlist";
		if (!this.vertical) {
			this.firstTRForHorizental = this.table.addTr();
			cname = "hlist";
		}
		this.element.addClassName(cname);
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
		
		WidgetI before = null;
		int idx = this.childList.size();//last as default
		if(this.comparator != null){
			
			for( int i=0; i<this.childList.size(); i++){
				WidgetI wi = this.childList.get(0);
				
				if( this.comparator.compare( (WidgetI)ceo,wi) < 0){
					idx = i;
					before = wi;
					break;
				}
			}
		}
		this.childList.add(idx, (WidgetI)ceo);
		String cname = (String)ceo.getProperty(ListI.PK_LIST_ITEM_CLASSNAME);
		if (this.vertical) {
			
			TRWrapper beforeTr = before==null?null:(TRWrapper) before.getProperty("externalParentElement");
			
			TRWrapper tr = this.table.insertTrBefore(beforeTr);
			
			TDWrapper td = tr.addTd();
			td.append(ceo.getElement());
			td.addClassName(UiCommonsConstants.CN_VLIST_ITEM);
			
			if(cname!= null){
				td.addClassName(cname);
			}
			
			ceo.setProperty("externalParentElement", tr);
		} else {//
			TDWrapper td = this.firstTRForHorizental.addTd();
			td.append(ceo.getElement());
			if(cname!= null){
				td.addClassName(cname);
			}
			td.addClassName(UiCommonsConstants.CN_HLIST_ITEM);
			ceo.setProperty("externalParentElement", td);
		}
	}
	
	

	@Override
	protected void onRemoveChild(Element ele, WidgetI cw) {
		ElementWrapper epe = (ElementWrapper) cw.getProperty("externalParentElement");
		//TRWrapper,or TDWrapper
		epe.getElement().removeFromParent();
		this.childList.remove(cw);//NOTE,equals
		super.onRemoveChild(ele, cw);
	}

}
