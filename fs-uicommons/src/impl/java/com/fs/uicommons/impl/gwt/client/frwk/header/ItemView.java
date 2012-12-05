package com.fs.uicommons.impl.gwt.client.frwk.header;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuItemWI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.efilter.ClickEventFilter;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.DOM;

public class ItemView extends LightWeightView {

	private AnchorWI anchor;

	private MenuWI menu;

	public ItemView(ContainerI ctn) {
		super("item", DOM.createSpan(), ctn);

	}

	@Override
	public ItemModel getModel() {
		return (ItemModel) this.model;
	}

	@Override
	public WidgetI model(ModelI model) {
		super.model(model);

		this.anchor = this.createLink(this.getModel());
		this.anchor.parent(this);//

		this.menu = this.factory.create(MenuWI.class);
		this.menu.setVisible(false);// init is not shown.
		this.menu.parent(this);

		this.anchor.addHandler(ClickEvent.TYPE, new HandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ItemView.this.getModel().trigger();
			}
		});

		this.model.addValueHandler(ItemModel.L_ISSELECTED,
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ItemView.this.onThisModelSelectedChanged(e);
					}
				});

		
		return this;
	}

	protected void onThisModelSelectedChanged(ModelValueEvent e) {

		// is down

		boolean sel = e.getValue(Boolean.FALSE);

		this.anchor.getElementWrapper().addAndRemoveClassName(sel,
				"selected", "unselected");
		this.tryOpenCloseMenu(sel);
	}
	
	protected boolean hasMenu(){
		return !this.menu.getChildList(MenuItemWI.class).isEmpty();		
	}
	
	protected void tryOpenCloseMenu(boolean open){
		if(open){
			if(this.hasMenu()){
				this.menu.openBy(this.anchor);//			
			}//else do nothing.
		}else {
			this.menu.close();
		}
		
	}

	@Override
	public void processChildModelAdd(ModelI p, final ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof ItemModel) {
			this.processChildAdd((ItemModel) cm);//
		}

	}

	// this is the item view itself.
	protected AnchorWI createLink(final ItemModel iw) {
		String wname = "header-item-" + iw.getName();
		AnchorWI a = this.factory.create(AnchorWI.class, wname,
				SimpleModel.valueOf(wname, iw.getName()));

		return a;
	}

	// this is the child of the item view,the menu take over the sub item.
	protected void processChildAdd(final ItemModel iw) {// second depth

		MenuItemWI mi = this.menu.addItem(iw.getName());
		mi.addHandler(new ClickEventFilter(mi), new HandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ItemView.this.onClick(iw);
			}
		});

	}
	
	

	public void onClick(ItemModel iw) {

		iw.trigger();
	}

	/*
	 *Nov 25, 2012
	 */
	@Override
	protected void processModelValue(Location loc, ValueWrapper vw) {
		super.processModelValue(loc, vw);
		if(loc.equals(ItemModel.L_DISPLAY_NAME)){
			this.anchor.getModel().setDefaultValue(vw.getValue());//TODO ValueDeliverI.
		}
	}

}
