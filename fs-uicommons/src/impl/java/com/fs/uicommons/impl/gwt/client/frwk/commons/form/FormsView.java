/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 16, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.commons.form;

import java.util.List;

import com.fs.uicommons.api.gwt.client.Constants;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI;
import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.widget.event.SelectEvent;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class FormsView extends SimpleView implements FormsViewI {

	// private static final String PK_TAB_FORMMODEL = FormsView.class.getName()
	// + "_formModel";

	private TabberWI tabber;

	/**
	 * @param ctn
	 */
	public FormsView(ContainerI ctn, FormsModel fm) {
		this(null, ctn, fm);
	}

	public FormsView(String name, ContainerI ctn, FormsModel fm) {
		this(null, name, ctn, fm);
	}

	public FormsView(Path agp, String name, ContainerI ctn, FormsModel fm) {
		super(agp, name, ctn, fm);
		this.tabber = this.factory.create(TabberWI.class);
		this.tabber.parent(this);//
		for (FormModel f : fm.getChildList(FormModel.class)) {
			this.addForm(f);
		}

	}

	public FormsModel concreteModel() {
		return (FormsModel) this.model;
	}

	@Override
	public void addForm(final FormModel cm) {
		String fname = cm.getName();
		FormView fv = new FormView(fname, this.getContainer(), cm);

		TabWI tb = this.tabber.addTab(tabName(cm), fv, false);
		tb.addSelectEventHandler(new EventHandlerI<SelectEvent>() {

			@Override
			public void handle(SelectEvent t) {
				if (!t.isSelected()) {
					return;
				}
				FormsView.this.onSelectedFromTab(cm);
			}
		});
		// first?
		if (tb.isSelected()) {
			this.setCurrentForm(cm);
		}

	}

	/**
	 * @param cm
	 */
	protected void onSelectedFromTab(FormModel cm) {
		this.setCurrentForm(cm);
	}

	@Override
	protected void beforeActionEvent(ActionEvent ae) {

		ae.property(Constants.AK_FORMS_MODEL, this.concreteModel());
	}

	private String tabName(FormModel fm) {
		return "tab-" + fm.getName();
	}

	public FormModel getCurrentForm() {
		return this.concreteModel().getCurrentForm();
	}

	/**
	 * Current form is changed.
	 * 
	 * @param e
	 */
	public void setCurrentForm(FormModel fm) {
		this.updateActionHidden(fm);// for current form,only should required
									// actions.
		// may have not yet add to tabber?

		TabWI tab = this.tabber.getTab(tabName(fm), false);
		if (tab == null) {// value set,but view have no chance to add the tab.
			return;// see the processChildFormModelAdd,there should process the
					// current form issue.
		}

		if (!tab.isSelected()) {
			tab.select();//
		}
	}

	protected void updateActionHidden(FormModel fm) {
		List<Path> actions = fm.getActionList();
		List<ActionModelI> amL = ControlUtil.getActionList(this.concreteModel());

		for (ActionModelI am : amL) {
			Path name = am.getActionPath();
			boolean show = actions.contains(name);
			am.setHidden(!show);// this value is listen by the super
								// class:SimpleView.
		}
		// change the actions display

	}

}
