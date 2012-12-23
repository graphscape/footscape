/**
 * 
 */
package com.fs.uicore.api.gwt.client.simple;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueDeliverI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.SimpleValueDeliverSupport;

/**
 * @author wuzhen
 * 
 */
public class SimpleValueDeliver<S, T> extends SimpleValueDeliverSupport<S, T> {

	protected ValuePoint<S> sourcePoint;

	public SimpleValueDeliver(ModelI model1, Location loc1, ModelI model2,
			Location loc2) {
		this(model1, loc1, new ValuePoint<T>(model2, loc2));
	}

	public SimpleValueDeliver(ModelI model1, Location loc1,
			ValueSourceI<T> target) {
		super(target);
		this.sourcePoint = new ValuePoint<S>(model1, loc1);
	}

	@Override
	public ValueDeliverI start() {

		EventHandlerI<ModelValueEvent> eh = new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {

				SimpleValueDeliver.this.deliver((S) e.getValue());

			}
		};

		// deliver the future change.
		this.sourcePoint.model.addValueHandler(this.sourcePoint.location, eh);// listen
																				// to
																				// the
																				// location.

		// deliver the current first value.
		Object obj = this.sourcePoint.getValue();

		this.deliver((S) obj);
		return this;
	}

}
