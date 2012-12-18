/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.GridMemberI;
import com.fs.gridservice.commons.api.GridedObjectI;
import com.fs.gridservice.commons.api.GridedObjectManagerI;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.MemberRefGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.core.api.DataGridI;
import com.fs.gridservice.core.api.DgFactoryI;
import com.fs.gridservice.core.api.objects.DgMapI;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wu
 * 
 */
public class GridFacadeImpl extends ConfigurableSupport implements GridFacadeI {

	public static final String N_WEBSOCKET_GOMANAGER = "webSocketGoManager";

	public static final String N_GLOBAL_EVENT_QUEUE = "event-queue-global";

	public static final String N_MEMBER_EVENT_QUEUE = "event-queue-member-";

	public static final String N_MAP_MEMBERS = "member-ref-map";

	public static final String N_MAP_SESSIONS = "map-id-session";

	public static final String N_MAP_SESSIONID_WEBSOCKETID = "map-sessionid-websocketid";

	protected DgFactoryI factory;

	protected DataGridI dg;

	protected DgMapI<String, MemberRefGd> memberRefDgMap;
	
	//protected Map<String,GridedObjectManagerI> goManagerMap;

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.factory = container.find(DgFactoryI.class, true);
		this.dg = this.factory.getInstance();
		this.memberRefDgMap = this.dg.getMap(N_MAP_MEMBERS, MemberRefGd.class);
		//this.goManagerMap = new HashMap<String,GridedObjectManagerI>();
	}

	public DgQueueI<EventGd> getGlogalEventQueue() {
		return this.dg.getQueue(N_GLOBAL_EVENT_QUEUE, EventGd.class);
	}

	public DgQueueI<EventGd> getLocalMemberEventQueue() {
		GridMemberI gm = this.container.find(GridMemberI.class, true);
		String mid = gm.getId();
		return this.getMemberEventQueue(mid);
	}

	public DgQueueI<EventGd> getMemberEventQueue(String mid) {

		return this.dg.getQueue(N_MEMBER_EVENT_QUEUE, EventGd.class);

	}

	@Override
	public <T extends GridedObjectI> GridedObjectManagerI<T> getGridedObjectManager(String name) {
		return container.find(GridedObjectManagerI.class, name, true);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public List<MemberRefGd> getMemberRefList() {
		//
		return this.memberRefDgMap.valueList();
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public MemberRefGd getMemberRef(String id) {
		//

		return this.memberRefDgMap.getValue(id);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public MemberRefGd getMemberRef(String id, boolean force) {
		//

		return this.memberRefDgMap.getValue(id, force);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public DgMapI<String, SessionGd> getSessionMap() {
		//
		return this.dg.getMap(N_MAP_SESSIONS);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public DgMapI<String, String> getSessionWebSocketIdMap() {
		//
		return this.dg.getMap(N_MAP_SESSIONID_WEBSOCKETID);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public DataGridI getDataGrid() {
		//
		return this.dg;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public GridMemberI getLocalMember() {
		//
		return this.container.find(GridMemberI.class, true);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public GridedObjectManagerI<WebSocketGoI> getWebSocketGridedObjectManager() {
		return getGridedObjectManager(N_WEBSOCKET_GOMANAGER);
	}

}
