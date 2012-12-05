/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.core;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;

/**
 * @author wu
 * 
 */
public interface WidgetI extends ElementObjectI {

	public static final Location IS_VISIBLE = Location.valueOf("_is_visible");

	public static interface CreaterI<T extends WidgetI> {
		public Class<T> getWidgetType();

		public T create(String name);
	}

	public static class ModelWrapper {

		private ModelI model;

		public ModelWrapper(ModelI model) {
			this.model = model;
		}

		public void setVisible(boolean v) {
			this.model.setValue(IS_VISIBLE, v);
		}

		public boolean isVisible() {
			return this.model.getValue(Boolean.class, IS_VISIBLE, Boolean.TRUE);
		}
	}

	public WidgetI model(ModelI ctx);

	public ModelI getModel();

	// public AdjusterI addAdjuster(String name);//

	// public AdjusterI addAdjuster(String name, HandlerI<ClickEvent> eh);

	@Deprecated
	// use ElementWrapper.click()
	public void _click();

}
