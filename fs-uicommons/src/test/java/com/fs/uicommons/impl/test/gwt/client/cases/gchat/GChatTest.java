/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.gchat;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatJoinEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.impl.gwt.client.gchat.GChatView;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wu
 * 
 */
public class GChatTest extends TestBase {

	public void testGroupChat() {
		
		this.finishing.add("join");
		this.finishing.add("message");
	
		this.delayTestFinish(this.timeoutMillis*100);
		
		HeaderModelI hc = this.framework.getHeader();
		HeaderModelI.ItemModel tools = hc.getItem("tools", true);
		HeaderModelI.ItemModel gchat = tools.getItem("gchat", true);
		
		//open the LazyMvcI of gchat
		gchat.trigger();
		
	}

	/*
	 *Dec 23, 2012
	 */
	@Override
	protected void onEvent(Event e) {
		super.onEvent(e);
		if(e instanceof AttachedEvent){
			Object src = e.getSource() ;
			if(src instanceof GChatView){
				this.onGChatOK();
			}
			
		}
	}

	/**
	 *Dec 23, 2012
	 */
	private void onGChatOK() {
		GChatControlI cc = this.client.find(GChatControlI.class, true);
		cc.addHandler(GChatJoinEvent.TYPE,new EventHandlerI<GChatJoinEvent>(){

			@Override
			public void handle(GChatJoinEvent t) {
				GChatTest.this.onJoinEvent(t);
			}});
		GChatModel cm = this.rootModel.find(GChatModel.class, true);
		cm.setGroupIdToJoin("group-001");
		ControlUtil.triggerAction(cm, GChatModel.A_JOIN);
	}

	/**
	 *Dec 23, 2012
	 */
	protected void onJoinEvent(GChatJoinEvent t) {
		System.out.println("joinevent:"+t);
		this.tryFinish("join");
		
	}
	
}
