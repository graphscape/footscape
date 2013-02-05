/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 15, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.support;

import com.fs.uicommons.api.gwt.client.AdjusterI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.support.ElementObjectSupport;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wuzhen
 * 
 */
public class AdjustersHelper extends ElementObjectSupport {

	public AdjustersHelper(ContainerI c) {
		super(c, DOM.createDiv());

		this.elementWrapper.addClassName("adjusters");// by css to absolute
														// position.
	}

	public ElementObjectI parent() {
		return (ElementObjectI) this.parent;
	}

	@Override
	public void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();

		this.parent().addGwtHandler(MouseOverEvent.getType(), new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				AdjustersHelper.this.visible(true);
			}
		});
		this.parent().addGwtHandler(MouseOutEvent.getType(), new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				AdjustersHelper.this.visible(false);
			}
		});
	}

	protected void visible(boolean vis) {
		if (vis) {
			this.elementWrapper.moveTo(this.parent().getElementWrapper().getAbsoluteRectangle().getTopLeft());// TODO
																												// right,top
		}
		this.elementWrapper.addAndRemoveClassName(vis, "visible", "invisible");

	}

	/**
	 * @param rt
	 */
	public AdjusterI addAdjuster(String name) {
		AdjusterImpl rt = new AdjusterImpl(this.container, name);
		DOM.appendChild(this.element, rt.getElement());// TODO layout?
		rt.parent(this);

		return rt;
	}

}
