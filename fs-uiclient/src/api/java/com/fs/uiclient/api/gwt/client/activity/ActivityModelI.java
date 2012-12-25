/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.activity;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface ActivityModelI extends ModelI {

	public static final Location L_ACTIVITY_ID = Location.valueOf("activityId");

	public static final Location L_SELECTED = Location.valueOf("selected");

	public static final String A_OPEN_CHAT_ROOM = "openChatRoom";

	public static final String A_REFRESH = "refresh";// refresh participant
														// list,or only the new
														// participant should be
														// refreshed.

	public void setActivityId(String actId);

	public String getActivityId();

	public PartnerModel addParticipant(String expId, String accId);
	
	public PartnerModel getParticipantByExpId(String expId);

	public List<PartnerModel> getParticipantList();

	public boolean isSelected();

	public void select();// focus

}
