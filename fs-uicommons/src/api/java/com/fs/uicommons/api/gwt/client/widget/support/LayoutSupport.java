/**
 * Jul 15, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.support;

import java.util.List;

import com.fs.uicore.api.gwt.client.CompositeI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.Element;

/**
 * @author wu TODO rename to LayoutWidgetSupport
 */
public class LayoutSupport extends WidgetSupport implements CompositeI {
	public LayoutSupport(ContainerI c, Element ele) {
		this(c, null, ele);
	}

	public LayoutSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);
	}

	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		this.onAddChild(this.element, ceo);
	}

	@Deprecated
	// use processAddChildElementObject
	protected void onAddChild(Element pe, ElementObjectI ceo) {
		pe.appendChild(ceo.getElement());//
	}

	/* */
	@Override
	public void removeChild(UiObjectI c) {
		super.removeChild(c);
		WidgetI cw = (WidgetI) c;
		this.onRemoveChild(this.element, cw);
	}

	protected void onRemoveChild(Element ele, WidgetI cw) {
		cw.getElement().removeFromParent();// TODO
	}

	/* */
	@Override
	public CompositeI child(WidgetI w) {
		w.parent(this);
		return this;
	}

	@Override
	public void attach() {
		super.attach();
		for (WidgetI w : this.getChildWidgetList()) {
			w.attach();
		}
	}

	@Override
	public void detach() {
		for (WidgetI w : this.getChildWidgetList()) {
			w.detach();
		}
		super.detach();
	}

	@Override
	public List<WidgetI> getChildWidgetList() {
		return this.getChildList(WidgetI.class);
	}

}
