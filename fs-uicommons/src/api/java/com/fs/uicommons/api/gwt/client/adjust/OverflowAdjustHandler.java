/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 15, 2012
 */
package com.fs.uicommons.api.gwt.client.adjust;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicommons.api.gwt.client.AdjusterI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

/**
 * @author wuzhen
 * @deprecated
 */
public class OverflowAdjustHandler implements EventHandlerI<ClickEvent> {

	@Override
	public void handle(ClickEvent e) {
		AdjusterI adj = (AdjusterI) e.getSource();
		WidgetI owner = adj.getOwner();//
		// by css
		String cname = "adjusted-" + adj.getName();

		owner.getElementWrapper().addOrRemoveClassName(cname);//

	}

	public List<ElementWrapper> getOverflowOffspring(ElementWrapper top) {
		return this.getOverflowOffspring(top, top);
	}

	// stop at the first overflowed child level
	public List<ElementWrapper> getOverflowOffspring(ElementWrapper top,
			ElementWrapper owner) {
		List<ElementWrapper> rt = new ArrayList<ElementWrapper>();
		for (ElementWrapper ew : owner.getChildElementList()) {

			if (!top.getAbsoluteRectangle().contains(
					ew.getAbsoluteRectangle())) {// overflowed
				rt.add(ew);
			} else {
				rt.addAll(this.getOverflowOffspring(top, ew));
			}

		}
		return rt;

	}

}
