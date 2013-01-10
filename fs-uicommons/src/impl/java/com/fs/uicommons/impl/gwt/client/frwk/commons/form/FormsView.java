/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 16, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.commons.form;

import java.util.List;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.simple.SyncValueDeliver;

/**
 * @author wu
 * 
 */
public class FormsView extends SimpleView {

	// private static final String PK_TAB_FORMMODEL = FormsView.class.getName()
	// + "_formModel";

	private TabberWI tabber;

	/**
	 * @param ctn
	 */
	public FormsView(ContainerI ctn) {
		this(null, ctn);
	}

	public FormsView(String name, ContainerI ctn) {
		super(name, ctn);
		this.tabber = this.factory.create(TabberWI.class);
		this.tabber.parent(this);//

	}

	@Override
	public WidgetI model(ModelI model) {
		super.model(model);

		return this;
	}

	private FormsModel concreteModel() {
		return (FormsModel) this.model;
	}

	@Override
	public void processChildModelAdd(final ModelI p, final ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof FormModel) {
			this.processChildFormModelAdd((FormModel) cm);
		}
	}

	/**
	 * @param cm
	 */
	protected void processChildFormModelAdd(final FormModel cm) {

		FormView fv = new FormView(this.getContainer());
		fv.setName(cm.getName());// for testing/debug
		fv.model(cm);
		TabWI tb = this.tabber.addTab(tabName(cm), fv);

		// tab select cause form model current change.
		SyncValueDeliver<Boolean, String> mvd = new SyncValueDeliver<Boolean, String>(
				tb.getModel(), TabWI.MK_SELECTED, this.concreteModel(),
				FormsModel.L_CURRENT_FORM)//
				.mapValue(Boolean.TRUE, cm.getName())// if tab selected,then the
														// current form selected
														// is the Name of the
														// cm.
				.reverseMapDefault(Boolean.FALSE);// if the name not match the
													// map reversely,set the tab
													// unselected.
													// the
													// value
													// is
		// false,@see:
		mvd.start();//
		// actions for this form

	}

	private String tabName(FormModel fm) {
		return "tab-" + fm.getName();
	}

	@Override
	protected void processModelValue(Location loc, ValueWrapper vw) {
		super.processModelValue(loc, vw);
		if (loc.equals(FormsModel.L_CURRENT_FORM)) {
			this.processModelValueCurrentForm(vw);
		}
	}

	/**
	 * Current form is changed.
	 * 
	 * @param e
	 */
	protected void processModelValueCurrentForm(ValueWrapper e) {
		FormModel fm = this.concreteModel().getCurrentForm();
		this.updateActionHidden(fm);// for current form,only should required
									// actions.
		// may have not yet add to tabber?

		TabWI tab = this.tabber.getTab(tabName(fm), false);
		if (tab == null) {// value set,but view have no chance to add the tab.
			return;// see the processChildFormModelAdd,there should process the
					// current form issue.
		}
		tab.select();//
	}

	protected void updateActionHidden(FormModel fm) {
		List<Path> actions = fm.getActionList();
		List<ActionModelI> amL = ControlUtil
				.getActionList(this.concreteModel());

		for (ActionModelI am : amL) {
			Path name = am.getActionPath();
			boolean show = actions.contains(name);
			am.setHidden(!show);// this value is listen by the super
								// class:SimpleView.
		}
		// change the actions display

	}

}
