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

	public static final String A_REQUEST = "request";

	public static final String A_CONFIRM = "confirm";

	public static final String A_REFRESH_INCOMING_CR = "refreshIncomingCr";

	public void cooper(String expId1, String expId2);// for request action,set
														// this two
	// exps firstly.

	public String getExpId1();

	public String getExpId2();

	public List<IncomingCrModel> getIncomingCooperRequestModelList();

	public void incomingCr(IncomingCrModel crm);

	public void removeIncomingCr(String crId);

}
