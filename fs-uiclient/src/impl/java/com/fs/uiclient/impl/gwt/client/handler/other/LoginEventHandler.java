/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 13, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.HeaderNames;
import com.fs.uiclient.impl.gwt.client.tasks.ActivityRefreshHandler;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.scheduler.SchedulerI;

/**
 * @author wu
 * 
 */
public class LoginEventHandler extends UiHandlerSupport implements EventHandlerI<UserLoginEvent> {

	/**
	 * @param c
	 */
	public LoginEventHandler(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void handle(UserLoginEvent t) {
		// update header item
		UserInfo ui = t.getUserInfo();
		// HeaderViewI hm =
		// this.getClient(true).getRoot().find(HeaderViewI.class, true);
		// header items
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		MainControlI mc = this.getControl(MainControlI.class, true);

		// open exp search view
		if (ui.isAnonymous()) {// ?or not remove this item,but notify user no
								// right?
			fc.tryRemoveHeaderItem(HeaderNames.H2_PROFILE);
		} else {
			fc.addHeaderItemIfNotExist(HeaderNames.H2_PROFILE);
			mc.openUeList(true);//
		}
		mc.closeAll();
		mc.openExpSearch(true).search();//
		// close login view if opened
		mc.closeLoginView();
		// open user exp msglist view.

		//
		// this.activeTasks();
	}

	private void activeTasks() {
		String secS = this.getClient(true).getParameter("refresh.interval", "60S");
		if (secS.endsWith("S")) {
			secS = secS.substring(0, secS.length() - 1);
		} else {
			throw new UiException("TODO:" + secS);
		}
		int intervalMS = Integer.parseInt(secS) * 1000;// ms

		SchedulerI sc = this.container.get(SchedulerI.class, true);

		sc.scheduleRepeat("activity-refresh", intervalMS,// 2 sec
				new ActivityRefreshHandler());
	}

}
