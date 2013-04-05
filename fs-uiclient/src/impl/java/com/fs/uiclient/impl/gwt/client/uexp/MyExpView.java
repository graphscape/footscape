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
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.uexp.ExpConnect;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.event.KeyUpEvent;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class MyExpView extends ViewSupport implements MyExpViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp msglist

	protected ListI outer;

	protected LabelI title;

	protected StringEditorI statement;

	protected ListI middle;

	protected ListI middleLeft;

	protected ListI middleRight;

	protected ButtonI more;
	// message msglist
	protected ListI msglist;

	protected ListI connected;

	protected Map<String, ExpMessage> map;

	protected Map<String, ExpConnect> map2;

	protected String expId;

	protected boolean isNew = true;

	protected DateData latestMessageTimestamp;

	/**
	 * @param ctn
	 */
	public MyExpView(ContainerI ctn, String expId) {
		super(ctn, "myexp", DOM.createDiv());
		this.expId = expId;
		this.outer = this.factory.create(ListI.class);
		this.outer.parent(this);
		{// outer/title
			this.title = this.factory.create(LabelI.class);
			this.title.parent(this.outer);
		}
		{ // close button
			final ButtonI close = this.factory.create(ButtonI.class);
			close.setText(true, "destroy");
			close.parent(this.outer);
			close.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

				@Override
				public void handle(ClickEvent t) {
					MyExpView.this.onDestroyClick();
				}
			});
		}
		// middle
		{// middle
			UiPropertiesI<Object> pts = new MapProperties<Object>();
			pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.FALSE);

			this.middle = this.factory.create(ListI.class, this.getChildName("middle"), pts);
			this.middle.parent(this.outer);
			this.middle.getElement().addClassName("myexp-middle");
		}
		{// outer/middleLeft
			{// middleLeft
				UiPropertiesI<Object> pts = new MapProperties<Object>();
				pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);

				this.middleLeft = this.factory.create(ListI.class, this.getChildName("middle-left"),pts);
				this.middleLeft.parent(this.middle);
				this.middleLeft.getElement().addClassName("myexp-middle-left");
			}
			{// middleLeft/child
				// more button
				more = this.factory.create(ButtonI.class);
				more.setText(true, "more");
				more.getElement().addClassName("more");
				more.parent(this.middleLeft);
				more.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

					@Override
					public void handle(ClickEvent t) {
						MyExpView.this.onMoreClick();
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

				this.msglist = this.factory.create(ListI.class, this.getChildName("message-list"),pts);
				this.msglist.parent(this.middleLeft);
				//TODO remove
				this.msglist.getElement().addClassName("myexp-messagelist");
			}
			// outer/middleLeft/msg input
			{
				UiPropertiesI<Object> pts = new MapProperties<Object>();
				pts.setProperty(StringEditorI.PK_TEXAREA, true);
				this.statement = this.factory.create(StringEditorI.class, this.getChildName("textarea"),pts);
				this.statement.parent(this.middleLeft);
				this.statement.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

					@Override
					public void handle(ChangeEvent t) {

					}
				});
				this.statement.addHandler(KeyUpEvent.TYPE, new EventHandlerI<KeyUpEvent>() {

					@Override
					public void handle(KeyUpEvent t) {
						if (!t.isEnter()) {
							return;
						}
						if (!t.isCtlKey()) {
							return;
						}
						MyExpView.this.onSendClick();
					}
				});
			}
			{
				// send button
				final ButtonI ok = this.factory.create(ButtonI.class);
				ok.setText(true, "send");
				ok.parent(this.middleLeft);
				ok.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

					@Override
					public void handle(ClickEvent t) {
						MyExpView.this.onSendClick();
					}
				});
			}
		}
		{
			{// middleRight
				UiPropertiesI<Object> pts = new MapProperties<Object>();
				pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);

				this.middleLeft = this.factory.create(ListI.class, this.getChildName("midle-left"),pts);
				this.middleLeft.parent(this.middle);
				this.middleLeft.getElement().addClassName("myexp-middle-left");
			}
			{

				// 
				this.connected = this.factory.create(ListI.class);
				this.connected.parent(this.middle);
				this.connected.getElement().addClassName("myexp-connected");
			}

		}
		this.map = new HashMap<String, ExpMessage>();

		this.map2 = new HashMap<String, ExpConnect>();

	}

	protected void onMoreClick() {
		MsgWrapper req = new MsgWrapper("expm/search");
		req.setHeader("isForMore", "true");
		req.setPayload("accountId2", this.getAccountId());
		req.setPayload("expId2", this.expId);
		req.setPayload("timestamp2", this.latestMessageTimestamp);
		int limit = this.getClient(true).getParameterAsInt(UiClientConstants.PK_MESSAGE_QUERY_LIMIT,10);
		req.setPayload("limit", limit);
		
		this.getClient(true).getEndpoint().sendMessage(req);
	}

	protected String getAccountId() {
		return this.getClient(true).getEndpoint().getUserInfo().getAccountId();
	}

	// close
	protected void onDestroyClick() {
		if (!Window.confirm("Do you confirm to destroy this expectation?")) {
			return;
		}

		MsgWrapper req = new MsgWrapper("/expe/close");
		req.setPayload("expId", this.expId);
		this.getClient(true).getEndpoint().sendMessage(req);//
	}

	protected void onSendClick() {
		String msg = this.statement.getData();
		if (msg == null) {
			Window.alert("Please input message before send.");
			return;
		}
		MsgWrapper req = new MsgWrapper("/expm/create");
		req.setPayload("expId1", this.expId);
		// expId2 is null, broad cast this message.
		// req.setPayload("expId2", this.expId);
		ObjectPropertiesData body = new ObjectPropertiesData();
		body.setProperty("text", msg);
		req.setPayload("body", body);
		//
		ObjectPropertiesData header = new ObjectPropertiesData();
		req.setPayload("header", header);
		req.setPayload("path", "/text-message");
		this.getClient(true).getEndpoint().sendMessage(req);//
	}

	@Override
	public void setMyExp(String body) {
		this.title.setText(body);
	}

	/*
	 * Mar 6, 2013
	 */
	@Override
	public void addOrUpdateMessage(ExpMessage msg) {
		//
		String id = msg.getId();
		ExpMessage msg2 = this.map.get(id);
		if (msg2 != null) {
			return;//
		}
		DateData ts = msg.getTimeStamp();
		if (this.latestMessageTimestamp == null || ts.getValue() > this.latestMessageTimestamp.getValue()) {
			this.latestMessageTimestamp = ts;
		}
		ExpMessageView ev = ExpMessageView.createViewForMessage(this.container, msg);
		ev.parent(this.msglist);
		this.map.put(id, msg);

	}

	/*
	 * Mar 10, 2013
	 */
	@Override
	public void addOrUpdateConnected(ExpConnect ec) {
		//
		String cid = ec.getConnectId();
		ExpConnect old = this.map2.get(cid);
		if (old != null) {
			return;
		}
		ConnectedExpView l = new ConnectedExpView(this.container, ec);

		l.parent(this.connected);

		this.map2.put(cid, ec);

	}

	/*
	 * Mar 24, 2013
	 */
	@Override
	public DateData getLatestMessageTimestamp() {
		//
		return this.latestMessageTimestamp;
	}

	/*
	 *Apr 3, 2013
	 */
	@Override
	public void noMore() {
		this.more.disable(true);
	}
}
