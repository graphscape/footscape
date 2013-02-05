/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.stack;

import com.fs.uicore.api.gwt.client.CompositeI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface StackWI extends CompositeI {

	public static class ItemModel {

		private StackWI stack;
		
		private boolean isDefault;
		
		private boolean isSelected;
		
		private WidgetI managed;
		
		public ItemModel(StackWI stack,WidgetI managed) {
			super();
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

		public void select(boolean sel) {
			this.isSelected = sel;
			this.stack.updateSelect(this);
		}

		public WidgetI getManagedWidget() {
			return this.managed;
		}
	}

	public ItemModel getDefaultItem(boolean force);

	public ItemModel getSelected(boolean force);

	public ItemModel insert(WidgetI child, boolean select);

	public int getSize();
	
	public void updateSelect(ItemModel im);

}
