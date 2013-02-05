/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.coper;

import java.util.List;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface CooperControlI extends ControlI {

	public String getExpId1();

	public String getExpId2();

	public void cooper(String expId1, String expId2);

	public void refreshIncomingCr(String crId);

	public void removeIncomingCr(String crId);

	public List<IncomingCrModel> getIncomingCooperRequestModelList();

	public void incomingCr(IncomingCrModel crm);

}
