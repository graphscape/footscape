/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 12, 2013
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fs.uiclient.api.gwt.client.event.FailureMessageEvent;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wu
 * 
 */
public abstract class AbstractTestWorker extends TestWorker {
	public static void beforeTesting() {

		AccountsLDW accs = AccountsLDW.getInstance();
		accs.invalid();

	}

	protected UiClientI client;

	protected MainControlI mcontrol;

	protected ControlManagerI manager;

	protected EndPointI endpoint;

	protected List<String> tasks = new ArrayList<String>();

	protected void tryFinish(String item) {
		if (this.tasks.isEmpty()) {
			throw new UiException(this.getClass() + ",finished,but got:" + item);
		}
		String s = this.tasks.get(0);
		if (!item.equals(s)) {
			throw new UiException(this.getClass() + ",expecting:" + s + " but got:" + item + ",waiting:"
					+ this.tasks);
		}
		this.tasks.remove(0);
		System.out.println(this.getClass() + ",finished:" + item + ",waiting:" + this.tasks);
		if (this.tasks.isEmpty()) {
			super.allTaskDone();
		}
	}

	@Override
	public void start(UiClientI client) {
		this.client = client;
		this.client.getContainer().getEventBus().addHandler(new EventHandlerI<Event>() {

			@Override
			public void handle(Event t) {
				if (AbstractTestWorker.this.isDone()) {
					return;
				}
				AbstractTestWorker.this.onEvent(t);//
			}
		});
		this.endpoint = this.client.getEndpoint();
		this.manager = client.find(ControlManagerI.class, true);
		this.mcontrol = client.find(MainControlI.class, true);

	}

	public UserInfo getUserInfo(boolean force) {
		UserInfo ui = this.client.getEndpoint().getUserInfo();
		if (ui == null && force) {
			throw new UiException("no user info");
		}
		return ui;
	}

	public UserInfo getRegisterUserInfo(boolean force) {
		UserInfo ui = this.getUserInfo(false);
		if (ui == null) {
			if (force) {
				throw new UiException("no user info");
			}
			return ui;
		}
		if (ui.isAnonymous()) {
			if (force) {
				throw new UiException("anonymous user");
			}
			return null;
		}
		return ui;

	}

	public void onEvent(Event e) {

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
	public List<String> getTasks() {
		return tasks;
	}

	public boolean isDone() {
		return this.tasks.isEmpty();
	}

}
