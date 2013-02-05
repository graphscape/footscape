/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 28, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class StandardView extends ViewSupport {

	protected ListI center;// TODO add PanelI

	protected ListI bottom;

	protected Map<String, ButtonI> actionMap = new HashMap<String, ButtonI>();

	/**
	 * @param ctn
	 */
	public StandardView(ContainerI ctn) {
		super(ctn, DOM.createDiv());
	}

	// TODO PanelI or CenterI
	protected ListI getOrBuildCenter() {
		if (this.center != null) {
			return this.center;
		}
		this.center = factory.create(ListI.class);

		this.child(this.center);// TODO

		return this.center;
	}

	protected ListI getOrBuildBottom() {
		if (this.bottom != null) {
			return this.bottom;
		}
		this.bottom = factory.create(ListI.class);

		this.child(this.bottom);// TODO

		return this.bottom;
	}

	public void addAction(final ActionModelI a) {

		ButtonI rt = this.actionMap.get(a.getName());
		if (rt != null) {
			return;// do nothing
		}

		// TODO model
		rt = factory.create(ButtonI.class);
		rt.setText(a.getName());// TODO title i18n
		rt.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				StandardView.this.onClick(a);
			}
		});

		// a.setWidget(rt);//

		this.actionMap.put(a.getName(), rt);//
		this.getOrBuildBottom().child(rt);//
	}

	protected void onClick(ActionModelI a) {
		// a.perform();
	}

	public void click(String action) {
		ButtonI bt = this.actionMap.get(action);
		bt.getElementWrapper().click();
	}
}
