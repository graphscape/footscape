/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import java.util.List;

import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.meta.FieldMeta;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.operations.NodeUpdateOperationI;
import com.fs.dataservice.api.core.result.BooleanResultI;
import com.fs.dataservice.api.core.support.BooleanResult;
import com.fs.dataservice.api.core.support.NodeOperationSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;

/**
 * @author wu
 * 
 */
public class NodeUpdateOperationE<W extends NodeWrapper> extends
		NodeOperationSupport<NodeUpdateOperationI<W>, W, BooleanResultI> implements NodeUpdateOperationI<W> {

	private ElasticClientI elastic;

	public static final String PK_PROPERTIES = "properties";// node

	// propertiesKey

	/**
	 * @param ds
	 */
	public NodeUpdateOperationE(DataServiceI ds) {
		super(new BooleanResult(ds));
		this.parameter(PK_PROPERTIES, new MapProperties<Object>());
		this.elastic = (ElasticClientI) ds;
	}

	public String getUniqueId(boolean force) {
		return (String) this.getParameter(NodeI.PK_UNIQUE_ID, force);
	}

	protected PropertiesI<Object> properties() {
		return (PropertiesI<Object>) this.parameters.getProperty(PK_PROPERTIES, true);
	}

	@Override
	public NodeUpdateOperationI<W> uniqueId(String uid) {
		this.parameter(NodeI.PK_UNIQUE_ID, uid);
		return this.cast();
	}

	public void validate(PropertiesI<Object> pts, NodeMeta nc, ErrorInfos rt) {
		List<String> kl = pts.keyList();// actual data
		for (String k : kl) {
			FieldMeta fc = nc.getField(k, false);
			if (fc == null) {// any data must be defined.
				rt.add(new ErrorInfo("no field:" + k + " is configured by for type:" + nc.getNodeType()));
				continue;
			}
		}

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(BooleanResultI rst) throws Exception {
		//
		Client client = elastic.getClient();
		PropertiesI<Object> pts = this.getNodeProperties();
		this.validate(pts, this.nodeConfig, rst.getErrorInfo());
		if (rst.getErrorInfo().hasError()) {
			return;
		}
		// type
		String type = this.getNodeType(true).toString();//
		pts.setProperty(NodeI.PK_TYPE, type);// type field

		// uid
		String uid = this.getUniqueId(true);

		String idx = this.elastic.getIndex();
		// idx = "index1";
		UpdateRequestBuilder urb = client.prepareUpdate(idx, type, uid);
		String script = "";
		for (String key : pts.keyList()) {
			Object value = pts.getProperty(key);
			script += "ctx._source." + key + " = " + key;
			script += ";";
			urb.addScriptParam(key, value);
		}
		urb.setScript(script);

		UpdateResponse response = urb.execute().actionGet();
		// boolean exists = response.getGetResult().exists();
		boolean exists = response.getId() != null;
		rst.set(exists);

		if (this.isRefreshAfterCreate()) {
			this.dataService.refresh();
		}
	}

	public boolean isRefreshAfterCreate() {

		return this.parameters.getPropertyAsBoolean(NodeUpdateOperationI.PK_REFRESH_AFTER_UPDATE, false);

	}

	public PropertiesI<Object> getNodeProperties() {
		//
		return (PropertiesI<Object>) this.getParameter(PK_PROPERTIES);
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public NodeUpdateOperationI<W> refreshAfterUpdate(boolean refreshAfterCreate) {
		this.parameter(NodeUpdateOperationI.PK_REFRESH_AFTER_UPDATE, refreshAfterCreate);
		return this;
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public NodeUpdateOperationI<W> property(String key, Object value) {
		//
		this.properties().setProperty(key, value);
		return this;
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public String getUniqueId() {
		//
		return (String) this.getParameter(NodeI.PK_UNIQUE_ID);
	}

}
