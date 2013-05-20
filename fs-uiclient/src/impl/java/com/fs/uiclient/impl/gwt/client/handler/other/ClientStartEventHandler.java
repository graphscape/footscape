/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 13, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.impl.gwt.client.HeaderNames;
import com.fs.uiclient.impl.gwt.client.handler.message.ContactMsgFailureMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ContactMsgSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.CooperConfirmSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.CooperRequestSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpClosedNotifyMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpConnectCreatedNotifyMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpConnectSearchMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpDeletedNotifyMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpEditSubmitFailureMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpEditSubmitSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpGetMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpMessageCreatedNotifyMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpMessageMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpSearchMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ProfileInitSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ProfileSubmitFailureMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ProfileSubmitSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ResourceGetSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.SignupSubmitFailureMH;
import com.fs.uiclient.impl.gwt.client.handler.message.SignupSubmitSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.SuccessOrFailureEventMH;
import com.fs.uiclient.impl.gwt.client.handler.message.UeListRefreshMH;
import com.fs.uiclient.impl.gwt.client.handler.message.UserInfoSuccessMH;
import com.fs.uicommons.api.gwt.client.frwk.BottomViewI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;

/**
 * @author wu
 * 
 */
public class ClientStartEventHandler extends UiHandlerSupport implements EventHandlerI<AfterClientStartEvent> {

	/**
	 * @param c
	 */
	public ClientStartEventHandler(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void handle(AfterClientStartEvent t) {
		this.activeMessageHandlers(this.container, t.getClient());//
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		//
		fc.addHeaderItem(HeaderNames.H1_LOGO, true);//left
		// right
		fc.addHeaderItemIfNotExist(HeaderNames.H1_SEARCH);
		fc.addHeaderItemIfNotExist(HeaderNames.H1_MYEXP);//anonymous will notify:no right to show exp
		fc.addHeaderItemIfNotExist(HeaderNames.H1_CREATE);//
		fc.addHeaderItem(HeaderNames.H2_SIGNUP);
		//
		BottomViewI bv = fc.getBottomView();
		//bv.addItem(HeaderNames.H1_ABOUT);
		bv.addItem(HeaderNames.H1_CONTACT);
		bv.addItem(HeaderNames.H1_CONSOLE);//
		
	}
	
	private void activeMessageHandlers(ContainerI c, UiClientI client) {
		EndPointI dis = client.getEndpoint(true);
		dis.addHandler(Path.valueOf("/endpoint/message"), new SuccessOrFailureEventMH(c));
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/cooper/request/success"),
				new CooperRequestSuccessMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/cooper/confirm/success"),
				new CooperConfirmSuccessMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/expc/search/success"), new ExpConnectSearchMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/expe/submit/success"), new ExpEditSubmitSuccessMH(c));// create
		dis.addHandler(Path.valueOf("/endpoint/message/expe/submit/failure"), new ExpEditSubmitFailureMH(c));// create
		// exp
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/exps/search/success"), new ExpSearchMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/exps/get/success"), new ExpGetMH(c));// search

		dis.addHandler(Path.valueOf("/endpoint/message/expm/search/success"), new ExpMessageMH(c));// search
		//
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/notify/exp-message-created"),
				new ExpMessageCreatedNotifyMH(c));// search

		dis.addHandler(Path.valueOf("/endpoint/message/notify/exp-connect-created"),
				new ExpConnectCreatedNotifyMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/notify/exp-closed"), new ExpClosedNotifyMH(c));
		
		dis.addHandler(Path.valueOf("/endpoint/message/notify/exp-deleted"), new ExpDeletedNotifyMH(c));

		dis.addHandler(Path.valueOf("/endpoint/message/profile/init/success"), new ProfileInitSuccessMH(c));//
		
		dis.addHandler(Path.valueOf("/endpoint/message/cttmsg/submit/success"), new ContactMsgSuccessMH(c));//
		
		dis.addHandler(Path.valueOf("/endpoint/message/cttmsg/submit/failure"), new ContactMsgFailureMH(c));//

		dis.addHandler(Path.valueOf("/endpoint/message/profile/submit/failure"),
				new ProfileSubmitFailureMH(c));//
		dis.addHandler(Path.valueOf("/endpoint/message/profile/submit/success"),
				new ProfileSubmitSuccessMH(c));//
		// exp
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/uelist/refresh/success"), new UeListRefreshMH(c));// refresh
		dis.addHandler(Path.valueOf("/endpoint/message/signup/submit/success"), new SignupSubmitSuccessMH(c));//
		// succ
		dis.addHandler(Path.valueOf("/endpoint/message/signup/submit/failure"), new SignupSubmitFailureMH(c));//

		// open resource
		dis.addHandler(Path.valueOf("/endpoint/message/resource/get/success"), new ResourceGetSuccessMH(c));//
		
		// userinfo
		dis.addHandler(Path.valueOf("/endpoint/message/uinfo/get/success"), new UserInfoSuccessMH(c));//

	}


}
