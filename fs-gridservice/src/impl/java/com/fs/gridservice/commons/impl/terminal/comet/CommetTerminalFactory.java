/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal.comet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.gobject.EndPointGoI;
import com.fs.gridservice.commons.impl.gobject.EndPointGoImpl;
import com.fs.gridservice.commons.impl.terminal.TerminalFactory;
import com.fs.webcomet.api.CometI;

/**
 * @author wu
 * 
 */
public class CommetTerminalFactory extends TerminalFactory<CometI> {

	private static final Logger LOG = LoggerFactory.getLogger(CommetTerminalFactory.class);

	protected static String PK_WSGO = "_webSocketGo";

	/**
	 * @param gf
	 */
	public CommetTerminalFactory(ContainerI c, GridFacadeI gf) {
		super(c, gf);
	}

	@Override
	public void onConnect(CometI ws) {
		// TODO Auto-generated method stub
		EndPointGoI wso = new EndPointGoImpl(ws, this.messageCodec);
		setEPG(ws, wso);

		if (LOG.isDebugEnabled()) {
			LOG.debug("onConnected," + wso.getProtocol() + ",endpointId:" + wso.getId() + ",cometId:"
					+ ws.getId());
		}

	}

	public static void setEPG(CometI ws, EndPointGoI wso) {
		ws.setProperty(PK_WSGO, wso);
	}

	public EndPointGoI getEndPointGo(CometI ws) {

		return (EndPointGoI) ws.getProperty(PK_WSGO);
	}

}
