/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelValueHandler;

/**
 * @author wu
 * 
 */
public class ManagerView extends SimpleView {

	private TabberWI tabber;

	/**
	 * @param ctn
	 */
	public ManagerView(String name, ContainerI ctn) {
		super(name, ctn);
		this.tabber = this.factory.create(TabberWI.class);//
		this.tabber.parent(this);
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
		String tname = cm.getName();//
		final TabWI sitem = this.tabber.addTab(tname, p);

		// model is already the child of panelModel.
		WidgetI w = cm.getManagedWidget();
		w.parent(p);

		cm.addValueHandler(ManagedModelI.L_SELECTED,
				new ModelValueHandler<Boolean>() {

					@Override
					public void handleValue(Boolean value) {
						if(value){
							sitem.select();
						}
					}
				});
		if(cm.isSelect()){
			sitem.select();// sync the current value.
			
		}
	}
}
