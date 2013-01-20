/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.wrapper;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.core.meta.FieldMeta;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.operations.NodeCreateOperationI;

/**
 * @author wu
 * 
 */
public class NodeWrapper extends PropertiesWrapper<Object, PropertiesI<Object>> {

	protected NodeType nodeType;

	protected DataServiceI dataService;

	protected NodeCreateOperationI createOperation;

	public NodeWrapper(NodeType type) {
		this.nodeType = type;
	}

	public <T extends NodeWrapper> T forOp(DataServiceI ds) {
		this.dataService = ds;
		return (T) this;
	}

	public <T extends NodeWrapper> T forCreate(DataServiceI ds) {
		this.forOp(ds);
		this.createOperation = ds.prepareOperation(NodeCreateOperationI.class);
		this.createOperation.nodeType(this.getClass());// this.target will
														// connect to the
		// operation's paramters.
		this.createOperation.wrapper(this);

		return (T) this;

	}

	public void validate(NodeMeta nc, ErrorInfos rt) {
		List<String> kl = this.target.keyList();// actual data
		Set<String> kset = new HashSet<String>(nc.keySet());// expected data
		for (String k : kl) {
			FieldMeta fc = nc.getField(k, false);
			if (fc == null) {// any data must be defined.
				rt.add(new ErrorInfo("no field:" + k
						+ " is configured by for type:" + nc.getNodeType()));
				continue;
			}
		}
		for (String k : kset) {// expected:
			FieldMeta fc = nc.getField(k, false);
			Object value = this.target.getProperty(k);//

			if (value == null && fc.isManditory()) {
				rt.add(new ErrorInfo("field:" + k + " is manditory for type:"
						+ nc.getNodeType()));
			}

			fc.validate(this, rt);
		}

	}

	/**
	 * @param signupRequest
	 * @param pts
	 */
	public NodeWrapper(NodeType ntype, PropertiesI<Object> pts) {
		this(ntype);
		this.attachTo(pts);
	}

	public <T extends PropertiesWrapper<Object, PropertiesI<Object>>> T attachTo(
			PropertiesI<Object> pts, DataServiceI dataService) {

		this.dataService = dataService;
		return this.attachTo(pts);

	}

	@Override
	public <T extends PropertiesWrapper<Object, PropertiesI<Object>>> T attachTo(
			PropertiesI<Object> pts) {
		super.attachTo(pts);//

		return (T) this;
	}

	public String getUniqueId() {
		return (String) this.target.getProperty(NodeI.PK_UNIQUE_ID);
	}

	public String getId() {
		return (String) this.target.getProperty(NodeI.PK_ID);//
	}

	public void setId(String id) {
		this.target.setProperty(NodeI.PK_ID, id);
	}

	public String save() {
		return this.save(false);
	}

	public String save(boolean refreshAfterCreate) {

		ResultI rst = this.createOperation
				.refreshAfterCreate(refreshAfterCreate).execute().getResult()
				.assertNoError();

		return (String) rst.get(true);
	}

	/**
	 * Oct 28, 2012
	 */
	public NodeType getNodeType() {
		//
		return this.nodeType;
	}

	public Object getProperty(String key) {

		return this.target.getProperty(key);
	}

	public Object getProperty(String key, boolean force) {

		Object rt = this.target.getProperty(key);
		if (force && rt == null) {
			throw new FsException("force :" + key);
		}
		return rt;
	}

	public <T> T getProperty(Class<T> cls, String key, T def) {
		Object rt = this.getProperty(key);

		if (rt == null) {
			return def;
		}
		return (T) rt;
	}

	public String getPropertyAsString(String key) {
		return (String) this.getProperty(key);
	}

	// NOTE type convert is needed, see the NodeI's impl class
	public Date getTimestamp() {
		return (Date) this.target.getProperty(NodeI.PK_TIMESTAMP);//
	}

	/**
	 * Nov 2, 2012
	 */

}
