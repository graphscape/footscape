/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.fs.commons.impl.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fs.commons.api.lang.Todo;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.session.SessionI;
import com.fs.commons.api.session.SessionManagerI;
import com.fs.commons.api.support.CollectionHandler;

/**
 * @author wu
 * 
 */
public class SessionManagerImpl implements SessionManagerI {

	private String name;

	private Map<String, SessionI> sessions = Collections.synchronizedMap(new HashMap<String, SessionI>());

	private CollectionHandler<SessionI> timeoutHandlers = new CollectionHandler<SessionI>();

	private SessionServerImpl server;

	public SessionManagerImpl(String name){
		this.name = name;
	}
	/*
	 * May 10, 2013
	 */
	@Override
	public String getName() {
		//
		return name;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI getSession(String id) {
		//
		SessionI s = this.sessions.get(id);
		if (s != null && s.isTimeout()) {
			this.doTimeout(s);
			s = null;
		}
		return s;

	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI touchSession(String id) {
		SessionI rt = this.getSession(id);
		if (rt == null) {
			return null;
		}
		rt.touch();
		return rt;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI createSession(long timeout) {
		//
		return this.createSession(UUID.randomUUID().toString(), timeout);
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI createSession(String id, long timeout) {
		SessionI rt = new SessionImpl(this.getName(), id, timeout);
		this.sessions.put(rt.getId(), rt);
		return rt;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void addCreatedHandler(HandlerI<SessionI> hdl) {
		//
		throw new Todo();
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void addTimeoutHandler(HandlerI<SessionI> hdl) {
		//
		this.timeoutHandlers.addHandler(hdl);
	}

	public void checkTimeout() {
		List<SessionI> sL = new ArrayList<SessionI>();// copy

		for (SessionI s : this.sessions.values()) {
			if (s.isTimeout()) {
				sL.add(s);
			}
		}
		//
		for (SessionI s : sL) {
			this.doTimeout(s);
		}

	}

	private void doTimeout(SessionI s) {
		this.sessions.remove(s.getId());
		this.timeoutHandlers.handle(s);
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void addTouchedHandler(HandlerI<SessionI> hdl) {
		//
		throw new Todo();
	}

}
