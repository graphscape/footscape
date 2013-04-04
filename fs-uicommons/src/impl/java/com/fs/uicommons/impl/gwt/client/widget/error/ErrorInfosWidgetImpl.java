/**
 * 
 */
package com.fs.uicommons.impl.gwt.client.widget.error;

import com.fs.uicommons.api.gwt.client.widget.error.ErrorInfosWidgetI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class ErrorInfosWidgetImpl extends WidgetSupport implements ErrorInfosWidgetI {

	protected Element tbody;

	/**
	 * @param ele
	 */
	public ErrorInfosWidgetImpl(ContainerI c, String name) {
		super(c, name, DOM.createDiv());
		Element table = DOM.createTable();
		DOM.appendChild(this.element, table);
		this.element.addClassName("error-infos");
		tbody = DOM.createTBody();
		DOM.appendChild(table, tbody);
		// NOTE
		//this.addAdjuster("ext", new OverflowAdjustHandler());
	}

	/**
	 * Nov 13, 2012
	 */
	public void clear() {
		while (true) {
			Element ele = this.tbody.getFirstChildElement().cast();
			// ele is TR?
			if (ele == null) {
				break;
			}
			ele.removeFromParent();//
		}

	}

	/**
	 * Nov 13, 2012
	 */
	@Override
	public void addErrorInfos(ErrorInfosData errorInfos) {
		UiClientI client = this.getClient(true);
		
		for (ErrorInfoData ei : errorInfos.getErrorInfoList()) {
			Element tr = DOM.createTR();
			DOM.appendChild(this.tbody, tr);
			
			appendMessageTD(tr, ei.getMessage(), "error-message");
			
			appendMessageTD(tr, ei.getCode(), "error-code");
			
			appendMessageTD(tr, ei.getId(), "error-id");

			
			//appendMessageTD(tr, ei.getDetail().toString(), "errorDetail");// TODO
																			// list

		}

	}

	protected void appendMessageTD(Element tr, String msg, String cname) {
		Element td = DOM.createTD();
		DOM.appendChild(tr, td);
		//
		Element div = DOM.createDiv();
		div.setInnerHTML(msg);
		div.addClassName(cname);
		DOM.appendChild(td, div);//

	}

}
