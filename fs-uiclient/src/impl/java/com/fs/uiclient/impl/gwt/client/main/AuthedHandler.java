/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 25, 2012
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.impl.gwt.client.activities.ActivitiesControl;
import com.fs.uiclient.impl.gwt.client.activities.ActivitiesModel;
import com.fs.uiclient.impl.gwt.client.cooper.CooperControl;
import com.fs.uiclient.impl.gwt.client.cooper.CooperModel;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicommons.api.gwt.client.mvc.support.LazyMcSupport;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class AuthedHandler implements HandlerI<ModelValueEvent> {

	MainControl mc;

	public AuthedHandler(MainControl mc) {

	}

	public void start() {
		ModelI rootM = this.mc.getClient(true).getRootModel();//
		SessionModelI sm = rootM.getChild(SessionModelI.class, true);//

		sm.addAuthedHandler(this);
	}

	/*
	 * Nov 25, 2012
	 */
	@Override
	public void handle(ModelValueEvent e) {
		ModelI rootM = this.mc.getClient(true).getRootModel();//
		{
			final LazyMvcI mvc = new LazyMcSupport(rootM, "activities") {

				@Override
				protected ModelI createModel(String name) {
					//
					return new ActivitiesModel(name);
				}

				@Override
				protected ControlI createControl(String name) {
					//
					return new ActivitiesControl(name);
				}
			};
			mvc.get();//
		}
		{
			final LazyMvcI mvc = new LazyMcSupport(rootM, "coper") {

				@Override
				protected ModelI createModel(String name) {
					//
					return new CooperModel(name);
				}

				@Override
				protected ControlI createControl(String name) {
					//
					return new CooperControl(name);
				}
			};
			mvc.get();
		}

	}

}
