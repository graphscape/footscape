/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.impl.gwt.client.activity.ActivityControl;
import com.fs.uiclient.impl.gwt.client.activity.ActivityModel;
import com.fs.uiclient.impl.gwt.client.activity.ActivityView;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.LazyMvcSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class ActivitiesControl extends ControlSupport implements ActivitiesControlI {

	/**
	 * @param name
	 */
	public ActivitiesControl(String name) {
		super(name);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		//
		super.processChildModelAdd(p, cm);

	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public void openActivity(final String actId) {
		//

		String lname = "activity-" + actId;
		Mvc mvc = this.getLazyObject(lname, false);
		if (mvc != null) {
			//TODO focus
			return;
		}

		LazyMvcI lmvc = new LazyMvcSupport(this.model, lname) {

			@Override
			protected ModelI createModel(String name) {
				//
				return new ActivityModel(name, actId);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new ActivityView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new ActivityControl(name);
			}

		};
		this.addLazy(lname, lmvc);
		lmvc.get();//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI#refresh(
	 * java.lang.String)
	 */
	@Override
	public void refresh(String actId) {
		this.triggerAction(Actions.A_ACTS_ACTIVITIES);
	}
}
