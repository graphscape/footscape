/**
 *  Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.i18n;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.expector.gridservice.impl.ExpectorGsSPI;
import com.fs.expector.gridservice.impl.util.I18nUtil;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wuzhen
 * 
 */
public class I18nHandler extends ExpectorTMREHSupport {

	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

	}

	/**
	 * Initia the configuration factory at client side,this method will get the
	 * configurations that configured in this handlers.
	 * 
	 * @param hc
	 * @param req
	 * @param res
	 */
	@Handle("refresh")
	public void handleRefresh(TerminalMsgReceiveEW ew, MessageContext hc, MessageI req, ResponseI res) {
		// Convert to Map's Map
		String locale = this.getLocale(ew);
		PropertiesI<String> pts = I18nUtil.resolveResource(locale);

		res.setPayload(pts);

	}


}
