/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 23, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.event.StateChangeEvent;
import com.fs.uicore.api.gwt.client.state.State;
import com.fs.uicore.api.gwt.client.state.StatefulI;

/**
 * @author wu
 * 
 */
public class StatefulUiObjectSupport extends UiObjectSupport implements
		StatefulI {

	/**
	 * @param name
	 */
	public StatefulUiObjectSupport() {
		this(null);
	}

	public StatefulUiObjectSupport(String name) {
		super(name);

	}

	protected State state;

	@Override
	public State getState() {
		return this.state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.state.StatefulI#isState(com.fs.uicore.api
	 * .gwt.client.state.State)
	 */
	@Override
	public boolean isState(State s) {
		return s.equals(this.state);
	}

	protected void setState(State news) {
		this.state = news;
		new StateChangeEvent(this).dispatch();
	}

}
