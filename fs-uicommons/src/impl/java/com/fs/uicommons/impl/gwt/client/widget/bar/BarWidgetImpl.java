/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.bar;

import com.fs.uicommons.api.gwt.client.Position;
import com.fs.uicommons.api.gwt.client.widget.bar.BarWidgetI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 *         <p>
 *         Do not use the other widget,but use the underlying DOM element to
 *         build a widget.
 *         <p>
 *         see HorizontalPanel in gwt.
 */
public class BarWidgetImpl extends LayoutSupport implements BarWidgetI {

	private static final String CPK_LOCACTION_IN_BAR = BarWidgetImpl.class
			.getName() + "_loc";

	private Element table;
	private Element tableRow;

	private Element leftTd;

	private Element centerTd;

	private Element rightTd;

	/** */
	public BarWidgetImpl(ContainerI c,String name) {
		super(c,name, DOM.createTable());
		this.table = this.getElement();
		this.elementWrapper.setAttribute("cellspacing", "0");
		this.elementWrapper.setAttribute("cellspading", "0");

		Element body = DOM.createTBody();
		DOM.appendChild(table, body);

		tableRow = DOM.createTR();
		DOM.appendChild(body, tableRow);

		this.leftTd = DOM.createTD();
		this.leftTd.addClassName("left");
		DOM.appendChild(tableRow, this.leftTd);

		this.centerTd = DOM.createTD();
		this.centerTd.addClassName("center");
		DOM.appendChild(tableRow, this.centerTd);

		this.rightTd = DOM.createTD();
		this.rightTd.addClassName("right");
		DOM.appendChild(tableRow, this.rightTd);

	}

	private Element createAlignedTd() {
		Element td = DOM.createTD();

		return td;
	}

	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {
		Position loc = this.getLocOfChild(cw);
		Element td = null;
		if (loc.equals(BarWidgetI.P_CENTER)) {
			td = this.centerTd;
		} else if (loc.equals(BarWidgetI.P_LEFT)) {
			td = this.leftTd;
		} else if (loc.equals(BarWidgetI.P_RIGHT)) {
			td = this.rightTd;
		} else {
			throw new UiException("no this location in bar:" + loc);
		}

		DOM.appendChild(td, cw.getElement());//

	}

	@Override
	protected void onRemoveChild(Element ele, WidgetI cw) {
		throw new UiException("TODO");
	}

	public Position getLocOfChild(ElementObjectI cw) {

		return (Position) cw.getProperty(CPK_LOCACTION_IN_BAR,
				BarWidgetI.P_LEFT);

	}

	public void setLocOfChild(WidgetI cw, Position loc) {
		cw.setProperty(CPK_LOCACTION_IN_BAR, loc);
	}

	/*
	 * Nov 9, 2012
	 */
	@Override
	public void addItem(Position loc, WidgetI cw) {
		this.setLocOfChild(cw, loc);
		this.child(cw);

	}

}
