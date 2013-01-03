/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.header;

import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;

/**
 * @author wu
 * 
 */
public class HeaderItemLazyMvc extends HeaderItemEHSupport {

	private LazyMvcI mvc;

	public HeaderItemLazyMvc(LazyMvcI lm) {
		this.mvc = lm;
	}

	@Override
	public void handle(HeaderItemEvent t) {
		this.mvc.get().focus(true);
	}

}
