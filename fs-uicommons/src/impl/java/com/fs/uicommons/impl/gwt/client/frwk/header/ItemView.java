package com.fs.uicommons.impl.gwt.client.frwk.header;

import com.fs.uicommons.api.gwt.client.event.HeaderItemDisplayNameUpdateEvent;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
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

	public ItemView(ContainerI ctn, Path path) {
		super("item", DOM.createSpan(), ctn);
		this.path = path;

		this.anchor = this.createLink();
		this.anchor.parent(this);//

		this.menu = this.factory.create(MenuWI.class);
		this.menu.setVisible(false);// init is not shown.
		this.menu.parent(this);

		this.anchor.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ItemView.this.select(true);
			}
		});
	}

	/**
	 * Jan 13, 2013
	 */
	protected void onHeaderItemDisplayNameUpdateEvent(HeaderItemDisplayNameUpdateEvent t) {
		//
		String dname = ((HeaderModelI.ItemModel) t.getSource()).getDisplayName();
		this.anchor.getModel().setDefaultValue(dname);// TODO ValueDeliverI.
	}

	public void select(boolean sel) {

		this.anchor.getElementWrapper().addAndRemoveClassName(sel, "selected", "unselected");
		this.tryOpenCloseMenu(sel);
		new HeaderItemEvent(this, this.path).dispatch();
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

	// this is the item view itself.
	protected AnchorWI createLink() {

		String wname = "header-item-" + this.path.toString('-');
		AnchorWI a = this.factory.create(AnchorWI.class, wname,
				SimpleModel.valueOf(wname, this.path.toString()));

		return a;
	}

}
