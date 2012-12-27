/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.gridservice.commons.impl.terminal.web20;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.core.api.objects.DgQueueI;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 *
 */
public class Web20TerminalFactoryServlet extends ConfigurableServletSupport {

	public static final String HK_TERMINAL_ID = "x-fs-terminal-id";
	
	protected CodecI messageCodec;

	protected DgQueueI<EventGd> global;

	protected GridFacadeI facade;
	
	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		//
		this.messageCodec = this.container.find(CodecI.FactoryI.class, true)
				.getCodec(MessageI.class);
		this.facade = this.container.find(GridFacadeI.class, true);

		this.global = this.facade.getGlogalEventQueue();//

	}
	
	/* */
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		//virtual terminal id
		String tid = arg0.getHeader(HK_TERMINAL_ID);
		
		if(tid == null){//create terminal for this web20 client.
			this.onConnection(arg0, arg1);
			return;
		}
		
		this.onMessage(tid,arg0, arg1);
		
		this.serviceGetMessage(tid,arg0,arg1);
	
	}	

	protected void serviceGetMessage(String tid,HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		//TODO long live http connection.
		//but for now,not use this terminal receive message from server side.
		//binding 
	}
	
	protected void onMessage(String tid,HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		
		Reader rd = arg0.getReader();
		Object obj = JSONValue.parse(rd);
		MessageI msg = (MessageI)this.messageCodec.decode(obj);
		String path = msg.getHeader("path");
		
		TerminalMsgReceiveEW ew = TerminalMsgReceiveEW.valueOf(path, tid, msg);
		// send to global event queue
		this.global.offer(ew.getTarget());
	
	}
	
	protected void onConnection(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		TerminalManagerI tm = this.facade
				.getEntityManager(TerminalManagerI.class);
		String address = "todo";
		PropertiesI<Object> pts = new MapProperties<Object>();
		TerminalGd td = tm.web20Terminal(address, pts);
		arg1.setHeader(HK_TERMINAL_ID, td.getId());//
	}

	
}
