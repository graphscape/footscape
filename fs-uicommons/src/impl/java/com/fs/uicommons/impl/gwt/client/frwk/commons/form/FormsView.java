/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 16, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.commons.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.UiCommonsConstants;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
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

	private static final String FM_DEFAULT = "default";

	private TabberWI tabber;

	private FormsModel formsModel;

	protected Map<String, FormViewI> formViewMap;

	/**
	 * @param ctn
	 */
	public FormsView(ContainerI ctn) {
		this(ctn, null);
	}

	public FormsView(ContainerI ctn, String name) {
		super(ctn, name);
		this.formViewMap = new HashMap<String, FormViewI>();
		this.formsModel = new FormsModel(null);
		this.tabber = this.factory.create(TabberWI.class);
		this.tabber.parent(this);//
		this.addForm(FM_DEFAULT);

	}

	@Override
	public FormViewI addForm(final String fname) {

		FormView fv = new FormView(this.getContainer(), fname);

		TabWI tb = this.tabber.addTab(tabPath(fname), fv, false);
		tb.addSelectEventHandler(new EventHandlerI<SelectEvent>() {

			@Override
			public void handle(SelectEvent t) {
				if (!t.isSelected()) {
					return;
				}
				FormsView.this.onSelectedFromTab(fname);
			}
		});
		this.formViewMap.put(fname, fv);
		// first?
		if (tb.isSelected()) {
			this.setCurrentForm(fname);
		}
		return fv;

	}

	public FormViewI getDefaultFormView() {
		return this.getFormView(FormsView.FM_DEFAULT);
	}

	public FormViewI getFormView(String fname) {
		return this.formViewMap.get(fname);
	}

	protected List<Path> getActionList(String fname) {

		return this.getFormView(fname).getActionList();
	}

	/**
	 * @param cm
	 */
	protected void onSelectedFromTab(String fname) {
		this.setCurrentForm(fname);
	}

	@Override
	protected void beforeActionEvent(ActionEvent ae) {

		ae.property(UiCommonsConstants.AK_FORMS_VIEW, this);
	}

	private Path tabPath(String fname) {
		return Path.valueOf("tab-" + fname);
	}

	/**
	 * Current form is changed.
	 * 
	 * @param e
	 */
	public void setCurrentForm(String fname) {

		this.updateActionHidden(fname);// for current form,only should required
										// actions.
		// may have not yet add to tabber?

		TabWI tab = this.tabber.getTab(tabPath(fname), false);
		if (tab == null) {// value set,but view have no chance to add the tab.
			return;// see the processChildFormModelAdd,there should process the
					// current form issue.
		}

		if (!tab.isSelected()) {
			tab.select();//
		}
	}

	protected void updateActionHidden(String fname) {

		List<Path> actions = this.getActionList();

		List<Path> actions2 = this.getActionList(fname);

		for (Path am : actions) {
			boolean show = actions2.contains(am);
			this.hideAction(am, !show);
		}
		// change the actions display

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI#getForm(java.
	 * lang.String)
	 */
	@Override
	public FormViewI getForm(String name) {
		// TODO Auto-generated method stub
		return this.formViewMap.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI#getDefaultForm()
	 */
	@Override
	public FormViewI getDefaultForm() {
		return this.getForm(FM_DEFAULT);

	}

}
