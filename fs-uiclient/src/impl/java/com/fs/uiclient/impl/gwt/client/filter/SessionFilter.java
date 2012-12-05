/**
 * Jul 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.filter;

import com.fs.uiclient.impl.gwt.client.Constants;
import com.fs.uicommons.api.gwt.client.mvc.UiSession;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.support.UiFilterSupport;

/**
 * @author wu
 * 
 */
public class SessionFilter extends UiFilterSupport {

	private UiClientI client;

	public SessionFilter(UiClientI client) {
		this.client = client;
	}

	/* */
	@Override
	protected void filterRequest(Context fc) {

		UiSession s = (UiSession) client.getProperty(Constants.SESSION);
		String sid = s == null ? null : s.getId();//
		if (sid != null) {
			fc.getRequest().setHeader(Constants.X_FS_SESSION_ID, sid);
		}
	}

	/* */
	@Override
	protected void filterResponse(Context fc) {
		ErrorInfosData eis = fc.getResponse().getErrorInfos();
		// TODO session timeout.
		// TODO session should not be timeout. session should be recover at
		// server side,except other reason?

	}

}
