/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.SimpleModel;

/**
 * @author wu
 * 
 */
public class ExpSearchView extends SimpleView implements ViewReferenceI.AwareI {

	protected StringEditorI statement;// keywords?

	protected ListI list;

	protected ButtonI nextPage;

	protected ButtonI previousPage;

	protected ViewReferenceI managed;

	/**
	 * @param ele
	 * @param ctn
	 */
	public ExpSearchView(ContainerI ctn, ExpSearchModelI m) {

		super(Actions.A_EXPS.getName(), ctn, m);

		this.statement = this.factory.create(StringEditorI.class, "search");
		this.statement.parent(this);
		this.statement.getModel().addValueHandler(ModelI.L_DEFAULT, new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent t) {
				String text = (String) t.getValue();
				ExpSearchView.this.onPhraseChange(text);
			}
		});

		this.list = this.factory.create(ListI.class);
		//
		this.list.setName("itemList");// for testing
		this.list.parent(this);
		//
		this.previousPage = this.factory.create(ButtonI.class, SimpleModel.valueOf("", "<<"));
		this.previousPage.parent(this);
		this.previousPage.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpSearchView.this.onPreviousPage();
			}
		});

		this.nextPage = this.factory.create(ButtonI.class, SimpleModel.valueOf("", ">>"));
		this.nextPage.parent(this);
		this.nextPage.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpSearchView.this.onNextPage();
			}
		});

	}

	protected void onPhraseChange(String text) {
		this.getModel().setPhrase(text);
	}

	protected void onPreviousPage() {
		ExpSearchModelI m = this.getModel();
		m.previousPage();
	}

	protected void onNextPage() {
		ExpSearchModelI m = this.getModel();
		m.nextPage();
	}

	public ExpSearchModelI getModel() {
		return this.findModel(ExpSearchModelI.class, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.
	 * ViewReferenceI.AwareI #
	 * setManaged(com.fs.uicommons.api.gwt.client.frwk.commons
	 * .manage.ManagerModelI .ViewReferenceI)
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

	/**
	 * Oct 20, 2012
	 */
	public void addExpItem(ExpItemModel cm) {
		String vname = viewName(cm);
		ExpItemView vi = new ExpItemView(vname, this.getContainer(), cm);
		vi.parent(this.list);// item view in list not this.

	}

	protected String viewName(ExpItemModel cm) {
		return "expItem-" + cm.getName();
	}

	protected ExpItemView getItemViewByModel(ExpItemModel cm, boolean force) {
		String vname = viewName(cm);
		return (ExpItemView) this.list.getChild(ModelI.class, vname, force);
	}

	/**
	 * Dec 1, 2012
	 */
	public void removeExpItem(ExpItemModel cm) {
		String vname = viewName(cm);
		ExpItemView ev = (ExpItemView) this.list.getChild(ViewI.class, vname, false);

		if (ev == null) {
			return;
		}
		ev.parent(null);

	}

}
