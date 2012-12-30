/**
 *  Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.i18n;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.expector.gridservice.impl.ExpectorGsSPI;
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
	public void handleRefresh(TerminalMsgReceiveEW ew, HandleContextI hc, RequestI req, ResponseI res) {
		// Convert to Map's Map
		String locale = this.getLocale(ew);
		PropertiesI<String> pts = this.mergeResource(locale);

		res.setPayload(pts);

	}

	protected PropertiesI<String> mergeResource(String locale) {
		PropertiesI<String> rt = this.loadResource(null);
		if (locale != null) {
			PropertiesI<String> rt2 = this.loadResource(locale);
			rt.setProperties(rt2);
		}
		return rt;
	}

	protected PropertiesI<String> loadResource(String locale) {
		PropertiesI<String> rt = new MapProperties<String>();

		String id = ExpectorGsSPI.class.getName();
		id += ".I18n";
		if (locale != null) {// default configuration.
			id += "." + locale;
		}
		Configuration cfg = Configuration.properties(id);
		rt.setProperties(cfg);
		return rt;
	}

}
