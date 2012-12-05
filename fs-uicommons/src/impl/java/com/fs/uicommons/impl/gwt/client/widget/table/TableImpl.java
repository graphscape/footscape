/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class TableImpl extends LayoutSupport implements TableI {

	protected HeadersI headers;

	protected BodyI body;

	/** */
	public TableImpl(String name) {
		super(name, DOM.createTable());

		this.headers = new HeadersImpl(this);
		this.child(this.headers);
		this.body = new BodyImpl(this);
		this.child(this.body);//
	}

	@Override
	public WidgetI model(ModelI model) {
		super.model(model);

		this.factory.initilize(this.headers, new SimpleModel("unkown"));
		this.factory.initilize(this.body, new SimpleModel("unkown"));

		return this;
	}

	/* */
	@Override
	public HeadersI getHeaders() {

		return this.headers;

	}

	/* */
	@Override
	public BodyI getBody() {

		return this.body;

	}

}
