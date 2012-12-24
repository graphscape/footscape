/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.support.SimpleModel;

/**
 * @author wu
 * 
 */
public class ExpSearchView extends SimpleView implements ManagableI {

	protected StringEditorI statement;// keywords?

	protected ListI list;

	protected ButtonI nextPage;

	protected ButtonI previousPage;

	protected ManagedModelI managed;

	/**
	 * @param ele
	 * @param ctn
	 */
	public ExpSearchView(String name, ContainerI ctn) {

		super(name, ctn);

		this.statement = this.factory.create(StringEditorI.class,"search");
		this.statement.parent(this);

		this.list = this.factory.create(ListI.class);
		//
		this.list.setName("itemList");// for testing
		this.list.parent(this);
		//
		this.previousPage = this.factory.create(ButtonI.class,
				SimpleModel.valueOf("", "<<"));
		this.previousPage.parent(this);
		this.previousPage.addHandler(ClickEvent.TYPE,
				new EventHandlerI<ClickEvent>() {

					@Override
					public void handle(ClickEvent e) {
						ExpSearchView.this.onPreviousPage();
					}
				});

		this.nextPage = this.factory.create(ButtonI.class,
				SimpleModel.valueOf("", ">>"));
		this.nextPage.parent(this);
		this.nextPage.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpSearchView.this.onNextPage();
			}
		});

	}

	protected void onPreviousPage() {
		ExpSearchModelI m = this.getModel();
		m.previousPage();
	}

	protected void onNextPage() {
		ExpSearchModelI m = this.getModel();
		m.nextPage();
	}

	@Override
	public ExpSearchModelI getModel() {
		return this.model.cast();
	}

	@Override
	public void doAttach() {
		super.doAttach();

		this.statement.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				// TODO call action search action
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ManagableI
	 * #getManager()
	 */
	@Override
	public String getManager() {
		// TODO Auto-generated method stub
		return BossModelI.M_CENTER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ManagableI
	 * #
	 * setManaged(com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI
	 * .ManagedModelI)
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof ExpItemModel) {
			this.processChildExpItemModelAdd((ExpItemModel) cm);
		}
	}

	/**
	 * Oct 20, 2012
	 */
	private void processChildExpItemModelAdd(ExpItemModel cm) {
		String vname = viewName(cm);
		ExpItemView vi = new ExpItemView(vname, this.getContainer());
		vi.model(cm);
		vi.parent(this.list);// item view in list not this.

	}

	protected String viewName(ExpItemModel cm) {
		return "expItem-" + cm.getName();
	}

	protected ExpItemView getItemViewByModel(ExpItemModel cm, boolean force) {
		String vname = viewName(cm);
		return (ExpItemView) this.list.getChild(ModelI.class, vname, force);
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public void processChildModelRemove(ModelI parent, ModelI cm) {
		//
		super.processChildModelRemove(parent, cm);
		if (cm instanceof ExpItemModel) {
			this.processChildExpItemModelRemove((ExpItemModel) cm);
		}
	}

	/**
	 * Dec 1, 2012
	 */
	private void processChildExpItemModelRemove(ExpItemModel cm) {
		String vname = viewName(cm);
		ExpItemView ev = (ExpItemView) this.list.getChild(ViewI.class, vname,
				false);

		if (ev == null) {
			return;
		}
		ev.parent(null);

	}

}
