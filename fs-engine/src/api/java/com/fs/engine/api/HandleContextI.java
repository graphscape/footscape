/**
 * Jun 11, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.context.ContextI;
import com.fs.commons.api.filter.FilterI;

/**
 * @author wuzhen
 * 
 */
public interface HandleContextI extends ContextI {

	public FilterI.Context<RequestI, ResponseI> getFilterContext();

	public RequestI getRequest();

	public ResponseI getResponse();

}
