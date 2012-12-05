/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class NodeQueryResult<W extends NodeWrapper> extends
		ResultSupport<NodeQueryResultI<W>, List<W>> implements
		NodeQueryResultI<W> {

	public NodeQueryResult(DataServiceI ds) {
		super(ds);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public List<W> list() {
		//
		return (List<W>) this.get(true);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public int size() {
		//
		return this.list().size();
	}

	@Override
	public W first(boolean force) {
		//
		if (this.size() == 0) {
			if (force) {
				throw new FsException("result is empty ");
			} else {
				return null;
			}
		} else {
			return this.list().get(0);// get first,can be used for sort by
										// timestamp
										// for instance.
		}
	}

	@Override
	public W single(boolean force) {
		//
		if (this.size() == 0) {
			if (force) {
				throw new FsException("result is empty ");
			} else {
				return null;
			}
		} else if (this.size() == 1) {
			return this.list().get(0);
		} else {
			throw new FsException("too many:" + this.list());
		}
	}

	/*
	 * Nov 29, 2012
	 */
	@Override
	public List<PropertiesI<Object>> propertiesList() {
		List<W> l = this.list();

		List<PropertiesI<Object>> rt = new ArrayList<PropertiesI<Object>>(
				l.size());
		for (W w : l) {
			rt.add(w.getTarget());
		}
		return rt;
	}

}
