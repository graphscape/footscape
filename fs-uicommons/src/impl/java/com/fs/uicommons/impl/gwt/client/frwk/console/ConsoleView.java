/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.console;

import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicore.api.gwt.client.Console;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ConsoleView extends SimpleView implements ManagableI {

	protected ManagedModelI managed;

	protected Element table;

	protected ElementWrapper tbody;

	protected int size;

	protected int maxSize = 100;

	/**
	 * @param ctn
	 */
	public ConsoleView(String name, ContainerI ctn) {
		super(name, ctn);
		table = DOM.createTable();
		this.tbody = new ElementWrapper(DOM.createTBody());
		DOM.appendChild(table, this.tbody.getElement());
		DOM.appendChild(this.element, table);//

	}

	/*
	 * Nov 11, 2012
	 */
	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);
		Console.getInstance().addMessageCallback(
				new UiCallbackI<Object, Boolean>() {

					@Override
					public Boolean execute(Object t) {
						//
						ConsoleView.this.processConsoleMessage(t);//
						return null;
					}
				});
	}

	/**
	 * New message is arrived. NOTE,this method should not generate any event,or
	 * to call the Console.pringln();Otherwise,there will be dead loop. Nov 11,
	 * 2012
	 */
	private void processConsoleMessage(Object msg) {
		Element tr = DOM.createTR();
		DOM.appendChild(this.tbody.getElement(), tr);

		Element td = DOM.createTD();
		String text = "" + msg;
		td.setInnerText(text);
		DOM.appendChild(tr, td);

		this.size++;
		this.getElement().setAttribute("scrollTop", "10000px");// Scroll to
																// bottom.
		this.shrink();

	}

	public void shrink() {
		// shrink
		while (true) {
			if (this.size <= this.maxSize) {
				break;
			}
			com.google.gwt.dom.client.Element ele = this.tbody.getElement()
					.getFirstChildElement();
			if (ele == null) {
				break;
			}
			ele.removeFromParent();
			this.size--;
		}

	}

	/*
	 * Nov 11, 2012
	 */
	@Override
	public String getManager() {
		//
		return BossModelI.M_POPUP;//
	}

	/*
	 * Nov 11, 2012
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

}
