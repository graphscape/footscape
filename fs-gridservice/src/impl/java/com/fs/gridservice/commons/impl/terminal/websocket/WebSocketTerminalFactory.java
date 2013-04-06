/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl.terminal.websocket;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.support.FacadeAwareConfigurableSupport;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.impl.gobject.WebSoketGoImpl;
import com.fs.gridservice.core.api.objects.DgQueueI;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.WsManagerI;

/**
 * @author wu
 * 
 */
public class WebSocketTerminalFactory extends FacadeAwareConfigurableSupport implements WsListenerI {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketTerminalFactory.class);

	protected static String PK_WSGO = "_webSocketGo";

	protected CodecI messageCodec;

	protected DgQueueI<EventGd> global;

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		//
		this.messageCodec = this.container.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);
		// listen to the wsmanagerI
		WsFactoryI wf = this.container.find(WsFactoryI.class, true);
		WsManagerI wsm = wf.getManager("default", true);// TODO new wsm
		wsm.addListener(this);

		this.global = this.facade.getGlogalEventQueue();//

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		WebSocketGoI wso = new WebSoketGoImpl(ws, this.messageCodec);

		setWso(ws, wso);

		if (LOG.isDebugEnabled()) {
			LOG.debug("onConnected,wsoId:" + wso.getId() + ",wsId:" + ws.getId());
		}

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		//
		LOG.error("exception got for ws:" + ws.getId(), t);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		WebSocketGoI wso = this.getWso(ws);

		if (LOG.isDebugEnabled()) {
			LOG.debug("onClose,wsoId:" + wso.getId() + ",wsId:" + ws.getId());
		}

		// server is ready means the terminal object is created and bind to this
		// WS.
		// and also the Client object is created and bind to this terminal.
		// TODO move to a common place for exit logic

		String tid = wso.getTerminalId(false);
		if (tid == null) {//
			LOG.warn("ws closed,but no terminal is bound with.");
			return;
		}

		TerminalManagerI tm = this.facade.getEntityManager(TerminalManagerI.class);
		TerminalGd t = tm.removeEntity(tid);
		String cid = t.getClientId(false);
		if (cid == null) {
			LOG.warn("terminal:" + tid + " destroyed, but no client bound with.");
		} else {
			ClientManagerI cm = this.facade.getEntityManager(ClientManagerI.class);

			ClientGd cg = cm.removeEntity(cid);
		}
		String sid = t.getSessionId(false);
		if (sid == null) {
			LOG.warn("terminal:" + tid + " destroyed,but no session bound with.");
		} else {
			SessionManagerI sm = this.facade.getEntityManager(SessionManagerI.class);
			SessionGd sg = sm.removeEntity(sid);

		}

		//

	}

	public static void setWso(WebSocketI ws, WebSocketGoI wso) {
		ws.setProperty(PK_WSGO, wso);
	}

	public static WebSocketGoI getWso(WebSocketI ws) {
		return (WebSocketGoI) ws.getProperty(PK_WSGO);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String ms) {
		//
		if (LOG.isDebugEnabled()) {
			LOG.debug("onMessage,wsId:" + ws.getId() + ",ms:" + ms);
		}
		JSONArray js = (JSONArray) JSONValue.parse(ms);
		MessageI msg = (MessageI) this.messageCodec.decode(js);
		Path path = msg.getPath();
		WebSocketGoI wso = getWso(ws);
		if (path.equals(WebSocketGoI.P_CLIENT_IS_READY)) {
			this.onClientIsReadyMessage(msg, wso);
		} else {
			this.onAppMessage(wso, path, msg);
		}
	}

	private void onAppMessage(WebSocketGoI wso, Path path, MessageI msg) {

		String tId = wso.getTerminalId(true);// assign the ws id.
		String cid = wso.getClientId(true);
		// NOTE,below is a new message,which payloaded the msg as nested.
		// NOTE,the two message with same id.
		// this is a event,of which the path is not same as the message from
		// client, but a prefix on that
		Path eventPath = Path.valueOf("events", path);

		TerminalMsgReceiveEW ew = TerminalMsgReceiveEW.valueOf(eventPath, tId, cid, msg);

		// eventWrapper->target:EventGd->payload:Message
		// RequestI->payload:EventGd->payload:Message
		//
		ew.getTarget().setHeader(MessageI.HK_RESPONSE_ADDRESS, msg.getHeader(MessageI.HK_RESPONSE_ADDRESS));
		ew.getTarget().setHeader(MessageI.HK_SILENCE, msg.getHeader(MessageI.HK_SILENCE));
		if (LOG.isDebugEnabled()) {
			LOG.debug("new ws message event:" + ew.getTarget());
		}
		// send to global event queue
		this.global.offer(ew.getTarget());

	}

	private void onClientIsReadyMessage(MessageI msg, WebSocketGoI wso) {

		// String wsoId = wso.getId();

		// create client

		TerminalManagerI tm = this.facade.getEntityManager(TerminalManagerI.class);
		TerminalGd t = tm.webSocketTerminal(wso);
		// cannot get this manager in active();it not available at that time.
		ClientManagerI cm = this.facade.getEntityManager(ClientManagerI.class);

		ClientGd cg = cm.createClient(t.getId());
		String cid = cg.getId();
		tm.bindingClient(t.getId(), cid);//

		wso.sendReady(msg.getId(), t.getId(), cid);//
	}

}
