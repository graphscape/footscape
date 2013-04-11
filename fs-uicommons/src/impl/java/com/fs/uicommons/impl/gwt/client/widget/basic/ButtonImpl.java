/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.state.State;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ButtonImpl extends WidgetSupport implements ButtonI {

	private State state;

	private boolean disable;

	public ButtonImpl(ContainerI c, String name) {
		super(c, name, (Element) Document.get().createPushButtonElement().cast());
		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				ButtonImpl.this.onGwtClick(event);
			}//
		});
	}

	@Override
	public void setText(boolean toloc, String txt) {
		String title = null;
		String txt2 = txt;
		if (toloc) {
			String tkey = Path.valueOf("tip").concat(Path.valueOf(txt)).toString();
			txt2 = this.localized(txt);//			
			title = this.localized(tkey);
		}
		Element ele = this.getElement();

		ele.setInnerText(txt2);//
		if(title!=null){
			ele.setTitle(title);// TODO replace this			
		}

	}

	public void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		if (this.disable) {
			return;
		}
		this.switchState();
		new ClickEvent(this).dispatch();
	}

	public void switchState() {
		State s = this.getState();

		if (ButtonI.UP.equals(s)) {
			this.setState(ButtonI.DOWN);
		} else {
			this.setState(ButtonI.UP);
		}
	}

	public void setState(State s) {
		this.state = s;
	}

	@Override
	public State getState() {

		return this.state;

	}

	/*
	 * Mar 14, 2013
	 */
	@Override
	public void disable(boolean dis) {
		if (dis) {
			this.elementWrapper.addClassName("disable");
		} else {
			this.elementWrapper.removeClassName("disable");
		}
		this.disable = dis;
		this.element.setPropertyBoolean("disabled", dis);
	}

}
