/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityControlI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.impl.gwt.client.activity.ActivityControl;
import com.fs.uiclient.impl.gwt.client.activity.ActivityModel;
import com.fs.uiclient.impl.gwt.client.activity.ActivityView;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.LazyMvcSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class ActivitiesControl extends ControlSupport implements
		ActivitiesControlI {

	/**
	 * @param name
	 */
	public ActivitiesControl(String name) {
		super(name);
		this.addActionProcessor(ActivitiesModelI.A_REFRESH, new RefreshAP());
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		//
		super.processChildModelAdd(p, cm);

		if (cm instanceof ActivityModelI) {
			this.processChildActivityModelAdd((ActivityModelI) cm);
		}
	}

	/**
	 * Oct 22, 2012
	 */
	private void processChildActivityModelAdd(ActivityModelI cm) {
		// control it.
		ActivityControlI ac = new ActivityControl("activity");
		ac.model(cm);
		this.getManager().addControl(ac);//
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public void openActivity(final String actId) {
		// 
		LazyMvcI mvc = new LazyMvcSupport(this.model,"activity"){

			@Override
			protected ModelI createModel(String name) {
				// 
				return new ActivityModel(name, actId);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				// 
				return new ActivityView(name,c);
			}

			@Override
			protected ControlI createControl(String name) {
				// 
				return new ActivityControl(name);
			}
			
		};
		mvc.get();//
		
	}
}
