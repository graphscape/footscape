/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal;

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
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.impl.gobject.WebSoketGoImpl;
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
	
	public abstract void onClose(T ws);
	
	public abstract TerminalGd create(T ws);

	public abstract void sendReady(T nativeTerminal, String id, String tid, String cid);

	public abstract String getTerminalId(T t);

	public abstract String getClientId(T t);

	public void onMessage(T ws, String ms) {
		//

		JSONArray js = (JSONArray) JSONValue.parse(ms);
		MessageI msg = (MessageI) this.messageCodec.decode(js);
		Path path = msg.getPath();

		if (path.equals(WebSocketGoI.P_CLIENT_IS_READY)) {

			this.onClientIsReadyMessage(ws, msg);
		} else {
			String tid = this.getTerminalId(ws);
			String cid = this.getClientId(ws);

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


	protected void onClose(String tid) {

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

}
