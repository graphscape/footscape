/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class AnchorWImpl extends WidgetSupport implements AnchorWI {

	public AnchorWImpl(String name) {
		super(name, (Element) Document.get().createAnchorElement().cast());
		this.addGwtHandler(
				com.google.gwt.event.dom.client.ClickEvent.getType(),
				new ClickHandler() {

					@Override
					public void onClick(
							com.google.gwt.event.dom.client.ClickEvent event) {
						AnchorWImpl.this.onGwtClick(event);
					}//
				});
	}

	@Override
	protected void processModelDefaultValue(ValueWrapper vw) {

		AnchorElement ae = this.getElement().cast();
		String sd = (String) vw.getValue();
		ae.setInnerText(sd);//
		ae.setTitle(sd);// TODO replace this

	}

	public void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		new ClickEvent(this).dispatch();
	}

	/* */
	@Override
	public void click() {
		this.assertAttached();
		this.getElementWrapper().click();
	}

}
