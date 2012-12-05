package com.fs.uicore.api.gwt.client.model;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

public class ModelEventReplayer implements ModelI.ReplayerI {

	protected ModelI model;

	protected String uniqueKey;

	public ModelEventReplayer(ModelI model, String key) {
		this.model = model;
		this.uniqueKey = key;
	}

	@Override
	public String getUniqueKey() {
		return this.uniqueKey;
	}

	@Override
	public void replay() {
		List<UiObjectI> cl = model.getChildList(UiObjectI.class);

		for (int i = 0; i < cl.size(); i++) {
			ModelI mc = (ModelI) cl.get(i);
			new ModelChildEvent(model, mc, i, true, this.uniqueKey).dispatch();
			mc.createReplayer(this.uniqueKey).replay();
		}

		for (String pro : this.model.keyList()) {

			Object obj = this.model.getProperty(pro);

			if (obj == null) {

				continue;
			}
			if (obj instanceof ValueWrapper) {
				ValueWrapper vw = (ValueWrapper) obj;
				new ModelValueEvent(this.model, Location.valueOf(pro), vw,
						this.uniqueKey).dispatch();

			} else if (obj instanceof List) {//
				List l = (List) obj;

				for (int i = 0; i < l.size(); i++) {
					Object vi = l.get(i);
					if (vi == null || !(vi instanceof ValueWrapper)) {
						throw new UiException(
								"list property:"
										+ pro
										+ " should be access by the model setValue(...) method");
					}
					ValueWrapper vwi = (ValueWrapper) vi;
					new ModelValueEvent(this.model, Location.valueOf(pro, i),
							vwi, this.uniqueKey).dispatch();
				}

			} else {
				throw new UiException("TODO");
			}

		}

	}

	protected void dispathc(Event e) {
		e.dispatch();
	}

}
