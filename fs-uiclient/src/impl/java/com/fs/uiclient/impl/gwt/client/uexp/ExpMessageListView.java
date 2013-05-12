/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class ExpMessageListView extends ViewSupport {

	protected ListI outer;

	protected ButtonI more;
	// message msglist
	protected ListI msglist;

	protected Map<String, ExpMessage> map;

	protected String expId;

	protected DateData firstMessageTimestamp;

	protected DateData latestMessageTimestamp;

	/**
	 * @param ctn
	 */
	public ExpMessageListView(ContainerI ctn, String expId) {
		super(ctn, "myexp", DOM.createDiv());
		this.map = new HashMap<String, ExpMessage>();
		this.element.addClassName("expm-list");

		this.expId = expId;

		{// outer/middleLeft
			{// middleLeft
				UiPropertiesI<Object> pts = new MapProperties<Object>();
				pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);

				this.outer = this.factory.create(ListI.class, "messages", pts);
				this.outer.parent(this);
			}
			{// middleLeft/child
				// more button
				more = this.factory.create(ButtonI.class);
				more.setText(true, UiClientConstants.AP_EXPM_MORE.toString());
				more.getElement().addClassName("more");
				more.parent(this.outer);
				more.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

					@Override
					public void handle(ClickEvent t) {
						ExpMessageListView.this.onMoreClick();
					}
				});
			}
			{// middleLeft/messagelist
				UiPropertiesI<Object> pts = new MapProperties<Object>();
				pts.setProperty(ListI.PK_COMPARATOR, new Comparator<ExpMessageView>() {

					@Override
					public int compare(ExpMessageView o1, ExpMessageView o2) {
						//
						return (int) (o1.getExpMessage().getTimeStamp().getValue() - o2.getExpMessage()
								.getTimeStamp().getValue());
					}
				});

				this.msglist = this.factory.create(ListI.class, this.getChildName("message-list"), pts);
				this.msglist.parent(this.outer);
				// TODO remove
			}

		}

		

	}

	protected void onMoreClick() {
		MsgWrapper req = new MsgWrapper("expm/search");
		req.setHeader("isForMore", "true");
		req.setPayload("accountId2", this.getAccountId());
		req.setPayload("expId2", this.expId);
		req.setPayload("timestamp2", this.firstMessageTimestamp);
		int limit = this.getClient(true).getParameterAsInt(UiClientConstants.PK_MESSAGE_QUERY_LIMIT, 10);
		req.setPayload("limit", limit);

		this.getClient(true).getEndpoint(true).sendMessage(req);
	}

	protected String getAccountId() {
		return this.getClient(true).getEndpoint(true).getUserInfo().getAccountId();
	}

	// close
	protected void onDestroyClick() {
		if (!Window.confirm("Do you confirm to destroy this expectation?")) {
			return;
		}

		MsgWrapper req = new MsgWrapper("/expe/close");
		req.setPayload("expId", this.expId);
		this.getClient(true).getEndpoint(true).sendMessage(req);//
	}

	/*
	 * Mar 6, 2013
	 */
	public void addOrUpdateMessage(ExpMessage msg) {
		//
		String id = msg.getId();
		ExpMessage msg2 = this.map.get(id);
		if (msg2 != null) {
			return;//
		}
		DateData ts = msg.getTimeStamp();
		boolean first = false;
		if (this.latestMessageTimestamp == null || ts.getValue() > this.latestMessageTimestamp.getValue()) {
			this.latestMessageTimestamp = ts;
			first = false;
		}
		if (this.firstMessageTimestamp == null || ts.getValue() < this.firstMessageTimestamp.getValue()) {
			this.firstMessageTimestamp = ts;
			first = true;
		}

		ExpMessageView ev = ExpMessageView.createViewForMessage(this.container, msg);
		ev.parent(this.msglist);
		this.map.put(id, msg);
		//scroll top to show the new message.
		int scroll = 0;
		if(!first){
			scroll = 10000;
		}
		this.getElement().setPropertyInt("scrollTop", scroll);
		// Scroll to
		// bottom.
	}

	public DateData getLatestMessageTimestamp() {
		//
		return this.latestMessageTimestamp;
	}

	public void noMore() {
		this.more.disable(true);
	}
}
