/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.fs.uicore.impl.gwt.client.comet;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.endpoint.Address;
import com.fs.uicore.api.gwt.client.state.State;
import com.fs.uicore.api.gwt.client.support.CollectionHandler;
import com.google.gwt.user.client.Timer;

/**
 * @author wu
 * 
 */
public abstract class GometSupport implements GometI {

	public static final State UNKNOWN = State.valueOf("unknown");

	public static final State OPENNING = State.valueOf("openning");

	public static final State OPENED = State.valueOf("opened");

	public static final State CLOSED = State.valueOf("closed");

	protected String protocol;

	protected Address uri;

	protected State state;

	protected boolean errorsInOpenning;

	protected CollectionHandler<GometI> openHandlers = new CollectionHandler<GometI>();
	protected CollectionHandler<String> closeHandlers = new CollectionHandler<String>();
	protected CollectionHandler<String> errorHandlers = new CollectionHandler<String>();
	protected CollectionHandler<String> msgHandlers = new CollectionHandler<String>();

	public GometSupport(Address uri) {
		this.uri = uri;
		this.protocol = this.uri.getProtocol();
		// to process timeout error notify
		this.errorHandlers.addHandler(new HandlerI<String>() {

			@Override
			public void handle(String t) {
				GometSupport.this.onError(t);
			}
		});
	}

	public String getProtocol() {
		return this.protocol;
	}

	public boolean isState(State s) {
		return this.state.equals(s);
	}

	public void setState(State s) {
		this.state = s;
	}

	@Override
	public void open(long timeout) {

		this.setState(OPENNING);
		new Timer() {

			@Override
			public void run() {
				GometSupport.this.checkOpenTimeout();
			}
		}.schedule((int) timeout);

	}

	protected void checkOpenTimeout() {

		if (!this.isState(OPENNING)) {//
			return;
		}

		// openning, but no error event .
		if (this.errorsInOpenning) {
			// already has error report,not report timeout?
		}

		//
		this.errorHandlers.handle("timeout to open:" + this.uri);
	}

	private void onError(String msg) {
		if (this.isState(OPENNING)) {// listen to check if has error in
										// openning.
			this.errorsInOpenning = true;
		}
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addOpenHandler(final HandlerI<GometI> handler) {
		this.openHandlers.addHandler(handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addCloseHandler(final HandlerI<String> handler) {
		this.closeHandlers.addHandler(handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addErrorHandler(final HandlerI<String> handler) {
		this.errorHandlers.addHandler(handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addMessageHandler(final HandlerI<String> handler) {
		this.msgHandlers.addHandler(handler);
	}

}
