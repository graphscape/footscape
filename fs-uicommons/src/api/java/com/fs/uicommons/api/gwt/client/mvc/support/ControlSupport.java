/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.CreaterI;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkViewI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wuzhen
 * 
 */
public class ControlSupport extends AbstractControl {

	public ControlSupport(ContainerI c, String name) {
		super(c, name);
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public void processChildModelRemove(ModelI parent, ModelI child) {
		//

	}

	public <T extends ModelI> T getOrCreateModel(ModelI parent, Class<T> cls, CreaterI<T> crt) {
		T rt = parent.getChild(cls, false);
		if (rt != null) {
			return rt;
		}
		rt = crt.create(this.container);
		rt.parent(parent);
		return rt;
	}

	public RootI getRootView() {
		return this.getClient(true).getRoot();
	}

	public ModelI getRootModel() {
		return this.getClient(true).getRootModel();
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getEndpoint().sendMessage(req);//
	}

	protected FrwkViewI getFrwkView() {
		return this.getRootView().getChild(FrwkViewI.class, true);
	}

	protected BodyViewI getBodyView() {
		return this.getFrwkView().getBodyView();
	}
}
