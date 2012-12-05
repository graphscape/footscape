/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.stack;

import com.fs.uicore.api.gwt.client.CompositeI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public interface StackWI extends CompositeI {

	public static class ItemModel extends ModelSupport {

		public static final Location L_WIDGET = Location.valueOf("_widget");

		public static final Location L_SELECTED = ModelI.L_DEFAULT;

		public static final Location L_IS_DEFAULT_ITEM = Location
				.valueOf("_defaultItem");

		/**
		 * @param name
		 */
		public ItemModel(String name) {
			super(name);
		}

		public boolean isDefaultItem() {
			return this.getValue(Boolean.class, L_IS_DEFAULT_ITEM,
					Boolean.FALSE);
		}

		public boolean isSelected() {
			return this.getDefaultValue(Boolean.TRUE);
		}

		public void trySelect(boolean sel) {
			if (this.isSelected() == sel) {
				return;
			}
			this.select(sel);
		}

		public void select(boolean sel) {

			this.setDefaultValue(sel);
		}

		public WidgetI getManagedWidget() {
			return (WidgetI) this.getValue(L_WIDGET);
		}
	}

	public ItemModel getDefaultItem(boolean force);

	public ItemModel getSelected(boolean force);

	public ItemModel insert(WidgetI child, boolean select);

	public int getSize();

}
