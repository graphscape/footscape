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
import com.fs.uicore.api.gwt.client.support.SimpleModel;
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
	}

	public MenuItemWI getOrAddMenuItem(final String name) {

		MenuItemWI rt = this.menu.addItem(name);
		
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
		this.select(!this.selected);
	}

	public void select(boolean sel) {
		if (this.selected == sel) {
			return;
		}
		this.selected = sel;

		this.tryOpenCloseMenu(sel);

		this.anchor.getElementWrapper().addAndRemoveClassName(sel, "selected", "unselected");

		if (this.selected) {
			new HeaderItemEvent(this, HeaderItemEvent.TYPE.getAsPath().concat(this.path)).dispatch();
		}
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
