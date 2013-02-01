/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.simple;

import java.util.List;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.error.ErrorInfosWidgetI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.simple.SimpleValueDeliver;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;

/**
 * @author wu display model as text
 */
public class SimpleView extends ViewSupport {

	private ListI actionList;// TODO ActionsELement
	private ErrorInfosWidgetI errorInfoDisplay;
	protected Element header;
	protected Element body;
	protected Element footer;

	private static final String ACTION_LIST = "actionList";

	/**
	 * @param ctn
	 */
	public SimpleView(ContainerI ctn) {
		this(null, ctn);
	}

	public SimpleView(String name, ContainerI ctn, ModelI md) {
		this(name, DOM.createDiv(), ctn, md);
	}

	public SimpleView(String name, ContainerI ctn) {
		this(name, DOM.createDiv(), ctn, new SimpleModel("unkown"));
	}

	public SimpleView(String name, Element ele, ContainerI ctn, ModelI md) {
		super(name, ele, ctn, md);

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

	@Override
	protected void processChildActionModelAdd(final ActionModelI cm) {
		super.processChildActionModelAdd(cm);
		// listen to the button clicked event,which is button state is changed.

		ModelI bm = this.addModel("button-" + cm.getName());// TODO button's
															// model is added
															// into the view's
															// model?
		ButtonI b = this.factory.create(ButtonI.class, bm);// TODO,
		b.getModel().setDefaultValue("%" + cm.getName());

		b.parent(this.actionList);
		// click event is raised in button,not button's model
		b.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				SimpleView.this.onActionClick(cm.getName());
			}
		});
		// deliver the action's hidden to the button's visible.
		// see widget base class,there will set the class name of the element.
		// see the css file also.
		SimpleValueDeliver<Boolean, Boolean> svd = new SimpleValueDeliver<Boolean, Boolean>(cm,
				ActionModelI.L_HIDDEN, bm, WidgetI.IS_VISIBLE);
		svd.mapValue(Boolean.TRUE, Boolean.FALSE).mapDefault(Boolean.TRUE).start();
		// widget for render the model

	}

	/**
	 * @param name
	 */
	protected void onActionClick(String name) {
		ActionEvent ae = this.newActionEvent(name);
		this.beforeActionEvent(ae);
		ae.dispatch();

	}

	//
	protected ActionEvent newActionEvent(String aname) {
		Path ap = Actions.ACTION.getSubPath(this.name).getSubPath(aname);
		ActionEvent rt = new ActionEvent(this, ap);
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
		List<ButtonI> btl = this.actionList.getChildList(ButtonI.class);
		String a = ap.getName();//
		ButtonI ab = null;
		for (ButtonI bt : btl) {
			if (bt.getModel().getName().equals("button-" + a)) {
				ab = bt;
			}
		}
		if (ab == null) {
			throw new UiException("widget not found for action:" + ap + " in view:" + this);
		}
		ab.getElementWrapper().click();
	}

	/*
	 * Nov 13, 2012
	 */
	@Override
	protected void handleActionProcessing(ActionModelI am) {
		super.handleActionProcessing(am);
		this.errorInfoDisplay.clear();
		// TODO disable input?
	}

	/*
	 * Nov 13, 2012
	 */
	@Override
	protected void handleActionProcessed(ActionModelI am) {
		super.handleActionProcessed(am);
		// display errors

		this.errorInfoDisplay.addErrorInfos(am.getErrorInfos());

	}

}
