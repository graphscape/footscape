/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.gobject.EndPointGoI;
import com.fs.gridservice.commons.impl.gobject.AjaxEndPointGoImpl;
import com.fs.gridservice.commons.impl.terminal.TerminalFactory;

/**
 * @author wu
 * 
 */
public class AjaxTerminalFactory extends TerminalFactory<AjaxSession> {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxTerminalFactory.class);

	protected static String PK_WSGO = "_webSocketGo";

	/**
	 * @param gf
	 */
	public AjaxTerminalFactory(ContainerI c, GridFacadeI gf) {
		super(c, gf);
	}

	/**
	 * @param wso
	 */
	public void onConnect(AjaxSession ws) {
		// TODO Auto-generated method stub
		EndPointGoI wso = new AjaxEndPointGoImpl(ws, this.messageCodec);
		setWso(ws, wso);

		if (LOG.isDebugEnabled()) {
			LOG.debug("onConnected,wsoId:" + wso.getId() + ",wsId:" + ws.getId());
		}

	}

	public static void setWso(AjaxSession ws, EndPointGoI wso) {
		ws.setProperty(PK_WSGO, wso);
	}

	public EndPointGoI getEndPointGo(AjaxSession ws) {

		return (EndPointGoI) ws.getProperty(PK_WSGO);
	}

}
