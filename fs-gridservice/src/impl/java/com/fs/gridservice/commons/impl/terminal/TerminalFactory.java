/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal;

import java.io.Reader;
import java.io.StringReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gobject.EndPointGoI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.core.api.objects.DgQueueI;
import com.fs.websocket.api.WebSocketI;

/**
 * @author wu
 * 
 */
public abstract class TerminalFactory<T> {

	private static final Logger LOG = LoggerFactory.getLogger(TerminalFactory.class);

	protected GridFacadeI facade;

	protected CodecI messageCodec;

	protected DgQueueI<EventGd> global;

	public TerminalFactory(ContainerI con, GridFacadeI gf) {
		this.facade = gf;
		this.global = this.facade.getGlogalEventQueue();//
		this.messageCodec = con.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);

	}

	/**
	 * @param wso
	 */
	public abstract void onConnect(T ws);

	public void onClose(T ws) {
		EndPointGoI wso = this.getEndPointGo(ws);

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
	}

	public abstract EndPointGoI getEndPointGo(T ws);

	public TerminalGd create(T ws) {
		TerminalManagerI tm = this.facade.getEntityManager(TerminalManagerI.class);
		EndPointGoI wso = this.getEndPointGo(ws);
		TerminalGd t = tm.createTerminal(wso);
		return t;
	}

	public void sendReady(T ws, String id, String tid, String cid) {
		EndPointGoI wso = getEndPointGo(ws);
		wso.sendReady(id, tid, cid);//
	}

	public void onMessage(T ws, String ms) {

		Reader rd = new StringReader(ms);
		this.onMessage(ws, rd);

	}

	public void onMessage(T ws, Reader ms) {
		//

		JSONArray js = (JSONArray) JSONValue.parse(ms);
		MessageI msg = (MessageI) this.messageCodec.decode(js);
		Path path = msg.getPath();

		if (path.equals(EndPointGoI.P_CLIENT_IS_READY)) {

			this.onClientIsReadyMessage(ws, msg);
		} else {

			String tid = this.getEndPointGo(ws).getTerminalId(true);
			String cid = this.getEndPointGo(ws).getClientId(true);

			this.onAppMessage(tid, cid, path, msg);
		}
	}

	public void onClientIsReadyMessage(T nativeTerminal, MessageI msg) {

		// String wsoId = wso.getId();

		// create client

		TerminalManagerI tm = this.facade.getEntityManager(TerminalManagerI.class);
		TerminalGd t = this.create(nativeTerminal);
		// cannot get this manager in active();it not available at that time.
		ClientManagerI cm = this.facade.getEntityManager(ClientManagerI.class);

		ClientGd cg = cm.createClient(t.getId());
		String cid = cg.getId();
		tm.bindingClient(t.getId(), cid);//
		this.sendReady(nativeTerminal, msg.getId(), t.getId(), cid);//
	}

	public void onAppMessage(String tId, String cid, Path path, MessageI msg) {

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

}
