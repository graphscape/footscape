/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wuzhen
 * 
 */
public class ProfileControl extends ControlSupport {

	/**
	 * @param client
	 */
	public ProfileControl(ContainerI c, String name) {
		super(c, name);

	}

	/**
	 * Nov 17, 2012
	 */
	public void open() {
		// TODO
		new ActionEvent(this, Actions.A_PROFILE_INIT).dispatch();//
	}

	public ProfileModel getModel() {

		return (ProfileModel) this.getRootModel().find(ProfileModel.class,true);
	}

}
