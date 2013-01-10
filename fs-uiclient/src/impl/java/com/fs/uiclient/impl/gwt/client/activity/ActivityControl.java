/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uiclient.api.gwt.client.activity.ActivityControlI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.PartnerModel;
import com.fs.uiclient.impl.gwt.client.handler.action.OpenChatRoomAP;
import com.fs.uiclient.impl.gwt.client.handler.action.ActivityRefreshAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class ActivityControl extends ControlSupport implements ActivityControlI {

	/**
	 * @param name
	 */
	public ActivityControl(String name) {
		super(name);
		
		
	}

	@Override
	public void processChildModelAdd(ModelI parent, ModelI cm) {
		super.processChildModelAdd(parent, cm);
		if(cm instanceof PartnerModel){
			this.processChildParticipantModelAdd((PartnerModel)cm);
		}
	}

	/**
	 * @param cm
	 */
	private void processChildParticipantModelAdd(PartnerModel cm) {
		//
		
	}
	
	

}
