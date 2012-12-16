/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.api;

import java.util.List;

import com.fs.datagrid.api.DataGridI;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.expector.api.data.EventGd;
import com.fs.expector.api.data.MemberRefGd;
import com.fs.expector.api.data.SessionGd;
import com.fs.expector.api.gobject.WebSocketGoI;

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
