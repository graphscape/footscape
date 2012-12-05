package com.fs.uicore.api.gwt.client.efilter;

import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

public class ModelValueEventFilter extends ModelEventFilter implements
		Event.FilterI {

	private Location location;

	private Object value;

	public ModelValueEventFilter(Location loc) {
		this(loc, null, null);
	}

	public ModelValueEventFilter(Location loc, Object value) {
		this(loc, value, null);
	}

	protected ModelValueEventFilter(Location loc, Object value, String rep) {
		super(ModelValueEvent.TYPE, rep);
		this.location = loc;
		this.value = value;
	}

	@Override
	public <T extends Event> T filter(Event e) {

		if (null == super.filter(e)) {
			return null;
		}
		ModelValueEvent mve = (ModelValueEvent) e;

		if (!mve.getLocation().isSubSetOf(this.location)) {
			return null;
		}
		if (value != null && !value.equals(mve.getValueWrapper().getValue())) {
			return null;
		}
		return (T) mve;

	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		return super.toString() + ",location:" + this.location + ",value:"
				+ value;
	}

}
