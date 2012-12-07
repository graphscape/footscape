/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.dataservice.expapp.impl.elastic;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.expapp.operations.CooperConfirmOperationI;
import com.fs.dataservice.api.expapp.result.CooperConfirmResultI;
import com.fs.dataservice.api.expapp.wrapper.Activity;
import com.fs.dataservice.api.expapp.wrapper.CooperConfirm;
import com.fs.dataservice.api.expapp.wrapper.CooperRequest;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper.Login;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;
import com.fs.dataservice.api.expapp.wrapper2.UserActivity;
import com.fs.dataservice.expapp.impl.elastic.support.AuthedOperationSupport;
import com.fs.dataservice.expapp.impl.result.CooperConfirmResultImpl;

/**
 * @author wu
 *         <p>
 *         Confirm the request,create a new activity or use the existing one.
 *         <p>
 *         Create ExpActivity relations for both side.
 */
public class CooperConfirmOperationE extends
		AuthedOperationSupport<CooperConfirmOperationI, CooperConfirmResultI>
		implements CooperConfirmOperationI {

	/**
	 * @param ds
	 */
	public CooperConfirmOperationE(DataServiceI ds) {
		super(new CooperConfirmResultImpl(ds));
	}

	@Override
	protected void executeInternal(Login login, CooperConfirmResultI rst)
			throws Exception {

		String id = (String) this.getParameter(PK_COOPER_REQUEST_ID, true);

		CooperRequest cr = this.dataService.getNewestById(CooperRequest.class,
				id, true);

		boolean createNew = this.getParameter(Boolean.class,
				PK_CREATE_NEW_ACTIVITY, Boolean.FALSE);

		String actId = (String) this.getParameter(PK_EXP2_ACTIVITY_UID,
				!createNew);
		CooperConfirm cc = new CooperConfirm().forCreate(this.dataService);
		cc.setAccountId(login.getAccountId());//
		cc.setCooperRequestId(id);
		cc.setCreateNewActivity(createNew);//
		cc.setExp2ActivityUid(actId);
		cc.save();

		// restul
		((CooperConfirmResultImpl) rst).setProperty(
				CooperConfirmResultImpl.COOPER_CONFIRM_ID, cc.getId());

		Expectation exp1 = this.dataService.getNewestById(Expectation.class,
				cr.getExpId1(), true);
		Expectation exp2 = this.dataService.getNewestById(Expectation.class,
				cr.getExpId2(), true);

		if (actId == null) {// create a new activity.
			Activity act = new Activity().forCreate(this.dataService);

			act.setCooperConfirmUid(cc.getId());

			act.save(true);

			actId = act.getId();//

			// TODO move this creating to other more reliable machanism

			// relate activity with exp2,exp2's user create this activity.
			this.tryLinkExp2Activity(exp2, actId);

		}
		// relate activity with exp1,exp1 request for cooper and activity is
		// allowed for his enter.
		this.tryLinkExp2Activity(exp1, actId);

		// find the related activity to see if any?
		// UserActivity

	}

	protected void tryLinkExp2Activity(Expectation exp, String actId) {
		ExpActivity oldEa = this.dataService.getNewest(ExpActivity.class,
				new String[] { ExpActivity.PK_EXP_ID,
						ExpActivity.PK_ACTIVITY_ID },
				new Object[] { exp.getId(), actId }, false);
		if (oldEa == null) {
			ExpActivity ea = new ExpActivity().forCreate(this.dataService);
			ea.setAccountId(exp.getAccountId());
			ea.setExpId(exp.getId());//
			ea.setActivityId(actId);
			ea.save(true);// Note the last one is refresh
			// query the user's activity list,update the link
		}
		UserActivity oldUa = this.dataService.getNewest(UserActivity.class,
				new String[] { UserActivity.PK_ACCOUNT_ID,
						UserActivity.PK_ACTIVITY_ID },
				new Object[] { exp.getAccountId(), actId }, false);
		if (oldUa == null) {
			// TODO duplicated entry:
			UserActivity ua = new UserActivity().forCreate(this.dataService);
			ua.setAccountId(exp.getAccountId());// the exp's user
			ua.setActivityId(actId);
			ua.save(true);
		}
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public CooperConfirmOperationI cooperRequestId(String cooperUid) {
		//
		this.parameters.setProperty(PK_COOPER_REQUEST_ID, cooperUid);
		return this;
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public CooperConfirmOperationI exp2ActivityId(String activityId) {
		//
		this.parameters.setProperty(PK_EXP2_ACTIVITY_UID, activityId);
		return this;
	}

	/*
	 * Nov 2, 2012
	 */
	@Override
	public CooperConfirmOperationI createNewActivity(boolean cna) {
		//
		this.parameters.setProperty(PK_CREATE_NEW_ACTIVITY, cna);
		return this;
	}

}
