/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.coper;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 *         <p>
 *         This model is shared singleton.Which means it should be populated and
 *         before calling the action.
 *         <p>
 *         TODO provide a none model control.
 */
public interface CooperModelI extends ModelI {


	public void cooper(String expId1, String expId2);// for request action,set
														// this two
	// exps firstly.

	public String getExpId1();

	public String getExpId2();

	public List<ExpMessage> getIncomingCooperRequestModelList();

	public void incomingCr(ExpMessage crm);

	public void removeIncomingCr(String crId);

}
