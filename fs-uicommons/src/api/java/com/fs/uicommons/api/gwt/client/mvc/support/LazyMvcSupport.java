/**
 *  Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wuzhen
 * 
 */
public abstract class LazyMvcSupport implements LazyMvcI {

	protected String name;

	protected ModelI parent;

	protected Mvc mvc;

	public LazyMvcSupport(ModelI parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isGot() {
		return this.mvc != null;
	}

	@Override
	public Mvc get() {
		if (this.isGot()) {
			return this.mvc;
		}
		ModelI m = this.createModel(name);
		ContainerI ctn = this.parent.getContainer();
		ViewI v = this.createView(name, ctn);
		ControlI c = this.createControl(name);
		this.mvc = new Mvc(m, v, c);
		this.mvc.start(this.parent);
		return this.mvc;
	}

	protected abstract ModelI createModel(String name);

	protected abstract ViewI createView(String name, ContainerI c);

	protected abstract ControlI createControl(String name);

}
