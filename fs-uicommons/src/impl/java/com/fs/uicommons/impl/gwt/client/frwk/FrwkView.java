/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 10, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicommons.impl.gwt.client.frwk.header.HeaderView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 *         <p>
 *         TODO replace ManagersView by Layout/Perspective, use Layout to define
 *         the rule of displaying based on the models.
 *         <p>
 *         There is no need to provide a complex but fixed layout for FrwkView.
 */
public class FrwkView extends ViewSupport {
	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(FrwkView.class);

	protected Map<String, Element> managerTdElements;

	/**
	 * @param ele
	 * @param ctn
	 */
	public FrwkView(String name, ContainerI ctn) {
		super(name, DOM.createDiv(), ctn);

		this.managerTdElements = new HashMap<String, Element>();

		Element top = this.createDivForPosition(FrwkModelI.M_TOP, this.element);

		{
			TableWrapper table = new TableWrapper();
			this.elementWrapper.append(table);

			TRWrapper tr = table.addTr();

			this.createTDForPosition(FrwkModelI.M_LEFT, tr);
			this.createTDForPosition(FrwkModelI.M_CENTER, tr);
			this.createTDForPosition(FrwkModelI.M_RIGHT, tr);
		}

		Element bot = this.createDivForPosition(FrwkModelI.M_BOTTOM, this.element);

		bot.setInnerHTML("This is the bottom.");

		// popup
		Element pop = this.createDivForPosition(FrwkModelI.M_POPUP, this.element);
	}

	@Override
	public FrwkModelI getModel() {
		return (FrwkModelI) this.model;
	}

	/*
	 * Jan 30, 2013
	 */
	@Override
	protected void doModel(ModelI model) {
		//
		super.doModel(model);
	}

	private Element createTDForPosition(String position, TRWrapper tr) {
		TDWrapper td = tr.addTd();

		td.addClassName("position-" + position);
		this.managerTdElements.put(position, td.getElement());
		return td.getElement();
	}

	private Element createDivForPosition(String position, Element parent) {
		Element td = DOM.createDiv();
		this.addPositionElement(position, td, parent);
		return td;
	}

	private void addPositionElement(String position, Element ele, Element parent) {
		DOM.appendChild(parent, ele);
		ele.addClassName("position-" + position);
		this.managerTdElements.put(position, ele);

	}

	/*
	 * Nov 10, 2012
	 */
	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {

		ModelI cm = ((WidgetI) cw).getModel();

		String mname = null;
		if (cm instanceof HeaderModelI) {
			mname = FrwkModelI.M_TOP;
		} else if (cm instanceof BodyModelI) {
			mname = FrwkModelI.M_CENTER;
		} else {
			throw new UiException("not supported to add child model:" + cm);
		}

		Element td = managerTdElements.get(mname);

		if (td == null) {
			throw new UiException("not supported manager name:" + mname);
		}
		DOM.appendChild(td, cw.getElement());
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI parent, ModelI cm) {
		super.processChildModelAdd(parent, cm);
		if (cm instanceof HeaderModelI) {
			this.processChildHeaderModelAdd((HeaderModelI) cm);
		} else if (cm instanceof BodyModelI) {
			this.processChildBodyModelAdd((BodyModelI) cm);
		}

	}

	private void processChildBodyModelAdd(BodyModelI cm) {
		ViewI mv = new BodyView("body", this.getContainer());
		Mvc mvc = new Mvc(cm, mv);
		mvc.start(this.model, this);

	}

	/**
	 * Nov 24, 2012
	 */

	private void processChildHeaderModelAdd(HeaderModelI cm) {
		ViewI mv = new HeaderView("header", this.getContainer());
		Mvc mvc = new Mvc(cm, mv);
		mvc.start(this.model, this);

	}

}
