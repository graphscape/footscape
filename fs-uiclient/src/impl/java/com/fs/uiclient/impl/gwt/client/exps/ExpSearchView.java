/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ExpSearchView extends ViewSupport implements ExpSearchViewI {

	protected StringEditorI statement;// keywords?

	protected ListI list;

	protected ListI header;

	protected ButtonI search;

	protected ButtonI more;

	// protected ButtonI previousPage;

	protected ViewReferenceI managed;

	protected ExpSearchModelI model;

	protected LabelI myexp;// TODO view

	/**
	 * @param ele
	 * @param ctn
	 */
	public ExpSearchView(ContainerI ctn) {

		super(ctn, "exps", DOM.createDiv());

		this.model = new ExpSearchModel();

		MapProperties pts = new MapProperties();
		pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.FALSE);
		this.header = this.factory.create(ListI.class, this.getChildName("header"), pts);//
		this.header.parent(this);

		this.statement = this.factory.create(StringEditorI.class);
		this.statement.parent(this.header);
		this.statement.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent t) {
				ExpSearchView.this.onPhraseChange(t);
			}
		});
		// search button
		this.search = this.factory.create(ButtonI.class);
		this.search.setText(true, "search");
		this.search.parent(this.header);
		this.search.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpSearchView.this.onSearch();
			}
		});
		//
		this.myexp = this.factory.create(LabelI.class);
		this.myexp.parent(this.header);

		this.list = this.factory.create(ListI.class);
		//
		this.list.setName("itemList");// for testing
		this.list.parent(this);

		this.more = this.factory.create(ButtonI.class);
		this.more.setText(true, "more");
		this.more.getElement().addClassName("more");

		this.more.parent(this);
		this.more.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpSearchView.this.onMore();
			}
		});

	}

	protected void onSearch() {
		this.reset();//
		this.dispatchActionEvent(Actions.A_EXPS_SEARCH);
	}

	/**
	 * @param t
	 */
	protected void onPhraseChange(ChangeEvent t) {
		String phrase = this.statement.getData();
		this.model.setPhrase(phrase);
	}

	protected void onMore() {
		ActionEvent rt = new ActionEvent(this, Actions.A_EXPS_SEARCH);
		rt.setPayload("isMore", Boolean.TRUE);
		rt.dispatch();
	}

	@Override
	public void addExpItem(ExpItemModel cm) {
		ExpItemModel old = this.model.addExpItem(cm);
		if (old != null) {
			return;// ignore?
		}

		String vname = viewName(cm);
		ExpItemView vi = new ExpItemView(vname, this.getContainer(), cm);
		vi.parent(this.list);// item view in msglist not this.

	}

	protected String viewName(ExpItemModel cm) {
		return "expItem-" + cm.getExpId();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#reset()
	 */
	@Override
	public void reset() {
		this.model.reset();
		this.list.clean(ExpItemView.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#getExpId(boolean)
	 */
	@Override
	public UserExpModel getExpId(boolean b) {
		// TODO Auto-generated method stub
		return this.model.getExpId(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#setExpId(java.lang
	 * .String)
	 */
	@Override
	public void setExpId(UserExpModel expId) {
		this.model.setExpId(expId);
		String text = null;
		if (expId != null) {
			text = expId.getBody();
		}

		this.myexp.setText(text);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#getPhrase(boolean)
	 */
	@Override
	public String getPhrase(boolean force) {
		return this.model.getPhrase(force);
	}

	/*
	 *Apr 3, 2013
	 */
	@Override
	public int getSize() {
		// 
		return this.model.getExpItemList().size();
	}

}
