/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 12, 2013
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import java.util.HashSet;
import java.util.Set;

import com.fs.uiclient.api.gwt.client.event.FailureMessageEvent;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wu
 * 
 */
public abstract class TestWorker {
	public static void beforeTesting() {

		AccountsLDW accs = AccountsLDW.getInstance();
		accs.invalid();

	}

	protected UiClientI client;

	protected MainControlI mcontrol;

	protected ControlManagerI manager;
	
	protected EndPointI endpoint;

	protected Set<String> tasks = new HashSet<String>();

	protected void tryFinish(String item) {
		this.tasks.remove(item);
		System.out.println("finished:" + item + ",waiting:" + this.tasks);
		if (this.tasks.isEmpty()) {
		}
	}

	public void start(UiClientI client) {
		this.client = client;
		this.client.getContainer().getEventBus().addHandler(new EventHandlerI<Event>() {

			@Override
			public void handle(Event t) {
				TestWorker.this.onEvent(t);//
			}
		});
		this.endpoint = this.client.getEndpoint();
		this.manager = client.find(ControlManagerI.class, true);
		this.mcontrol = client.find(MainControlI.class, true);

	}

	public void onEvent(Event e) {
		if (this.isDone()) {
			return;
		}
		System.out.println("TestWroker.onEvent:" + e);

		if (e instanceof AttachedEvent) {
			AttachedEvent ae = (AttachedEvent) e;
			this.onAttachedEvent(ae);
		} else if (e instanceof SuccessMessageEvent) {
			this.onSuccessMessageEvent((SuccessMessageEvent) e);
		} else if (e instanceof FailureMessageEvent) {
			this.onFailureMessageEvent((FailureMessageEvent) e);
		}

	}

	/**
	 * @param ae
	 */
	protected void onAttachedEvent(AttachedEvent ae) {

	}

	/**
	 * Jan 3, 2013
	 */
	protected void onFailureMessageEvent(FailureMessageEvent e) {
		//
		System.err.println("failure message " + e.getMessage());
	}

	/**
	 * Jan 3, 2013
	 */
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		//

	}

	/**
	 * @return the tasks
	 */
	public Set<String> getTasks() {
		return tasks;
	}

	public boolean isDone() {
		return this.tasks.isEmpty();
	}

}
