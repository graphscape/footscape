/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wu
 * @deprecated TODO remove this class
 */
public class ExpSearchModel implements ExpSearchModelI {

	private UserExpModel expId;

	private String phrase;

	private int firstResult;

	private int size = 15;

	private List<ExpItemModel> itemList;

	private Map<String, ExpItemModel> itemMap;

	/**
	 * @param name
	 */
	public ExpSearchModel() {
		this.itemList = new ArrayList<ExpItemModel>();
		this.itemMap = new HashMap<String, ExpItemModel>();
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public UserExpModel getExpId(boolean force) {
		//
		UserExpModel rt = this.getExpId();
		if (force && rt == null) {
			throw new UiException("no expId in :" + this);
		}
		return rt;

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public ExpItemModel addExpItem(ExpItemModel ei) {
		//
		ExpItemModel old = this.getExpItemById(ei.getExpId());

		if (old == null) {
			this.itemList.add(ei);
			this.itemMap.put(ei.getExpId(), ei);
		} else {
			// ignore?
		}
		return old;

	}

	/**
	 * @param expId2
	 * @return
	 */
	private ExpItemModel getExpItemById(String expId2) {
		return this.itemMap.get(expId2);
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public int getFirstResult() {
		//
		return this.firstResult;
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public int getMaxResult() {
		//
		return this.firstResult + this.size;// TODO
	}

	public void setFirstResult(int pg) {
		this.firstResult = pg;
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public boolean nextPage() {
		int fr = this.getFirstResult();
		this.setFirstResult(this.getMaxResult() + fr);
		return true;
	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	public boolean previousPage() {
		int fr = this.getFirstResult();
		fr = fr - this.getMaxResult();
		if (fr < 0) {
			fr = 0;
		}
		this.setFirstResult(fr);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#getExpItemList()
	 */
	@Override
	public List<ExpItemModel> getExpItemList() {
		return this.itemList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#setExpId(java.lang
	 * .String)
	 */
	@Override
	public void setExpId(UserExpModel expId) {
		this.expId = expId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#getExpId()
	 */
	@Override
	public UserExpModel getExpId() {
		return this.expId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#getPhrase(boolean)
	 */
	@Override
	public String getPhrase(boolean force) {
		if (force && this.phrase == null) {
			throw new UiException("no phrase provided by user");
		}

		return this.phrase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#setPhrase(java.lang
	 * .String)
	 */
	@Override
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI#reset()
	 */
	@Override
	public void reset() {
		this.itemList.clear();
		this.itemMap.clear();
	}

}
