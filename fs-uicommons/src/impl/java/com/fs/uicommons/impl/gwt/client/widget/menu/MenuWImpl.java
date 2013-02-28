/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.menu;

import com.fs.uicommons.api.gwt.client.widget.menu.MenuItemWI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuWI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.efilter.SimpleEventFilter;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class MenuWImpl extends LayoutSupport implements MenuWI {

	/**
	 * TODO close menu when click other position.
	 * 
	 * @param ele
	 */
	protected Element ul;

	public MenuWImpl(ContainerI c, String name) {
		super(c, name, DOM.createDiv());

		this.ul = (com.google.gwt.user.client.Element) Document.get().createULElement().cast();
		DOM.appendChild(this.element, this.ul);
	}

	@Override
	public MenuItemWI addItem(String name) {

		MenuItemWI rt = new MenuItemWImpl(this.container);
		rt.setText(true, name);

		this.child(rt);
		return rt;
	}

	/*
	 * Nov 12, 2012
	 */
	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {
		//
		if (!(cw instanceof MenuItemWI)) {
			throw new UiException("node allowed child type:" + cw);
		}

		Element li = (com.google.gwt.user.client.Element) Document.get().createLIElement().cast();
		DOM.appendChild(this.ul, li);
		DOM.appendChild(li, cw.getElement());//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.menu.MenuWI#addItem(java.lang.
	 * String, com.fs.uicommons.api.gwt.client.widget.menu.MenuWI)
	 */
	@Override
	public MenuItemWI addItem(String name, MenuWI subm) {

		MenuItemWI rt = this.addItem(name);//
		rt.setProperty("_SUBMENU", subm);

		this.addHandler(SimpleEventFilter.valueOf(ClickEvent.TYPE, MenuItemWI.class, rt), //
				new EventHandlerI<ClickEvent>() {

					@Override
					public void handle(ClickEvent e) {
						MenuWImpl.this.onClick(e);
					}
				});

		return rt;
	}

	protected void onClick(ClickEvent e) {
		MenuItemWI miw = (MenuItemWI) e.getSource();
		MenuWI mi = (MenuWI) miw.getProperty("_SUBMENU");
		if (mi != null) {
			// mi.setVisible(true);// show sub munu;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.menu.MenuWI#openBy(com.fs.uicore
	 * .api.gwt.client.core.WidgetI)
	 */
	@Override
	public void openBy(WidgetI src) {
		this.setVisible(true);//
		Point topLeft = src.getElementWrapper().getAbsoluteRectangle().getBottomLeft();
		ElementWrapper body = this.getElementWrapper().getBody();// TODO move
																	// around.
		this.getElementWrapper().tryMoveInside(topLeft, body);
	}

	@Override
	public void close() {
		this.setVisible(false);// TODO remove setVisible.replace by css
								// classname.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.menu.MenuWI#getItem(java.lang.
	 * String)
	 */
	@Override
	public MenuItemWI getItem(String name) {
		throw new UiException("TODO");

	}

}
