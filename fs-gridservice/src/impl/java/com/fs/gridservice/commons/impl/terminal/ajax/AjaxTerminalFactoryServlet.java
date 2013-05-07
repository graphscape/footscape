/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.gridservice.commons.impl.terminal.ajax;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fs.commons.api.ActiveContext;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.impl.terminal.TerminalFactory;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 * 
 */
public class AjaxTerminalFactoryServlet extends ConfigurableServletSupport {

	public static final String HK_ACTION = "x-fs-action";

	public static final String HK_TERMINAL_ID = "x-fs-terminal-id";

	public static final String HK_CLIENT_ID = "x-fs-client-id";
	

	protected GridFacadeI facade;
	
	protected TerminalFactory<AjaxSession> tfactory;
	
	protected Map<String,AjaxSession> sessionMap; 

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.sessionMap = new HashMap<String,AjaxSession>();
		//
		this.facade = this.container.find(GridFacadeI.class, true);

		this.tfactory = new AjaxTerminalFactory(this.container,this.facade);
	}

	/* */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		// virtual terminal id
		String action = req.getHeader(HK_ACTION);
		
		if(action == null){
			
			return;//ignore
		}
		
		if(action.equals("connect")){
			//do connection
			String tid = UUID.randomUUID().toString();
			AjaxSession as = new AjaxSession(tid);
			this.sessionMap.put(tid, as);//
			this.tfactory.onConnect(as);
			return;
		}
		
		String tid = req.getHeader(HK_TERMINAL_ID);
		
		String cid = req.getHeader(HK_CLIENT_ID);
		AjaxSession as = this.sessionMap.get(tid);

		if(action.equals("close")){
			this.tfactory.onClose(as);
			return;//
		}

		if(action.equals("message")){
			
			Reader rd = req.getReader();
			
			this.tfactory.onMessage(as,rd);
			
		}
		as.fetchMessages(req,res);

	}



}
