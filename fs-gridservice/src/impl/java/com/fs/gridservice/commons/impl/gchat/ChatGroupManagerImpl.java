/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.gchat;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.gchat.ChatGroupI;
import com.fs.gridservice.commons.api.gchat.ChatGroupManagerI;
import com.fs.gridservice.commons.api.gchat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.gchat.data.ChatGroupGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.impl.support.FacadeAwareConfigurableSupport;
import com.fs.gridservice.core.api.objects.DgMapI;

/**
 * @author wuzhen
 * 
 */
public class ChatGroupManagerImpl extends FacadeAwareConfigurableSupport
		implements ChatGroupManagerI {

	private static final String GDMAP_NAME = "map-chat-groups";

	protected DgMapI<String, ChatGroupGd> chatRoomDgMap;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.chatRoomDgMap = this.facade.getDataGrid().getMap(GDMAP_NAME);
	}

	@Override
	public ChatGroupI createChatRoom(PropertiesI<Object> pts) {
		ChatGroupGd da = new ChatGroupGd(pts);
		this.chatRoomDgMap.put(da.getId(), da);
		ChatGroupI rt = this.getChatGroup(da.getId());
		return rt;
	}

	@Override
	public ChatGroupI getChatGroup(String id) {
		return this.getChatRoom(id, false);
	}

	public ChatGroupI getChatRoom(String id, boolean force) {
		ChatGroupGd cd = this.chatRoomDgMap.getValue(id);
		if (cd == null && force) {
			throw new FsException("no chat room with id:" + id);
		}
		return new ChatGroupImpl(id, this);

	}
	


}
