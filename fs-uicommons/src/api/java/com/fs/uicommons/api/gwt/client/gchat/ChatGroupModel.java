/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ChatGroupModel extends ModelSupport {

	protected Map<String, ParticipantModel> pMap;

	/**
	 * @param name
	 */
	public ChatGroupModel(String id) {
		super(id, id);
		this.pMap = new HashMap<String, ParticipantModel>();

	}

	public void addMessage(MessageModel mm) {
		this.child(mm);
	}

	public List<MessageModel> getMessageModelList() {
		return this.getChildList(MessageModel.class);
	}

	/**
	 * Dec 23, 2012
	 */
	public void addParticipant(ParticipantModel p) {
		this.pMap.put(p.getId(), p);
	}

	public ParticipantModel getParticipant(String pid, boolean force) {
		ParticipantModel rt = this.pMap.get(pid);
		if (force && rt == null) {
			throw new UiException("no participant:" + pid + " in group:" + this.id);

		}
		return rt;

	}

}
