/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import java.util.List;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ExpSearchModel extends ModelSupport implements ExpSearchModelI {

	private String expId;

	/**
	 * @param name
	 */
	public ExpSearchModel(String name) {
		super(name);
		ControlUtil.addAction(this, ExpSearchModelI.A_SEARCH);//
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public String getKeyword() {
		//
		return null;
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public String getExpId(boolean force) {
		//
		String rt = this.getExpId();
		if (force && rt == null) {
			throw new UiException("no expId in :" + this);
		}
		return rt;

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public ExpItemModel addExpItem(String id) {
		//
		ExpItemModel rt = new ExpItemModel(id);

		rt.parent(this);
		return rt;
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public int getPageNumber() {
		//
		return this.getValue(Integer.class, L_PAGENUMBER, 0);
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public int getPageSize() {
		//
		return 15;// TODO
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public void pageNumber(int pg) {
		this.setValue(L_PAGENUMBER, pg);
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public boolean nextPage() {
		int pg = this.getPageNumber();
		this.pageNumber(pg + 1);
		return true;
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public boolean previousPage() {
		int pg = this.getPageNumber();
		if (pg == 0) {
			return false;
		}
		this.pageNumber(pg - 1);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#getExpItemList()
	 */
	@Override
	public List<ExpItemModel> getExpItemList() {
		return this.getChildList(ExpItemModel.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#setExpId(java.lang
	 * .String)
	 */
	@Override
	public void setExpId(String expId) {
		this.expId = expId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#getExpId()
	 */
	@Override
	public String getExpId() {
		return this.expId;
	}

}
