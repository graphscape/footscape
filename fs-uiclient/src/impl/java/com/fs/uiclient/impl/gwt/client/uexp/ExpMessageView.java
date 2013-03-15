/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 7, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public abstract class ExpMessageView extends ViewSupport {

	public static interface CreatorI {
		public ExpMessageView create(ContainerI c, ExpMessage msg);
	}

	private static Map<Path, CreatorI> creatorMap = new HashMap<Path, CreatorI>();

	private static CreatorI DEFAULT = new CreatorI() {

		@Override
		public ExpMessageView create(ContainerI c, ExpMessage msg) {
			//
			return new DefaultEMV(c, msg);
		}
	};

	static {
		creatorMap.put(Path.valueOf("/connect/request"), CooperRequestMessageView.CRT);
		creatorMap.put(Path.valueOf("/text-message"), TextMessageView.CRT);
	}
	
	protected ExpMessage msg;
	
	protected ElementWrapper actionsDiv;
	
	protected ListI actions;

	/**
	 * @param c
	 * @param ele
	 */
	public ExpMessageView(ContainerI c, ExpMessage msg) {
		super(c, DOM.createDiv());
		this.msg = msg;
		//accountId:
		Element ele = DOM.createDiv();
		ele.setInnerText(""+msg.getNick1() + ":");
		this.element.appendChild(ele);
		//
		/*ele = DOM.createDiv();
		ele.setInnerText("expId:"+msg.getExpId1() + "");
		this.element.appendChild(ele);
		*/
		//
		ele = DOM.createDiv();
		ele.setInnerText(""+msg.getExpBody1() + "");
		this.element.appendChild(ele);
		
		// actions
		ele = DOM.createDiv();
		this.element.appendChild(ele);
		this.actionsDiv = new ElementWrapper(ele);
		this.actions = this.factory.create(ListI.class);//
		this.actions.parent(this);
		//
		
	}

	public static ExpMessageView createViewForMessage(ContainerI c, ExpMessage msg) {
		Path path = msg.getPath();
		CreatorI crt = creatorMap.get(path);
		if (crt == null) {
			crt = DEFAULT;
		}
		return crt.create(c, msg);

	}

	/*
	 * Mar 8, 2013
	 */
	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		if (ceo instanceof ListI) {// actions
			this.actionsDiv.append(ceo.getElement());
		} else {
			super.processAddChildElementObject(ceo);
		}
	}

}
