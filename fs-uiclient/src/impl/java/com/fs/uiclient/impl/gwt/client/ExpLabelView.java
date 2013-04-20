/**
 *  
 */
package com.fs.uiclient.impl.gwt.client;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Cause;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

/**
 * @author wu
 * 
 */
public class ExpLabelView extends LightWeightView {

	private AnchorWI anchor;
	private String expId;

	/**
	 * @param ctn
	 */
	public ExpLabelView(ContainerI ctn) {
		super(ctn);
		this.anchor = this.factory.create(AnchorWI.class);
		this.anchor.getElement().addClassName("exp-label");
		this.anchor.parent(this);//
		this.anchor.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpLabelView.this.onClick();
			}
		});

	}

	public void setExp(String expId, String expTitle) {
		this.expId = expId;
		this.anchor.setDisplayText(expTitle);
	}

	protected void onClick() {
		if (this.expId == null) {
			return;// ignore
		}
		MainControlI mc = this.getClient(true).getChild(ControlManagerI.class, true)
				.getControl(MainControlI.class, true);
		mc.openMyExp(Cause.valueOf("expLabel"), this.expId, true);
	}

	/**
	 * 
	 */
	public void clearExp() {
		this.expId = null;
		this.anchor.setDisplayText(null);
	}

}
