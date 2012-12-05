/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class UserExpListView extends SimpleView implements ManagableI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp list

	protected ListI list;

	protected ManagedModelI managed;

	/**
	 * @param ctn
	 */
	public UserExpListView(String name, ContainerI ctn) {
		super(name, ctn);

		this.list = this.factory.create(ListI.class);
		this.list.setName("expListContainer");
		this.list.parent(this);

	}

	@Override
	public void doAttach() {
		super.doAttach();

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
		// TODO Auto-generated method stub
		super.processChildModelAdd(p, cm);
		if (cm instanceof UserExpModel) {
			processChildUserExpModelAdd((UserExpModel) cm);
		}
	}

	/**
	 * @param cm
	 */
	private void processChildUserExpModelAdd(UserExpModel cm) {
		String expId = cm.getExpId();
		UserExpView ue = new UserExpView(this.getContainer());
		ue.setName("userExpView-" + expId);
		ue.model(cm);

		ue.parent(this.list);

	}

	@Override
	public String getManager() {
		return BossModelI.M_LEFT;
	}

	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

}
