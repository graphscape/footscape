/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.core.UiFilterI;
import com.fs.uicore.api.gwt.client.support.UiFilterSupport;

/**
 * @author wu
 * 
 */
public class LocalRequestFilter extends UiFilterSupport {
	/* */
	@Override
	protected UiFilterI next(Context fc) {
		boolean isLocal = fc.getRequest().isLocal();

		if (isLocal) {
			return null;//
		}
		return super.next(fc);

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void filterRequest(Context fc) {
		//

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void filterResponse(Context fc) {
		//

	}
}
