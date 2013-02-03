/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 3, 2013
 */
package com.fs.uicommons.api.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface ChatGroupViewI extends ViewI {

	public void addMessage(String msg);

	public void addParticipant(ParticipantModel om);

}
