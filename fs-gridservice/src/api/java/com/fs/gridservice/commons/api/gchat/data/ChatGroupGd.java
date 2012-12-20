/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.gchat.data;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.EntityGd;

/**
 * @author wuzhen
 * 
 */
public class ChatGroupGd extends EntityGd {

	public static final String PK_PIDLIST = "_participantIdList";

	public ChatGroupGd() {
		this(null, null);
	}

	public ChatGroupGd(PropertiesI<Object> pts) {
		this(null, pts);
	}

	public ChatGroupGd(String id, PropertiesI<Object> pts) {
		super(id, pts);
		this.setProperty(PK_PIDLIST, new ArrayList<String>());
	}

	public void addParticipantIdList(List<String> accIdL) {
		List<String> l = this.getParticipantIdList();
		l.addAll(accIdL);
		this.setProperty(PK_PIDLIST, l);

	}

	public void addParticipantId(String pId) {
		List<String> l = this.getParticipantIdList();

		l.add(pId);
		this.setProperty(PK_PIDLIST, l);
	}
	public void removeParticipantId(String pId) {
		List<String> l = this.getParticipantIdList();
		l.remove(pId);
		this.setProperty(PK_PIDLIST, l);
	}

	/**
	 * Dec 19, 2012
	 */
	public List<String> getParticipantIdList() {
		//
		List<String> rt = (List<String>) this.getProperty(PK_PIDLIST);

		return rt;
	}
}
