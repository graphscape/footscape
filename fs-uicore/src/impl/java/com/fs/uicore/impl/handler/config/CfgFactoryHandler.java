/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 21, 2012
 */
package com.fs.uicore.impl.handler.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.engine.api.support.ValidatorHandlerSupport;

/**
 * @author wu
 * 
 */
public class CfgFactoryHandler extends ValidatorHandlerSupport {

	private List<String> configurationIdList;

	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		{
			ValidatorI<RequestI> vl = this.createValidator("init");
		}
		String cfgIdS = this.config.getProperty("cfgIdCsv");// client require
															// these
															// configurations
															// when init.
		String[] cfgIdA = cfgIdS.split(",");
		this.configurationIdList = Arrays.asList(cfgIdA);
	}

	/**
	 * Initia the configuration factory at client side,this method will get the
	 * configurations that configured in this handlers.
	 * 
	 * @param hc
	 * @param req
	 * @param res
	 */
	@Handle("init")
	public void handleInit(HandleContextI hc, RequestI req, ResponseI res) {
		List<Configuration> cfgL = new ArrayList<Configuration>();
		// Convert to Map's Map
		PropertiesI<PropertiesI> pts = new MapProperties();

		for (String id : this.configurationIdList) {
			Configuration cfg = Configuration.properties(id);
			cfgL.add(cfg);
			//Configuration cannot be through RMI,so convert to MapProperties
			//cfg will be convert to as ProperteisI:_O.
			PropertiesI<String> cfgPts = MapProperties.valueOf(cfg.getAsMap());//
			pts.setProperty(id, cfgPts);//
		}

		res.setPayload(pts);
	}
}
