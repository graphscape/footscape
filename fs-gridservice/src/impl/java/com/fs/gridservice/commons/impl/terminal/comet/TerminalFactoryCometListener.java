/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl.terminal.comet;

import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.gridservice.commons.api.support.FacadeAwareConfigurableSupport;
import com.fs.gridservice.commons.impl.terminal.TerminalFactory;
import com.fs.webcomet.api.CometFactoryI;
import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.CometListenerI;
import com.fs.webcomet.api.CometManagerI;

/**
 * @author wu
 * 
 */
public class TerminalFactoryCometListener extends FacadeAwareConfigurableSupport implements CometListenerI {

	private static final Logger LOG = LoggerFactory.getLogger(TerminalFactoryCometListener.class);

	protected TerminalFactory<CometI> tfactory;

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		//
		CometFactoryI wf = this.container.find(CometFactoryI.class, true);
		//
		{//ws
			CometManagerI wsm = wf.getManager("websocket", "default", true);
			wsm.addListener(this);
		}
		
		{//ajax

			CometManagerI wsm = wf.getManager("ajax", "default", true);
			wsm.addListener(this);
		}

		this.tfactory = new CommetTerminalFactory(this.container, this.facade);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onConnect(CometI ws) {

		this.tfactory.onConnect(ws);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		//
		LOG.error("exception got for ws:" + ws.getId(), t);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {

		this.tfactory.onClose(ws);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onMessage(CometI ws, String ms) {
		this.tfactory.onMessage(ws, ms);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.webcomet.api.CometListenerI#onMessage(com.fs.webcomet.api.CometI,
	 * java.io.Reader)
	 */
	@Override
	public void onMessage(CometI ws, Reader reader) {
		this.tfactory.onMessage(ws, reader);
	}

}
