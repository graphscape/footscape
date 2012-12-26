/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.HeaderNames;
import com.fs.uiclient.impl.gwt.client.achat.AChatControlImpl;
import com.fs.uiclient.impl.gwt.client.activities.ActivitiesControl;
import com.fs.uiclient.impl.gwt.client.activities.ActivitiesModel;
import com.fs.uiclient.impl.gwt.client.cooper.CooperControl;
import com.fs.uiclient.impl.gwt.client.cooper.CooperModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditControl;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchControl;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchModel;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchView;
import com.fs.uiclient.impl.gwt.client.profile.ProfileControl;
import com.fs.uiclient.impl.gwt.client.profile.ProfileModel;
import com.fs.uiclient.impl.gwt.client.profile.ProfileView;
import com.fs.uiclient.impl.gwt.client.signup.SignupControl;
import com.fs.uiclient.impl.gwt.client.signup.SignupModel;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uiclient.impl.gwt.client.tasks.TaskManager;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListControl;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListModel;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.gwt.client.usshot.UserSnapshotControlImpl;
import com.fs.uiclient.impl.gwt.client.usshot.UserSnapshotModelImpl;
import com.fs.uicommons.api.gwt.client.frwk.support.LazyMvcHeaderItemHandler;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.LazyMcSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.LazyMvcSupport;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class MainControl extends ControlSupport implements MainControlI {

	/**
	 * @param name
	 */
	public MainControl(String name) {
		super(name);
	}

	@Override
	protected void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();
		this.activeLazyMvcs();//
		this.activeAuthProcessors();
		this.activeHeaderItems();//

		this.activeTasks();

	}

	protected void activeAuthProcessors() {// auth processor

		ModelI rootM = this.getClient(true).getRootModel();//
		SessionModelI sm = rootM.getChild(SessionModelI.class, true);//
		// TODO server side provide the snapshot replacement,push to client
		// command/data from server side.
		sm.addAuthedProcessor(this.getLazy(MainControlI.LZ_USERSNAPSHOT, true));
		// after auth,active activities control.
		sm.addAuthedProcessor(this.getLazy(MainControlI.LZ_ACTIVITIES, true));
		// active search
		sm.addAuthedProcessor(this.getLazy(MainControlI.LZ_EXP_SEARCH, true));
		// active user exp list
		sm.addAuthedProcessor(this.getLazy(MainControlI.LZ_UE_LIST, true));
		// active cooper control.
		sm.addAuthedProcessor(this.getLazy(MainControlI.LZ_COOPER, true));

		sm.addAuthedProcessor(this.getLazy(MainControlI.LZ_ACHAT, true));

	}

	protected void activeHeaderItems() {
		this.addHeaderForLazy(MainControlI.LZ_SIGNUP, HeaderNames.H1_USER);
		this.addHeaderForLazy(MainControlI.LZ_PROFILE, HeaderNames.H1_USER);
	}

	protected void addHeaderForLazy(String lazyName) {
		this.addHeaderForLazy(lazyName);
	}

	protected void addHeaderForLazy(String lazyName, String headerItem) {
		LazyI mvc = this.getLazy(lazyName, true);
		new LazyMvcHeaderItemHandler((LazyMvcI) mvc, headerItem)
				.start(this.model);

	}

	protected void addLazyMvc(String name, LazyMvcI lz) {
		this.addLazy(name, lz);
	}

	protected void activeLazyMvcs() {
		ModelI rootM = this.getClient(true).getRootModel();//
		this.addLazyMvc(MainControlI.LZ_USERSNAPSHOT, new LazyMcSupport(
				this.model, "usshot") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new UserSnapshotModelImpl(name);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new UserSnapshotControlImpl(name);
			}
		});

		this.addLazyMvc(MainControlI.LZ_ACTIVITIES, new LazyMcSupport(
				this.model, "activities") {

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
		});
		this.addLazyMvc(MainControlI.LZ_SIGNUP, new LazyMvcSupport(this.model,
				"signup") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new SignupModel(name);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new SignupView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new SignupControl(name);
			}

		});
		//
		this.addLazyMvc(MainControlI.LZ_PROFILE, new LazyMvcSupport(this.model,
				"profile") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new ProfileModel(name);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new ProfileView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new ProfileControl(name);
			}

		});

		//
		this.addLazyMvc(MainControlI.LZ_EXP_SEARCH, new LazyMvcSupport(
				this.model, "exps") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new ExpSearchModel(name);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new ExpSearchView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new ExpSearchControl(name);
			}

		});

		//
		this.addLazy(MainControlI.LZ_UE_LIST, new LazyMvcSupport(this.model,
				"uelist") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new UserExpListModel(name);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new UserExpListView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new UserExpListControl(name);
			}

		});
		//
		this.addLazyMvc(MainControlI.LZ_EXP_EDIT, new LazyMvcSupport(
				this.model, "expe") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new ExpEditModel(name);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new ExpEditView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new ExpEditControl(name);
			}

		});

		this.addLazyMvc(MainControlI.LZ_COOPER, new LazyMcSupport(rootM,
				"cooper") {

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
		});

		this.addLazyMvc(MainControlI.LZ_ACHAT, new LazyMcSupport(rootM,
				"chatrooms") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new AChatModel(name);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new AChatControlImpl(name);
			}
		});

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

}
