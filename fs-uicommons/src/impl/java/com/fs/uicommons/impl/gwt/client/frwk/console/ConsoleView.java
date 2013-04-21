/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.console;

import com.fs.uicommons.api.gwt.client.frwk.ConsoleViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicore.api.gwt.client.Console;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ConsoleView extends SimpleView implements ConsoleViewI {

	protected Element table;

	protected ElementWrapper tbody;

	protected int size;

	protected int maxSize = 1000;
	
	protected UiCallbackI<Object, Boolean> handler;

	/**
	 * @param ctn
	 */
	public ConsoleView(ContainerI c) {
		super(c, "console", DOM.createDiv());
		this.element.addClassName("console");
		table = DOM.createTable();
		this.tbody = new ElementWrapper(DOM.createTBody());
		DOM.appendChild(table, this.tbody.getElement());
		DOM.appendChild(this.element, table);//
		this.handler = new UiCallbackI<Object, Boolean>() {

			@Override
			public Boolean execute(Object t) {
				//
				ConsoleView.this.onConsoleLine(t);//
				return null;
			}
		};
		Console.getInstance().addMessageCallback(this.handler);
		Console.getInstance().replay(this.handler);
	}

	/**
	 * New message is arrived. NOTE,this method should not generate any event,or
	 * to call the Console.pringln();Otherwise,there will be dead loop. Nov 11,
	 * 2012
	 */
	private void onConsoleLine(Object msg) {
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
			com.google.gwt.dom.client.Element ele = this.tbody.getElement().getFirstChildElement();
			if (ele == null) {
				break;
			}
			ele.removeFromParent();
			this.size--;
		}

	}

}
