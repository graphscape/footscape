/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelValueHandler;

/**
 * @author wu
 * 
 */
public class ManagerView extends SimpleView {

	private StackWI stack;

	/**
	 * @param ctn
	 */
	public ManagerView(String name, ContainerI ctn) {
		super(name, ctn);
		this.stack = this.factory.create(StackWI.class);//
		this.stack.parent(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport#processChildModelAdd
	 * (com.fs.uicore.api.gwt.client.ModelI)
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof ManagedModelI) {
			this.processChildManagedModelAdd((ManagedModelI) cm);
		}
	}

	private void processChildManagedModelAdd(ManagedModelI cm) {

		final PanelWI p = this.factory.create(PanelWI.class);
		final StackWI.ItemModel sitem = this.stack.insert(p, false);

		// model is already the child of panelModel.
		WidgetI w = cm.getManagedWidget();
		w.parent(p);

		cm.addValueHandler(ManagedModelI.L_SELECTED,
				new ModelValueHandler<Boolean>() {

					@Override
					public void handleValue(Boolean value) {

						sitem.select(value);

					}
				});
		sitem.select(cm.isSelect());// sync the current value.
	}
}
