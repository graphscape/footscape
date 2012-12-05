/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.header;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.bar.BarWidgetI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class HeaderView extends SimpleView implements ManagableI {

	protected ManagedModelI managed;

	private BarWidgetI itemList;

	/**
	 * @param ctn
	 */
	public HeaderView(String name, ContainerI ctn) {
		super(name, ctn);
		this.itemList = this.factory.create(BarWidgetI.class);
		this.itemList.parent(this);//
	}

	public void doAttach() {
		super.doAttach();
	}

	@Override
	public void processChildModelAdd(ModelI p, final ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof ItemModel) {
			this.processChildAdd((ItemModel) cm);//
		}

	}

	protected void processChildAdd(final ItemModel iw) {

		// TODO remove addItem,use parrent and model to pass paramter.
		this.itemList.addItem(iw.getPosition(),
				new ItemView(this.getContainer()).model(iw));

	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public String getManager() {
		//
		return BossModelI.M_TOP;
	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

}
