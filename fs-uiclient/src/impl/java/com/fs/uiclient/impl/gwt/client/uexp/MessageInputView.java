/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
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
public class MessageInputView extends ViewSupport {

	protected StringEditorI statement;

	protected String expId;
	
	protected ListI outer;

	/**
	 * @param ctn
	 */
	public MessageInputView(ContainerI ctn, String expId) {
		super(ctn, "myexp", DOM.createDiv());
		this.getElement().addClassName("expmi");
		this.expId = expId;
		
		{// outer/middleLeft
			{// middleLeft
				UiPropertiesI<Object> pts = new MapProperties<Object>();
				pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);

				this.outer = this.factory.create(ListI.class, "outer", pts);
				this.outer.parent(this);
			}
			// outer/middleLeft/msg input
			{
				UiPropertiesI<Object> pts = new MapProperties<Object>();
				pts.setProperty(StringEditorI.PK_TEXAREA, true);
				this.statement = this.factory.create(StringEditorI.class, this.getChildName("textarea"), pts);
				this.statement.getElement().addClassName("expmi-textarea");
				this.statement.parent(this.outer);
				this.statement.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

					@Override
					public void handle(ChangeEvent t) {
						//DO nothing
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
						MessageInputView.this.onSendClick();
					}
				});
			}
			{
				// send button
				final ButtonI ok = this.factory.create(ButtonI.class);
				ok.setText(true, "send");
				ok.parent(this.outer);
				ok.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

					@Override
					public void handle(ClickEvent t) {
						MessageInputView.this.onSendClick();
					}
				});
			}
		}
		


	}

	protected String getAccountId() {
		return this.getClient(true).getEndpoint(true).getUserInfo().getAccountId();
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
		this.getClient(true).getEndpoint(true).sendMessage(req);//
		this.statement.setData(null);
	}

	
}
