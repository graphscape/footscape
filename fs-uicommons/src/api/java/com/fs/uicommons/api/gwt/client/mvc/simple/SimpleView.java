/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.error.ErrorInfosWidgetI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;

/**
 * @author wu display model as text
 */
public class SimpleView extends ViewSupport {

	private static final String ACTION_LIST = "actionList";

	private ListI actionList;// TODO ActionsELement

	protected Map<Path, ButtonI> actionMap;

	private ErrorInfosWidgetI errorInfoDisplay;

	protected Element header;

	protected Element body;

	protected Element footer;


	/**
	 * @param ctn
	 */
	public SimpleView(ContainerI ctn) {
		this(ctn, null);
	}

	public SimpleView(ContainerI ctn, String name) {
		this(ctn, name, DOM.createDiv());
	}

	public SimpleView(ContainerI c, String name, Element ele) {

		super(c, name, ele);
		
		this.actionMap = new HashMap<Path, ButtonI>();

		// BODY:
		this.header = DOM.createDiv();
		DOM.appendChild(this.element, header);
		this.header.addClassName("position-header");
		// BODY:
		this.body = DOM.createDiv();
		DOM.appendChild(this.element, body);
		this.body.addClassName("position-body");
		// BODY:
		this.footer = DOM.createDiv();
		DOM.appendChild(this.element, footer);
		this.footer.addClassName("position-footer");

		// TODO header
		this.errorInfoDisplay = this.factory.create(ErrorInfosWidgetI.class);
		this.errorInfoDisplay.parent(this);

		// footer:

		this.actionList = this.factory.create(ListI.class);
		this.actionList.setName(ACTION_LIST);//
		this.actionList.parent(this);

	}

	public void addAction(final Path aname) {
		this.addAction(aname, false);
	}

	public void addAction(final Path aname, boolean hide) {
		// listen to the button clicked event,which is button state is changed.
		ButtonI b = this.actionMap.get(aname);
		if (b != null) {
			throw new UiException("already exist action:" + name + " in view:" + this);
		}

		b = this.factory.create(ButtonI.class);// TODO,
		b.setText("%" + aname);

		b.parent(this.actionList);
		// click event is raised in button,not button's model
		b.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				SimpleView.this.onActionClick(aname);
			}
		});
		this.actionMap.put(aname, b);
		this.hideAction(aname, hide);

	}

	protected void hideAction(Path aname, boolean hide) {
		ButtonI b = this.actionMap.get(aname);
		if (b == null) {
			throw new UiException("no action:" + aname + " in view:" + this);
		}
		b.setVisible(!hide);
	}

	/**
	 * @param name
	 */
	protected void onActionClick(Path name) {
		ActionEvent ae = this.newActionEvent(name);
		this.beforeActionEvent(ae);
		ae.dispatch();

	}

	//
	protected ActionEvent newActionEvent(Path aname) {
		ActionEvent rt = new ActionEvent(this, (aname));

		return rt;
	}

	protected void beforeActionEvent(ActionEvent ae) {

	}

	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {
		Element parent = pe;
		if (cw instanceof ErrorInfosWidgetI) {
			parent = this.header;
		} else if (ACTION_LIST.equals(cw.getName()) && (cw instanceof ListI)) {
			// is
			// action
			// list
			// ,see
			// Constructor.
			parent = this.footer;
		} else {// all other add to body.
			parent = this.body;//
		}

		super.onAddChild(parent, cw);
	}

	protected List<Path> getActionList() {
		return new ArrayList<Path>(this.actionMap.keySet());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ViewI#clickAction(java.lang.String)
	 */
	@Override
	public void clickAction(final Path apath) {
		// click action is used for testing code,that should be the simulation
		// of human click.
		// for some test case,when child model is adding to the parent child,the
		// event will raise in a none-consistant situation.
		// We should consider to do deferred command for any event dispatching.
		DeferredCommand.add(new Command() {

			@Override
			public void execute() {
				SimpleView.this.doClickAction(apath);
			}
		});
	}

	private void doClickAction(Path ap) {
		ButtonI ab = this.actionMap.get(ap);
		if (ab == null) {
			throw new UiException("widget not found for action:" + ap + " in view:" + this);
		}
		ab.getElementWrapper().click();
	}

}
