/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.simulator;

import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class UserSimulator implements UserSimulatorI {
	private UiClientI client;
	private ModelI rootModel;
	private RootI root;

	public UserSimulator(ContainerI c) {
		this.client = c.get(UiClientI.class, true);
		this.root = this.client.getRoot();
		this.rootModel = this.client.getRootModel();
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void start() {
		SessionModelI sm = this.rootModel.find(SessionModelI.class, true);
		sm.addValueHandler(SessionModelI.L_IS_AUTHED,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						UserSimulator.this.onAuthed();
					}
				});

		// this.delayTestFinish(timeoutMillis);//

		// sm.setAccount(this.account);// TODO more info?

		// this.listenToTheUserExpInitBeforeAuth();//

		sm.setAuthed(true);
	}

	protected void onAuthed() {
		System.out.println("authed ");
	}
}
