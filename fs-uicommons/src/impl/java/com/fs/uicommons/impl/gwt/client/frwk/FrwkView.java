/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 10, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkViewI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicommons.impl.gwt.client.frwk.header.HeaderView;
import com.fs.uicommons.impl.gwt.client.frwk.other.EndpointBusyIndicator;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
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
public class FrwkView extends ViewSupport implements FrwkViewI {
	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(FrwkView.class);

	protected Map<String, Element> managerTdElements;

	/**
	 * @param ele
	 * @param ctn
	 */
	public FrwkView(ContainerI c) {
		super(c, "frwk", DOM.createDiv());

		this.managerTdElements = new HashMap<String, Element>();

		Element top = this.createDivForPosition("top", this.element);

		{
			TableWrapper table = new TableWrapper();
			this.elementWrapper.append(table);

			TRWrapper tr = table.addTr();

			this.createTDForPosition("left", tr);
			Element ele = this.createTDForPosition("body", tr);

			this.createTDForPosition("right", tr);
		}

		Element bot = this.createDivForPosition("bottom", this.element);

		bot.setInnerHTML("This is the bottom.");

		// popup
		Element pop = this.createDivForPosition("popup", this.element);

		//

		HeaderView hv = new HeaderView(this.container);

		hv.parent(this);

		BodyView bv = new BodyView(this.container);
		bv.parent(this);
		//
		EndpointBusyIndicator ebi = new EndpointBusyIndicator(this.container);
		ebi.parent(this);
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

		Element td = this.managerTdElements.get("top");
		DOM.appendChild(td, cw.getElement());
	}

	@Override
	public BodyViewI getBodyView() {
		return this.getChild(BodyViewI.class, true);

	}

	@Override
	public HeaderViewI getHeader() {
		return this.getChild(HeaderViewI.class, true);
	}

}
