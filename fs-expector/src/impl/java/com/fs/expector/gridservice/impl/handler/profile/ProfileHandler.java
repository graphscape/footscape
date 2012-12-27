/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.handler.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.data.SessionGd;

/**
 * @author wu
 * 
 */
public class ProfileHandler extends ExpectorTMREHSupport {

	private static Logger LOG = LoggerFactory.getLogger(ProfileHandler.class);

	public ProfileHandler() {
		super();
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		{// submit
			ValidatorI<RequestI> vl = this.createValidator("submit");
			vl.addExpression("payloads.property['age']!=null");
			vl.addExpression("payloads.property['gender']!=null");
			vl.addExpression("payloads.property['icon']!=null");

		}

	}

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

	}

	/* */
	@Override
	public void handle(HandleContextI sc) {

		super.handle(sc);
	}

	@Handle("init")
	public void handleInit(HandleContextI hc, ResponseI res) {

		SessionGd login = this.getSession(hc, true);

		Profile pf = this.dataService.getNewest(Profile.class,
				Profile.ACCOUNTID, login.getAccountId(), false);

		PropertiesI<Object> pts = null;
		if (pf != null) {
			pts = pf.getTarget();
		}

		res.setPayload("profile", pts);// Profile is NodeI' wrapper,
										// will codec as PropertiesI

	}

	@Handle("submit")
	public void handleSubmit(RequestI req, ResponseI res, HandleContextI hc,
			ValidatorI<RequestI> vl, ValidateResult<RequestI> vr) {

		if (res.getErrorInfos().hasError()) {
			// if has error such as validate error,then not continue.
			return;
		}
		SessionGd login = this.getSession(hc, true);//
		// here the data is valid for save processing.
		Integer age = (Integer) req.getPayload("age");// just for display.
		String gender = (String) req.getPayload("gender");
		String icon = (String) req.getPayload("icon");

		Profile pts = new Profile().forCreate(this.dataService);// NOTE
		pts.setAccountId(login.getAccountId());//
		pts.setAge(age);
		pts.setGender(gender);
		pts.setIcon(icon);
		pts.save(true);

	}
}
