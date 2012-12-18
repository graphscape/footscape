/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api;

import java.util.List;

import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.MemberRefGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.core.api.DataGridI;
import com.fs.gridservice.core.api.objects.DgMapI;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wu
 * 
 */
public interface GridFacadeI {

	public List<MemberRefGd> getMemberRefList();

	public GridMemberI getLocalMember();

	public MemberRefGd getMemberRef(String id);

	public MemberRefGd getMemberRef(String id, boolean force);

	public DgQueueI<EventGd> getGlogalEventQueue();

	public DgQueueI<EventGd> getLocalMemberEventQueue();

	public DgQueueI<EventGd> getMemberEventQueue(String mid);

	public DgMapI<String, SessionGd> getSessionMap();

	public <T extends GridedObjectI> GridedObjectManagerI<T> getGridedObjectManager(String name);

	public GridedObjectManagerI<WebSocketGoI> getWebSocketGridedObjectManager();

	public DgMapI<String, String> getSessionWebSocketIdMap();

	public DataGridI getDataGrid();

}
