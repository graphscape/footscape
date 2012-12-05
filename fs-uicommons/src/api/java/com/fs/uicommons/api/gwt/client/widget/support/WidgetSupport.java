/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.support;

import com.fs.uicommons.api.gwt.client.AdjusterI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.model.ModelChildProcessorI;
import com.fs.uicore.api.gwt.client.support.WidgetBase;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class WidgetSupport extends WidgetBase implements ModelChildProcessorI {

	protected AdjustersHelper adjustersHelper;

	public WidgetSupport(Element ele) {
		this(null, ele);
	}

	public WidgetSupport(String name, Element ele) {
		super(name, ele);

	}

	@Override
	public UiObjectI parent(UiObjectI parent) {
		WidgetI old = (WidgetI) this.parent;

		if (parent != null && !(parent instanceof WidgetI)) {
			throw new UiException("cannot set a none widget parent");
		}
		super.parent(parent);

		this.onSetParent(old, (WidgetI) parent);

		return this;
	}

	protected void onSetParent(WidgetI old, WidgetI newP) {

		if (old != null && newP != null) {
			throw new UiException(
					"changing parent not suported, current parent:" + old
							+ ",new parent:" + newP + " for widget:" + this
							+ "");
		}

		if (old != null && old.isAttached()) {
			this.detach();
		}

		if (newP != null && newP.isAttached()) {
			this.attach();
		}

	}

	@Override
	public void doAttach() {
		super.doAttach();

		if (this.model == null) {
			throw new UiException("bug,model is null for widget:" + this);
		}
		ModelChildProcessorI.Helper.onAttach(model, this);
	}

	@Override
	public void processChildModelAdd(final ModelI parent, final ModelI cm) {

	}

	@Override
	public void processChildModelRemove(final ModelI parent, final ModelI cm) {

	}

	@Override
	public boolean isAttached() {
		return attached;
	}

	@Override
	public void setVisible(boolean v) {
		super.setVisible(v);
		this.model.setValue(IS_VISIBLE, v);
	}


	//@Override
	public AdjusterI addAdjuster(String name, HandlerI<ClickEvent> eh) {
		AdjusterI rt = this.addAdjuster(name);
		rt.addClickHandler(eh);
		return rt;
	}

	//@Override
	public AdjusterI addAdjuster(String name) {
		if (this.adjustersHelper == null) {
			this.adjustersHelper = new AdjustersHelper();//
			this.adjustersHelper.parent(this);
		}

		AdjusterI rt = this.adjustersHelper.addAdjuster(name);

		return rt;
	}

	@Override
	public void addChild(UiObjectI c) {
		super.addChild(c);
		if (c instanceof AdjusterI) {// add to the element
			// this.processAddAdjuster((AdjusterI)c);
		}
	}

}
