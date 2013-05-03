/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.operations.NodeCreateOperationI;
import com.fs.dataservice.api.core.result.NodeCreateResultI;
import com.fs.dataservice.api.core.support.NodeOperationSupport;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.UUIDUtil;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;

/**
 * @author wu
 * 
 */
public class NodeCreateOperationE<W extends NodeWrapper> extends
		NodeOperationSupport<NodeCreateOperationI<W>, W, NodeCreateResultI> implements
		NodeCreateOperationI<W> {

	private ElasticClientI elastic;

	private boolean allowNullValue = false;

	public static final String PK_PROPERTIES = "properties";// node
	// propertiesKey

	private NodeWrapper wrapper;

	/**
	 * @param ds
	 */
	public NodeCreateOperationE(DataServiceI ds) {
		super(new ResultImpl(ds));
		this.parameter(PK_PROPERTIES, new MapProperties<Object>());
		this.elastic = (ElasticClientI) ds;
	}

	public String getUniqueId(boolean force) {
		return (String) this.getParameter(NodeI.PK_UNIQUE_ID, force);
	}

	public String getId(boolean force) {
		return (String) this.properties().getProperty(NodeI.PK_ID, force);
	}

	protected <T> T getProperty(String key) {
		return (T) this.properties().getProperty(key);
	}

	protected PropertiesI<Object> properties() {
		return (PropertiesI<Object>) this.parameters.getProperty(PK_PROPERTIES,
				true);
	}

	public void setId(String id) {
		this.properties().setProperty(NodeI.PK_ID, id);
	}
	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeCreateOperationI<W> uniqueId(String uid) {
		this.parameter(NodeI.PK_UNIQUE_ID, uid);
		return this.cast();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(NodeCreateResultI rst) throws Exception {
		//
		Client client = elastic.getClient();
		PropertiesI<Object> pts = this.getNodeProperties();
		NodeWrapper nw = null;
		if (this.wrapper == null) {
			nw = this.nodeConfig.newWraper();
			nw.attachTo(pts);//
		} else {
			nw = this.wrapper;
		}

		// validate against NodeMeta
		nw.validate(this.nodeConfig, rst.getErrorInfo());//
		if (rst.getErrorInfo().hasError()) {
			return;
		}
		// type
		String type = this.getNodeType(true).toString();//
		pts.setProperty(NodeI.PK_TYPE, type);// type field

		// uid
		String uid = this.getUniqueId(false);

		if (uid == null) {
			uid = UUIDUtil.randomStringUUID();
		}

		pts.setProperty(NodeI.PK_UNIQUE_ID, uid);// uid field
		// id
		String id = this.getId(false);
		if (id == null) {
			id = UUIDUtil.randomStringUUID();
			pts.setProperty(NodeI.PK_ID, id);//
		}
		// timestamp
		pts.setProperty(NodeI.PK_TIMESTAMP, new Date());//
		// build properties
		XContentBuilder jb = JsonXContent.contentBuilder();
		jb.startObject();

		for (String key : pts.keyList()) {
			Object value = pts.getProperty(key);

			jb.field(key, value);//
		}

		jb.endObject();

		if (rst.getErrorInfo().hasError()) {
			return;
		}

		String idx = this.elastic.getIndex();
		// idx = "index1";
		IndexResponse response = client.prepareIndex(idx, type, uid).setSource(jb).execute().actionGet();
		String rid = response.getId();

		rst.set(rid);//
		if (this.isRefreshAfterCreate()) {
			this.dataService.refresh();
		}
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeCreateOperationI execute(NodeType type, PropertiesI<Object> pts) {
		//
		return (NodeCreateOperationI) this.nodeType(type).properties(pts).execute();

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeCreateOperationI execute() {
		//
		return (NodeCreateOperationI) super.execute();
	}

	public static class ResultImpl extends ResultSupport<NodeCreateResultI, String> implements
			NodeCreateResultI {

		public ResultImpl(DataServiceI ds) {
			super(ds);
		}

		/*
		 * Oct 29, 2012
		 */
		@Override
		public String getUid(boolean force) {
			//
			return (String) this.get(force);
		}

	}

	public boolean isRefreshAfterCreate() {

		return this.parameters.getPropertyAsBoolean(NodeCreateOperationI.PK_REFRESH_AFTER_CREATE, false);

	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public NodeCreateOperationI refreshAfterCreate(boolean refreshAfterCreate) {
		this.parameter(NodeCreateOperationI.PK_REFRESH_AFTER_CREATE, refreshAfterCreate);
		return this;
	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	public <T extends NodeWrapper> NodeCreateOperationI wrapper(T nw) {
		//
		if (this.wrapper != null) {
			throw new FsException("wrapper already set");
		}
		this.wrapper = nw;
		this.wrapper.attachTo(this.getNodeProperties());
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeCreateOperationI<W> properties(PropertiesI<Object> pts) {
		this.getNodeProperties().setProperties(pts);
		return this.cast();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeCreateOperationI<W> property(String key, Object value) {
		this.getNodeProperties().setProperty(key, value);
		return this.cast();
	}

	@Override
	public PropertiesI<Object> getNodeProperties() {
		//
		return (PropertiesI<Object>) this.getParameter(PK_PROPERTIES);
	}

}
