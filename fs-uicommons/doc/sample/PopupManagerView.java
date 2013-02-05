/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.manage.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelValueHandler;

/**
 * @author wu
 * 
 */
public class PopupManagerView extends SimpleView {

	/**
	 * @param ctn
	 */
	public PopupManagerView(ContainerI c, String name) {
		super(c,name);
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
		if (cm instanceof ViewReferenceI) {
			this.processChildManagedModelAdd((ViewReferenceI) cm);
		}
	}

	private void processChildManagedModelAdd(final ViewReferenceI cm) {

		final PanelWI p = this.factory.create(PanelWI.class);
		p.parent(this);//NOTE attach 
		// model is already the child of panelModel.
		WidgetI w = cm.getManagedWidget();
		w.parent(p);

		final PopupEO pe = new PopupEO(p.getElement());
		pe.parent(this);//
		cm.addValueHandler(ViewReferenceI.L_SELECTED,
				new ModelValueHandler<Boolean>() {

					@Override
					public void handleValue(Boolean value) {

						PopupManagerView.this.select(pe, cm, value);

					}
				});

	}

	/**
	 * Nov 24, 2012
	 */
	protected void select(PopupEO pp, ViewReferenceI cm, Boolean value) {
		pp.setVisible(value);//
	}

}
