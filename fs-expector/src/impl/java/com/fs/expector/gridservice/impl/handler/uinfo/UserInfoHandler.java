/**
 *  
 */
package com.fs.expector.gridservice.impl.handler.uinfo;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class UserInfoHandler extends ExpectorTMREHSupport {

	@Handle("get")
	public void handleGet(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String accId = req.getString("accountId", true);
		Account acc = this.dataService.getNewestById(Account.class, accId, true);
		PropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperties(acc.getTarget());
		
		Profile p = this.dataService.getNewest(Profile.class, Profile.ACCOUNTID, accId, false);		
		
		if(p != null){
			pts.setProperties(p.getTarget());
		}else{
			pts.setProperty(Profile.ACCOUNTID,accId);
			pts.setProperty(Profile.BIRTHDAY, null);//
			pts.setProperty(Profile.GENDER, "n/a");
			pts.setProperty(Profile.ICON, this.efacade.getDefaultUserIconImageUrl().toString());
		}
		
		res.setPayload("userInfo", pts);// good
	}

}
