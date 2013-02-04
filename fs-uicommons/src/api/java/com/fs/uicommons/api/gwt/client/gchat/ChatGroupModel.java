/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ChatGroupModel extends ModelSupport {

	protected Map<String,ParticipantModel> pMap ;
	
	/**
	 * @param name
	 */
	public ChatGroupModel(String name) {
		super(name);
		this.pMap = new HashMap<String,ParticipantModel>();
		
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
	
	public ParticipantModel getParticipant(String pid, boolean force){
		this.getChild(cls, force)
	}

}
