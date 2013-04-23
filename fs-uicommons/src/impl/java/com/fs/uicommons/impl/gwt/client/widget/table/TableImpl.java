/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class TableImpl extends LayoutSupport implements TableI {

	protected HeadersI headers;

	protected BodyI body;

	/** */
	public TableImpl(ContainerI c, String name) {
		super(c,name, DOM.createTable());
		this.element.setAttribute("cellspacing", "0");
		
		this.element.setAttribute("cellspading", "0");
		
		this.headers = new HeadersImpl(this.container,this);
		this.child(this.headers);
		this.body = new BodyImpl(this.container,this);
		this.child(this.body);//
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
