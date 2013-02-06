/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.stack;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicommons.api.gwt.client.widget.stack.StackItemI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
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

	private List<StackItemI> itemList;

	/**
	 * @param ele
	 */
	public StackWImpl(ContainerI c) {
		super(c, DOM.createDiv());
		this.itemList = new ArrayList<StackItemI>();

	}

	@Override
	public StackItemI getSelected(boolean force) {

		List<StackItemI> iml = this.itemList;
		StackItemI rt = null;
		for (StackItemI im : iml) {
			if (im.isSelect()) {
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
	public StackItemI getDefaultItem(boolean force) {

		List<StackItemI> iml = this.itemList;
		StackItemI rt = null;
		for (StackItemI im : iml) {
			if (((StackItemRef) im).isDefaultItem()) {
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
	public StackItemRef insert(Path path, WidgetI child, boolean select) {
		if (null != this.getByPath(path, false)) {
			throw new UiException("already exist:" + path);
		}
		StackItemRef rt = new StackItemRef(path, this, child);

		this.itemList.add(rt);// NOTE,rt is the child of the widget's model
		rt.select(select);
		child.setProperty("_item_path", path);
		this.child(child);

		return rt;
	}

	@Override
	public StackItemI getByPath(Path path, boolean force) {
		for (StackItemI s : this.itemList) {
			if (s.getPath().equals(path)) {
				return s;
			}
		}
		if (force) {
			throw new UiException("no item found for path:" + path);
		}
		return null;

	}

	@Override
	public void remove(Path path) {
		StackItemI si = this.getByPath(path, false);
		si.getManagedWidget().parent(null);
		this.itemList.remove(si);

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
	protected void onRemoveChild(Element ele, WidgetI cw) {
		Element ce = cw.getElement();
		ce.getParentElement().removeFromParent();
	}

	@Override
	public void updateSelect(StackItemI im) {

		// reset selected

		boolean sel = im.isSelect();//

		if (sel) {// push

			List<StackItemI> iml = this.itemList;
			for (StackItemI imm : iml) {
				if (imm != im) {// unselect all the other
								// item if its selected.
					((StackItemRef) imm).trySelect(false);//
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
