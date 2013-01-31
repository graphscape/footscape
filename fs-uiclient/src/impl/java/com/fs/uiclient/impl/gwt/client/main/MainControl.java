/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uiclient.impl.gwt.client.tasks.TaskManager;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * @author wu
 * 
 */
public class MainControl extends ControlSupport implements MainControlI {

	/**
	 * @param name
	 */
	public MainControl(ContainerI c, String name) {
		super(c, name);
	}

	@Override
	protected void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();

		this.getEventBus(true).addHandler(EndpointBondEvent.TYPE, new BondHandler(this));

		this.activeTasks();

	}

	protected void activeTasks() {
		TaskManager tm = new TaskManager();
		this.child(tm);

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		//
		super.processChildModelAdd(p, cm);

	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public ExpSearchModelI getExpSearchModel() {
		//
		return null;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public ExpEditModelI getExpExitModel() {
		//
		return null;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public CooperModelI getCooperModel() {
		//
		return null;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public void openExpSearch() {
		//

	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public void openUeList() {
		//

	}

	/*
	 *Jan 31, 2013
	 */
	@Override
	public SignupView getSignupView() {
		// 
		return null;
	}

}
