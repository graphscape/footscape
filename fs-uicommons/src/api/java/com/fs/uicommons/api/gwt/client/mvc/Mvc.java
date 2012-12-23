/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc;

import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.manage.util.BossUtil;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.simple.SimpleValueDeliver;

/**
 * @author wu
 * 
 */
public class Mvc {

	public static Location L_VIEW_OPENED = Location.valueOf("__VIEW_OPEND");

	protected ModelI model;
	protected ViewI view;
	protected ControlI control;

	protected ManagedModelI managed;

	public Mvc(ViewI v) {
		this(null, v);
	}

	public Mvc(ModelI m) {
		this(m, null, null);
	}

	public Mvc(ModelI m, ViewI v) {
		this(m, v, null);
	}

	public Mvc(ModelI m, ControlI c) {
		this(m, null, c);
	}

	public Mvc(ModelI m, ViewI v, ControlI c) {
		this.model = m;
		this.view = v;
		this.control = c;
	}

	public static Mvc valueOf(ModelI model, ViewI view) {
		return new Mvc(model, view, null);
	}

	public Mvc start(ModelI parent) {
		return this.start(parent, null);
	}

	public Mvc start(ModelI parent, WidgetI pview) {
		if (this.model != null) {
			if (this.model.getParent() == null) {
				this.model.parent(parent);
			}
		}
		if (this.view != null) {
			this.view.model(this.model);
			//
			if (pview != null) {// managed by parent view.
				this.view.parent(pview);
			} else {// managed by boss

				this.managed = BossUtil.manage(this.model, this.view);

				SimpleValueDeliver<Boolean, Boolean> sd = new SimpleValueDeliver<Boolean, Boolean>(
						this.managed, ManagedModelI.L_SELECTED, this.model, Mvc.L_VIEW_OPENED);
				sd.mapDefaultDirect();
				sd.start();
			}
		}
		if (this.control != null) {
			this.control.model(this.model);

			this.getControlManager(parent).addControl(this.control);//
		}
		return this;
	}

	public ControlManagerI getControlManager(ModelI model) {
		return model.getClient(true).getChild(ControlManagerI.class, true);
	}

	/**
	 * @return the model
	 */
	public <T extends ModelI> T getModel() {
		return (T) model;
	}

	/**
	 * @return the view
	 */
	public <T extends ViewI> T getView() {
		return (T) view;
	}

	/**
	 * @return the control
	 */
	public <T extends ControlI> T getControl() {
		return (T) control;
	}

	public ManagedModelI getManaged() {
		return this.managed;
	}

	/**
	 * Nov 23, 2012
	 */
	public void focus(boolean b) {
		if (this.managed == null) {
			return;
		}
		this.managed.select(b);
	}

}
