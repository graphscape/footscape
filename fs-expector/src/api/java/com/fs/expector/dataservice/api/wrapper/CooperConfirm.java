/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 29, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.conf.FieldConfig;
import com.fs.dataservice.api.core.conf.FieldValidatorI;
import com.fs.dataservice.api.core.conf.NodeConfig;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.AuthedNode;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class CooperConfirm extends AuthedNode {

	public static final String COOPER_REQUEST_ID = "cooperRequestId";

	// create a new activity or use the same activity with exp2 in the
	// exp2ActivityUid.
	public static final String CREATE_NEW_ACTIVITY = "creatNewActivity";

	public static final String EXP2_ACTIVITY_ID = "exp2ActivityId";//

	/**
	 * @param ntype
	 */
	public CooperConfirm() {
		super(NodeTypes.COOPER_CONFIRM);
	}

	public static void config(NodeConfigurations cfs) {
		NodeConfig nc = cfs.addConfig(NodeTypes.COOPER_CONFIRM,
				CooperConfirm.class);
		nc.field(COOPER_REQUEST_ID);
		nc.field(EXP2_ACTIVITY_ID, false);

		AuthedNode.config(nc.field(CREATE_NEW_ACTIVITY));
		FieldConfig fc = nc.getField(EXP2_ACTIVITY_ID, true);
		fc.addValiditor(new FieldValidatorI() {

			@Override
			public void validate(FieldConfig fc, NodeWrapper nw, ErrorInfos eis) {
				//
				CooperConfirm cc = (CooperConfirm) nw;
				boolean createNew = cc.getCreateNewActivity();
				Object value = nw.getProperty(fc.getName());//
				if (createNew && value != null || !createNew && value == null) {
					eis.add(new ErrorInfo(
							"conflicted field value:createNew:"
									+ createNew
									+ ",exp2ActivityId:"
									+ value
									+ ",either create new activity or use the activity that service exp2"));
				}

			}
		});
	}

	public void setExp2ActivityUid(String expId) {
		this.setProperty(EXP2_ACTIVITY_ID, expId);

	}

	public String getExp2ActivityId() {
		return (String) this.getProperty(EXP2_ACTIVITY_ID);
	}

	public void setCooperRequestId(String expId) {
		this.setProperty(COOPER_REQUEST_ID, expId);

	}

	public void setCreateNewActivity(boolean newAct) {
		this.setProperty(CREATE_NEW_ACTIVITY, newAct);
	}

	public String getCooperRequestId() {
		return (String) this.getProperty(COOPER_REQUEST_ID);
	}

	/**
	 * Nov 2, 2012
	 */
	public boolean getCreateNewActivity() {
		//
		return this.getProperty(Boolean.class,
				CooperConfirm.CREATE_NEW_ACTIVITY, Boolean.FALSE);

	}

}
