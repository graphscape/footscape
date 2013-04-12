package com.fs.uicommons.impl.gwt.client.frwk.header;

import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuItemWI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.event.HideEvent;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtMouseOutHandler;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtMouseOverHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.DOM;

public class ItemView extends LightWeightView {

	private AnchorWI anchor;

	private MenuWI menu;

	private Path path;

	private boolean selected;

	public ItemView(ContainerI ctn, Path path) {
		super(ctn, "item", DOM.createSpan());
		this.path = path;

		this.anchor = this.factory.create(AnchorWI.class);
		this.anchor.getElement().addClassName("header-item");
		this.anchor.parent(this);//
		this.setDisplayText(true, this.path.toString());//
		this.menu = this.factory.create(MenuWI.class);
		this.menu.setVisible(false);// init is not shown.
		this.menu.parent(this);

		this.anchor.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ItemView.this.onClick();
			}
		});

		this.addGwtEventHandler(com.google.gwt.event.dom.client.MouseOutEvent.getType(),
				new GwtMouseOutHandler() {
					protected void handleInternal(com.google.gwt.event.dom.client.MouseOutEvent evt) {
						ItemView.this.onGwtMouseOut(evt);
					}

				});
		this.addGwtEventHandler(com.google.gwt.event.dom.client.MouseOverEvent.getType(),
				new GwtMouseOverHandler() {
					protected void handleInternal(com.google.gwt.event.dom.client.MouseOverEvent evt) {
						ItemView.this.onGwtMouseOver(evt);
					}

				});
		this.menu.addHandler(HideEvent.TYPE, new EventHandlerI<HideEvent>() {

			@Override
			public void handle(HideEvent t) {
				ItemView.this.onMenuHide();
			}
		});

		this.anchor.getElementWrapper().addClassName("header-item" + path.toString('-'));
	}

	/**
	 * Mar 30, 2013
	 */
	protected void onGwtMouseOut(MouseOutEvent evt) {
		if (this.hasMenu() && this.menu.isVisible()) {
			// menu is shown, so wait the menu hide event
			return;
		}
		this.select(false);
	}

	protected void onMenuHide() {
		this.select(false);
	}

	protected void onGwtMouseOver(MouseOverEvent evt) {
		this.select(true);
	}

	public MenuItemWI getOrAddMenuItem(final String name) {

		MenuItemWI rt = this.menu.getItem(name);
		if (rt != null) {
			return rt;
		}
		rt = this.menu.addItem(name);

		rt.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent t) {
				ItemView.this.onMenuItemClick(name);
			}
		});
		return rt;
	}

	protected void onMenuItemClick(String name) {
		new HeaderItemEvent(this, HeaderItemEvent.TYPE.getAsPath().concat(this.path.getSubPath(name)))
				.dispatch();

	}

	/**
	 * Jan 13, 2013
	 */
	public void setDisplayText(boolean toloc, String txt) {
		//
		if (toloc) {
			txt = this.getClient(true).localized(txt);
		}

		this.anchor.setDisplayText(txt);// TODO ValueDeliverI.
	}

	private void onClick() {
		this.select(true);
		new HeaderItemEvent(this, HeaderItemEvent.TYPE.getAsPath().concat(this.path)).dispatch();
	}

	public void select(boolean sel) {
		if (this.selected == sel) {
			return;
		}
		this.selected = sel;

		this.tryOpenCloseMenu(sel);

		this.anchor.getElementWrapper().addAndRemoveClassName(sel, "selected", "unselected");

	}

	protected boolean hasMenu() {
		return !this.menu.getChildList(MenuItemWI.class).isEmpty();
	}

	protected void tryOpenCloseMenu(boolean open) {
		if (open) {
			if (this.hasMenu()) {
				this.menu.openBy(this.anchor);//
			}// else do nothing.
		} else {
			this.menu.close();
		}

	}

}
