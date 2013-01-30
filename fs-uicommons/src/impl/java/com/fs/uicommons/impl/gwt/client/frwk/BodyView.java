/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.event.BodyItemCreatedEvent;
import com.fs.uicommons.api.gwt.client.event.BodyItemSelectEvent;
import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public class BodyView extends LightWeightView {

	private TabberWI tabber;// TODO replace by a stack and a menu view.

	/**
	 * @param ctn
	 */
	public BodyView(String name, ContainerI ctn) {
		super(name, ctn);
		this.tabber = this.factory.create(TabberWI.class);//
		this.tabber.parent(this);
	}

	@Override
	public void doModel(ModelI model) {
		super.doModel(model);
		BodyModelI bm = (BodyModelI) model;
		bm.addHandler(BodyItemCreatedEvent.TYPE, new EventHandlerI<BodyItemCreatedEvent>() {

			@Override
			public void handle(BodyItemCreatedEvent t) {
				BodyView.this.onBodyItemCreatedEvent(t);
			}
		});

		bm.addHandler(BodyItemSelectEvent.TYPE, new EventHandlerI<BodyItemSelectEvent>() {

			@Override
			public void handle(BodyItemSelectEvent t) {
				BodyView.this.onBodyViewSelect(t);
			}
		});
	}

	@Override
	public BodyModelI getModel() {
		return (BodyModelI) this.model;
	}

	/**
	 * @param t
	 */
	protected void onBodyItemCreatedEvent(BodyItemCreatedEvent t) {
		final PanelWI p = this.factory.create(PanelWI.class);
		Path path = t.getPath();
		String tname = p.toString();
		final TabWI sitem = this.tabber.addTab(tname, p);

		// model is already the child of panelModel.
		ViewReferenceI vr = this.getModel().getManaged(path);
		WidgetI w = vr.getManagedWidget();
		w.parent(p);

	}

	/**
	 * @param t
	 */
	protected void onBodyViewSelect(BodyItemSelectEvent t) {
		if (!t.isSelected()) {
			return;
		}
		final PanelWI p = this.factory.create(PanelWI.class);
		Path path = t.getPath();
		String tname = p.toString();
		final TabWI sitem = this.tabber.getTab(tname, true);
		sitem.select();
	}

}
