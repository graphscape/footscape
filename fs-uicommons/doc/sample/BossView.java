/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 10, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagerModelI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
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
public class BossView extends ViewSupport {
	private static final UiLoggerI LOG = UiLoggerFactory
			.getLogger(BossView.class);

	protected Map<String, Element> managerTdElements;

	/**
	 * @param ele
	 * @param ctn
	 */
	public BossView(String name, ContainerI ctn) {
		super(name, DOM.createDiv(), ctn);

		this.managerTdElements = new HashMap<String, Element>();

		Element top = this.createDivForPosition(BossModelI.M_TOP, this.element);

		{
			TableWrapper table = new TableWrapper();
			this.elementWrapper.append(table);

			TRWrapper tr = table.addTr();

			this.createTDForPosition(BossModelI.M_LEFT, tr);
			this.createTDForPosition(BossModelI.M_CENTER, tr);
			this.createTDForPosition(BossModelI.M_RIGHT, tr);
		}

		Element bot = this.createDivForPosition(BossModelI.M_BOTTOM,
				this.element);

		bot.setInnerHTML("This is the bottom.");

		// popup
		Element pop = this.createDivForPosition(BossModelI.M_POPUP,
				this.element);
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
		if (!(cm instanceof ManagerModelI)) {
			throw new UiException("not a manager model for:" + cw);
		}

		String mname = cm.getName();// the manager name,

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
		if (cm instanceof ManagerModelI) {
			this.processChildManagerModelAdd((ManagerModelI) cm);
		}
	}

	/**
	 * Nov 24, 2012
	 */

	private void processChildManagerModelAdd(ManagerModelI cm) {
		ViewI mv = null;
		if (cm.getName().equals(BossModelI.M_POPUP)) {
			mv = new PopupManagerView("manager", this.getContainer());
		} else {
			mv = new ManagerView("manager", this.getContainer());
		}
		Mvc mvc = new Mvc(cm, mv);
		mvc.start(this.model, this);

	}

}
