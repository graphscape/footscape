/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uiclient.api.gwt.client.activity.ActivityControlI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.ParticipantModel;
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
		// this.addActionProcessor(ActivityModelI.A_REFRESH, new RefreshAP());
		this.localMap.put(ActivityModelI.A_OPEN_CHAT_ROOM, true);
		this.addActionProcessor(ActivityModelI.A_OPEN_CHAT_ROOM,
				new OpenChatRoomAP());
		//this.localMap.put(ActivityModelI.A_REFRESH, true);
		this.addActionProcessor(ActivityModelI.A_REFRESH,
				new RefreshAP());
		
	}

	@Override
	public void processChildModelAdd(ModelI parent, ModelI cm) {
		super.processChildModelAdd(parent, cm);
		if(cm instanceof ParticipantModel){
			this.processChildParticipantModelAdd((ParticipantModel)cm);
		}
	}

	/**
	 * @param cm
	 */
	private void processChildParticipantModelAdd(ParticipantModel cm) {
		//
		
	}
	
	

}
