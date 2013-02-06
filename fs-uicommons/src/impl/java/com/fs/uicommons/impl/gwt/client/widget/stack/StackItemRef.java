/**
 *  Feb 6, 2013
 */
package com.fs.uicommons.impl.gwt.client.widget.stack;

import com.fs.uicommons.api.gwt.client.widget.stack.StackItemI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public class StackItemRef implements StackItemI {

	private StackWI stack;

	private boolean isDefault;

	private boolean isSelected;

	private WidgetI managed;

	private Path path;

	public StackItemRef(Path path, StackWI stack, WidgetI managed) {
		super();
		this.path = path;
		this.stack = stack;
		this.managed = managed;
	}

	public boolean isDefaultItem() {
		return this.isDefault;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void trySelect(boolean sel) {
		if (this.isSelected() == sel) {
			return;
		}
		this.select(sel);
	}

	@Override
	public void select(boolean sel) {
		this.isSelected = sel;
		this.stack.updateSelect(this);
	}

	public WidgetI getManagedWidget() {
		return this.managed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	public boolean isSelect() {
		// TODO Auto-generated method stub
		return this.isSelected;
	}

	@Override
	public void remove() {
		
	}
}