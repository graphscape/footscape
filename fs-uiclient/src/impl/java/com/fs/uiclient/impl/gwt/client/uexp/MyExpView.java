/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.coper.MyExp;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.uexp.ExpConnect;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.basic.ImageI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
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
public class MyExpView extends ViewSupport implements MyExpViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp msglist

	protected ListI outer;

	protected LabelI title;
	
	protected LabelI status;

	protected ListI middle;

	protected ListI middleLeft;

	protected ExpMessageListView messageList;

	protected MessageInputView messageInput;

	// connected exp list
	protected ConnectedExpListView connectedExpList;

	protected Map<String, ExpConnect> map2;
	
	protected ButtonI close;

	protected String expId;

	protected boolean isNew = true;

	protected ImageI image;

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
		{// outer/image,TODO

			image = this.factory.create(ImageI.class);

			image.parent(this.outer);
		}
		{//status
			this.status = this.factory.create(LabelI.class);
			this.status.parent(this.outer);//
		}
		{ // close button
			this.close = this.factory.create(ButtonI.class);
			close.setText(true, UiClientConstants.AP_EXPE_CLOSE.toString());
			close.parent(this.outer);
			close.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

				@Override
				public void handle(ClickEvent t) {
					MyExpView.this.onCloseClick();
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

				this.middleLeft = this.factory.create(ListI.class, this.getChildName("middle-left"), pts);
				this.middleLeft.parent(this.middle);
				this.middleLeft.getElement().addClassName("myexp-middle-left");
			}
			{// message list
				this.messageList = new ExpMessageListView(this.container, this.expId);
				this.messageList.setProperty(ListI.PK_LIST_ITEM_CLASSNAME,"expm-td");
				this.messageList.parent(this.middleLeft);
			}
			{// input
				this.messageInput = new MessageInputView(this.container, this.expId);
				this.messageInput.parent(this.middleLeft);
			}
		}
		{
			
			this.connectedExpList = new ConnectedExpListView(this.container, this.expId);
			
			this.connectedExpList.parent(this.middle);

		}

		this.map2 = new HashMap<String, ExpConnect>();

	}

	protected String getAccountId() {
		return this.getClient(true).getEndpoint(true).getUserInfo().getAccountId();
	}

	// close
	protected void onCloseClick() {
		if (!Window.confirm("Do you confirm to close this expectation?")) {
			return;
		}

		MsgWrapper req = new MsgWrapper("/expe/close");
		req.setPayload("expId", this.expId);
		this.getClient(true).getEndpoint(true).sendMessage(req);//
	}

	@Override
	public void setMyExp(MyExp me) {
		
		this.title.setTextAndTitle(me.getTitle(), false, me.getBody());
		
		if(!me.getIsOpen()){
			this.expClosed();
		}
		String img = me.getImage();
		
		if (img != null) {
			this.image.setSrc(img);
		}
	}

	/*
	 * Mar 6, 2013
	 */
	@Override
	public void addOrUpdateMessage(ExpMessage msg) {
		this.messageList.addOrUpdateMessage(msg);
	}

	/*
	 * Mar 10, 2013
	 */
	@Override
	public void addOrUpdateConnected(ExpConnect ec) {
		this.connectedExpList.addOrUpdateConnected(ec);

	}

	/*
	 * Mar 24, 2013
	 */
	@Override
	public DateData getLatestMessageTimestamp() {
		//
		return this.messageList.getLatestMessageTimestamp();
	}

	/*
	 * Apr 3, 2013
	 */
	@Override
	public void noMore() {
		this.messageList.setNoMore(true);
	}

	/*
	 *May 3, 2013
	 */
	@Override
	public void expClosed() {
		this.status.setText("close");
		this.close.disable(true);//
		//
	}
}
