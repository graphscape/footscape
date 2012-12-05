/**
 * Jul 19, 2012
 */
package com.fs.uiclient.impl.gwt.client.filter;

import com.fs.uiclient.api.gwt.client.Constants;
import com.fs.uicore.api.gwt.client.core.UiFilterI;
import com.fs.uicore.api.gwt.client.support.UiFilterSupport;

/**
 * @author wu
 * 
 */
public class LocalActionFilter extends UiFilterSupport {

	/* */
	@Override
	protected void filterRequest(Context fc) {

	}

	/* */
	@Override
	protected void filterResponse(Context fc) {

	}

	/* */
	@Override
	protected UiFilterI next(Context fc) {
		boolean isLocal = fc.getRequest().getHeaders()
				.getPropertyAsBoolean(Constants.IS_LOCAL, false);

		if (isLocal) {
			return null;//
		}
		return super.next(fc);

	}

}
