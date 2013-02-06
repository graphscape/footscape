/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.stack;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class StackWImpl extends LayoutSupport implements StackWI {

	private List<ItemModel> itemList;

	/**
	 * @param ele
	 */
	public StackWImpl(ContainerI c) {
		super(c, DOM.createDiv());
		this.itemList = new ArrayList<ItemModel>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.stack.StackWI#getSelected()
	 */
	@Override
	public ItemModel getSelected(boolean force) {

		List<ItemModel> iml = this.itemList;
		ItemModel rt = null;
		for (ItemModel im : iml) {
			if (im.isSelected()) {
				if (rt != null) {
					throw new UiException("bug,more than one item is selected");
				}
				rt = im;

			}
		}

		if (force && rt == null) {
			throw new UiException("no selected item in stack");
		}
		return rt;

	}

	@Override
	public ItemModel getDefaultItem(boolean force) {

		List<ItemModel> iml = this.itemList;
		ItemModel rt = null;
		for (ItemModel im : iml) {
			if (im.isDefaultItem()) {
				if (rt != null) {
					throw new UiException("bug,more than one item is default");
				}
				rt = im;

			}
		}

		if (force && rt == null) {
			throw new UiException("no default item in stack");
		}
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.stack.StackWI#insert(com.fs.uicore
	 * .api.gwt.client.core.WidgetI)
	 */
	@Override
	public ItemModel insert(WidgetI child, boolean select) {

		ItemModel rt = new ItemModel(this, child);

		this.itemList.add(rt);// NOTE,rt is the child of the widget's model
		rt.select(select);
		this.child(child);

		return rt;
	}

	/*
	 * Nov 10, 2012
	 */
	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {
		Element ele = DOM.createDiv();
		DOM.appendChild(ele, cw.getElement());//
		DOM.appendChild(this.element, ele);
	}

	@Override
	public void updateSelect(ItemModel im) {

		// reset selected

		boolean sel = im.isSelected();//

		if (sel) {// push

			List<ItemModel> iml = this.itemList;
			for (ItemModel imm : iml) {
				if (imm != im) {// unselect all the other
								// item if its selected.
					imm.trySelect(false);//
				}
			}
		} else {// unselect,do nothing.
				// TODO may be to find the item to be selected.
				// this.getSelected(false);

		}

		ElementWrapper ew = im.getManagedWidget().getElementWrapper();

		String cname = "position-selected";
		String cname2 = "position-unselected";

		ew.addClassName(sel ? cname : cname2);// selected
		ew.removeClassName(sel ? cname2 : cname);// unselected

	}

	@Override
	public int getSize() {
		return this.itemList.size();
	}

}
