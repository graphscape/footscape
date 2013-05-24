/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.handler.profile;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.util.ImageUrl;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

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
			ValidatorI<MessageI> vl = this.createValidator("submit");
			vl.addExpression(Constants.P_ERROR_PROFILE_BIRTHDAY, prefix + "['birthDay']!=null");
			vl.addExpression(Constants.P_ERROR_PROFILE_GENDER, prefix + "['gender']!=null");
			// vl.addExpression(Constants.P_ERROR_PROFILE_ICON, prefix +
			// "['icon']!=null");

		}

	}

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

	}

	/* */
	@Override
	public void handle(MessageContext sc) {

		super.handle(sc);
	}

	@Handle("init")
	public void handleInit(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {

		SessionGd login = this.getSession(ew, true);

		Profile pf = this.dataService
				.getNewest(Profile.class, Profile.ACCOUNTID, login.getAccountId(), false);

		PropertiesI<Object> pts = null;
		if (pf != null) {
			pts = pf.getTarget();
		}

		res.setPayload("profile", pts);// Profile is NodeI' wrapper,
										// will codec as PropertiesI

	}

	@Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW ew, ResponseI res, MessageContext hc,
			ValidatorI<MessageI> vl, ValidateResult<MessageI> vr) {
		MessageI req = ew.getMessage();//
		if (res.getErrorInfos().hasError()) {
			// if has error such as validate error,then not continue.
			return;
		}
		SessionGd login = this.getSession(ew, true);//
		// here the data is valid for save processing.
		Date bd = (Date) req.getPayload("birthDay", true);// just for display.
		String gender = (String) req.getPayload("gender", true);
		String icon = (String) req.getPayload("icon", false);

		ImageUrl diu = icon == null ? ImageUrl.NONE : ImageUrl.parse(icon, true);

		ImageUrl diu2 = this.efacade.saveImage(diu);//TODO rename to trySaveImage

		Profile pts = new Profile().forCreate(this.dataService);// NOTE
		pts.setAccountId(login.getAccountId());//
		pts.setBirthDay(bd);
		pts.setGender(gender);
		pts.setIcon(diu2.toString());
		pts.save(true);

	}
}
