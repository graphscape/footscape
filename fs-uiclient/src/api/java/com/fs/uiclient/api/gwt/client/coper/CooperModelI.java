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

	public static final Location L_EXPID1 = Location.valueOf("expId1");

	public static final Location L_EXPID2 = Location.valueOf("expId2");

	// before confirm action,please set this value.
	public static final Location L_COOPERREQUEST_ID = Location.valueOf("cooperRequestId");// for
																							// confirm

	public void coperExpId1(String expId);// for request action,set this two
											// exps firstly.

	public void coperExpId2(String expId);

	public void cooperRequestId(String crId);// for confirm action, to set this
												// value first.
	// incoming cooper request only needed,for outgoing cr, it is a "message" to
	// another people,no worth to do see it here.may be in "history" message.

	public List<IncomingCrModel> getIncomingCooperRequestModelList();

}
