package com.fs.uicore.api.gwt.client.model;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

public class ProxyModel extends ModelSupport implements ModelI {

	protected ModelI target;

	public ProxyModel(ModelI target) {
		super("proxy-" + target.getName());

		this.target = target;
	}

}
