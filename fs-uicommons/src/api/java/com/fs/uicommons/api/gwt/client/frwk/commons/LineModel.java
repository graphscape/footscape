package com.fs.uicommons.api.gwt.client.frwk.commons;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

public class LineModel extends ModelSupport {

	public static final Location L_TEXT = ModelI.L_DEFAULT;

	public LineModel(String name) {
		super(name);
	}

	public String getText() {
		return (String) this.getValue(L_TEXT);
	}

}
