/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fs.commons.api.ActiveContext;
import com.fs.webcomet.api.support.CollectionCometListener;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 * 
 */
public class AjaxCometServlet extends ConfigurableServletSupport {

	public static final String HK_ACTION = "x-fs-action";

	public static final String HK_TERMINAL_ID = "x-fs-terminal-id";

	public static final String HK_CLIENT_ID = "x-fs-client-id";
	

	protected Map<String,AjaxComet> sessionMap; 
	
	protected CollectionCometListener tfactory = new CollectionCometListener();

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.sessionMap = new HashMap<String,AjaxComet>();
		//
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
			AjaxComet as = new AjaxComet(tid);
			this.sessionMap.put(tid, as);//
			this.tfactory.onConnect(as);
			return;
		}
		
		String tid = req.getHeader(HK_TERMINAL_ID);
		
		String cid = req.getHeader(HK_CLIENT_ID);
		AjaxComet as = this.sessionMap.get(tid);

		if(action.equals("close")){
			this.tfactory.onClose(as,0,"");
			return;//
		}

		if(action.equals("message")){
			
			Reader rd = req.getReader();
			
			this.tfactory.onMessage(as,rd);
			
		}
		as.fetchMessages(req,res);

	}



}
