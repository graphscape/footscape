/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.header;

import com.fs.uicommons.api.gwt.client.Position;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class HeaderModel extends ModelSupport implements HeaderModelI {

	/**
	 * @param c
	 */
	public HeaderModel(String name) {
		super(name);

	}

	@Override
	public ItemModel addItem(String name, Position pos) {
		return this.addItem(name, pos, null);
	}

	@Override
	public ItemModel addItem(String name, Position pos, final ViewReferenceI mgd) {
		final ItemModel rt = new ItemModel(name);
		rt.setPosition(pos);
		rt.addHandler(HeaderItemEvent.TYPE,
				new EventHandlerI<HeaderItemEvent>() {

					@Override
					public void handle(HeaderItemEvent e) {
						HeaderModel.this.onItemTrigger(mgd, rt);
					}
				});
		rt.parent(this).cast();

		// exclusive trigger
		rt.addSelectHandler(new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				HeaderModel.this.onItemSelect(rt);
			}
		});

		return rt;
	}

	/**
	 * Nov 26, 2012
	 */
	protected void onItemTrigger(ViewReferenceI mgd, ItemModel rt) {
		//
		rt.select(!rt.isSelected());//
		if (mgd != null) {
			mgd.select(true);//
		}
		// change the select state

	}

	/**
	 * Nov 25, 2012
	 */
	protected void onItemSelect(ItemModel rt) {
		// disable other item
		boolean sel = rt.isSelected();
		if (!sel) {
			return;// do nothing when unselect
		}

		// selected,only one is selected,other unselect
		for (ItemModel it : this.getChildList(ItemModel.class)) {
			if (rt == it) {
				continue;
			}
			//
			it.select(false);
		}
	}

	@Override
	public ItemModel getItem(String name, boolean force) {
		return this.getChild(ItemModel.class, name, force);
	}

	/*
	 * Nov 22, 2012
	 */
	@Override
	public ItemModel getOrAdd(String name, Position pos) {
		//
		ItemModel rt = this.getItem(name, false);
		if (rt == null) {
			rt = this.addItem(name, pos);
		}
		return rt;
	}

	/*
	 * Nov 22, 2012
	 */
	@Override
	public ItemModel addItem(String[] path, Position pos) {
		//
		ItemModel rt = null;
		if (path.length == 1) {
			rt = this.addItem(path[0], pos);
		} else if (path.length == 2) {
			ItemModel parent = this.getOrAdd(path[0], pos);
			rt = parent.addItem(path[1]);
		} else {
			throw new UiException("not supported:" + path);
		}
		return rt;
	}
}
