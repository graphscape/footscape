/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.chat;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.chat.ChatRoomI;
import com.fs.gridservice.commons.api.chat.ChatRoomManagerI;
import com.fs.gridservice.commons.api.chat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.chat.data.ChatRoomGd;
import com.fs.gridservice.commons.api.wrapper.WsMsgSendEW;
import com.fs.gridservice.commons.impl.support.FacadeAwareConfigurableSupport;
import com.fs.gridservice.core.api.objects.DgMapI;

/**
 * @author wuzhen
 * 
 */
public class ChatRoomManagerImpl extends FacadeAwareConfigurableSupport
		implements ChatRoomManagerI {

	private static final String GDMAP_NAME = "map-chat-rooms";

	protected DgMapI<String, ChatRoomGd> chatRoomDgMap;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.chatRoomDgMap = this.facade.getDataGrid().getMap(GDMAP_NAME);
	}

	@Override
	public ChatRoomI createChatRoom(PropertiesI<Object> pts) {
		ChatRoomGd da = new ChatRoomGd(pts);
		this.chatRoomDgMap.put(da.getId(), da);
		ChatRoomI rt = this.getChatRoom(da.getId());
		return rt;
	}

	@Override
	public ChatRoomI getChatRoom(String id) {
		return this.getChatRoom(id, false);
	}

	public ChatRoomI getChatRoom(String id, boolean force) {
		ChatRoomGd cd = this.chatRoomDgMap.getValue(id);
		if (cd == null && force) {
			throw new FsException("no chat room with id:" + id);
		}
		return new ChatRoomImpl(id, this);

	}
	


}
