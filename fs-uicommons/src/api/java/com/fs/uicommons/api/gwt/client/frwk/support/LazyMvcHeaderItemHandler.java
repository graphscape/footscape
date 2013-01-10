/**
 *  Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.support;

import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class LazyMvcHeaderItemHandler implements EventHandlerI<HeaderItemEvent> {

	protected ItemModel headerItem;

	protected LazyMvcI lazyMvc;

	protected Object activeValue;

	protected String menuItem;

	public LazyMvcHeaderItemHandler(ModelI parent, String lazy) {
		this((LazyMvcI) (LazyI) parent.getLazy(lazy, true));//
	}

	public LazyMvcHeaderItemHandler(LazyMvcI mvc) {
		this(mvc, null);
	}

	public LazyMvcHeaderItemHandler(LazyMvcI mvc, String menuItem) {

		this.lazyMvc = mvc;
		this.menuItem = menuItem;
	}

	public void start(ModelI parent) {

		HeaderModelI hm = this.getFrwkModel(parent).getHeader();
		if (this.menuItem == null) {// top menu item
			this.headerItem = hm.addItem(this.lazyMvc.getName(),
					HeaderModelI.ItemModel.P_RIGHT);
		} else {
			// second level menu item.
			this.headerItem = hm.addItem(new String[] { this.menuItem,
					this.lazyMvc.getName() }, HeaderModelI.ItemModel.P_RIGHT);
		}

		// call init at control.
		this.headerItem.addHandler(HeaderItemEvent.TYPE, this);
	}

	@Override
	public void handle(HeaderItemEvent e) {
		this.lazyMvc.get().focus(true);

	}

	protected FrwkModelI getFrwkModel(ModelI parent) {
		FrwkModelI fc = parent.getTopObject().getChild(FrwkModelI.class, true);

		return fc;
	}

}
