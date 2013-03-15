/**
 *  Feb 8, 2013
 */
package com.fs.uicommons.impl.gwt.client.widget.tab;

import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class HorizentalTabberLayout extends TabberLayout {

	private Element headerTd;

	private Element middleTd;

	private Element bodyTd;

	/**
	 * @param c
	 * @param element
	 */
	public HorizentalTabberLayout(Element element, boolean rev) {
		super(element, "layout-horizental",rev);

		Element table = this.element;
		Element tbody = DOM.createTBody();
		DOM.appendChild(table, tbody);

		this.headerTd = this.createTrTd(tbody, "position-header");

		this.middleTd = this.createTrTd(tbody, "position-middle");

		this.bodyTd = this.createTrTd(tbody, "position-body");
	}

	public void setStack(StackWI cw) {
		DOM.appendChild(this.bodyTd, cw.getElement());//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.impl.gwt.client.widget.tab.TabberLayout#addTab(com.fs
	 * .uicommons.api.gwt.client.widget.tab.TabWI)
	 */
	@Override
	public void addTab(TabWI cw) {
		// TODO Auto-generated method stub
		if(this.isReverse){
			this.headerTd.insertFirst(cw.getElement());
		}else{
			
			this.headerTd.appendChild(cw.getElement());//
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.impl.gwt.client.widget.tab.TabberLayout#removeTab(com
	 * .fs.uicommons.api.gwt.client.widget.tab.TabWI)
	 */
	@Override
	public void removeTab(TabWI cw) {
		cw.getElement().removeFromParent();
	}

}
