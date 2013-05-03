/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.dataservice.api.core.operations.NodeCountOperationI;
import com.fs.dataservice.api.core.operations.NodeCreateOperationI;
import com.fs.dataservice.api.core.operations.NodeDeleteOperationI;
import com.fs.dataservice.api.core.operations.NodeGetOperationI;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.operations.NodeUpdateOperationI;
import com.fs.dataservice.api.core.operations.RefreshOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.dataservice.api.core.support.DataServiceSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.elastic.operations.DumpOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeCountOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeCreateOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeDeleteOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeGetOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeSearchOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeUpdateOperationE;
import com.fs.dataservice.core.impl.elastic.operations.RefreshOperationE;

/**
 * @author wu
 * 
 */
public class ElasticDataServiceImpl extends DataServiceSupport implements ElasticClientI {
	private static final Logger LOG = LoggerFactory.getLogger(ElasticDataServiceImpl.class);

	protected String dataVersion = "0.1";

	protected Client client;

	protected String index;

	// iso8601
	public ElasticDataServiceImpl(DataSchema ds, Client client, String index) {
		super(ds);
		this.client = client;
		this.index = index;

		this.registerOperation("core.nodeget", NodeGetOperationI.class, NodeGetOperationE.class);
		this.registerOperation("core.nodecreate", NodeCreateOperationI.class, NodeCreateOperationE.class);
		this.registerOperation("core.nodesearch", NodeSearchOperationI.class, NodeSearchOperationE.class);
		this.registerOperation("core.nodecount", NodeCountOperationI.class, NodeCountOperationE.class);
		this.registerOperation("core.nodeupdate", NodeUpdateOperationI.class, NodeUpdateOperationE.class);

		this.registerOperation("core.dump", DumpOperationI.class, DumpOperationE.class);
		this.registerOperation("core.refresh", RefreshOperationI.class, RefreshOperationE.class);
		this.registerOperation("core.delete", NodeDeleteOperationI.class, NodeDeleteOperationE.class);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends OperationI> T perpareOperationInternal(Class<T> itf, Class<? extends T> cls2) {

		T rt = ClassUtil.newInstance(cls2, new Class[] { DataServiceI.class }, new Object[] { this });

		return rt;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public Client getClient() {
		//
		return this.client;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T> T executeInClient(CallbackI<Client, T> cb) {

		return cb.execute(this.client);

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public String getIndex() {
		//
		return this.index;
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public void refresh() {
		this.prepareOperation(RefreshOperationI.class).execute().getResult().assertNoError();
	}

	/*
	 * Nov 26, 2012
	 */
	@Override
	public <T extends NodeWrapper> T getNewestById(Class<T> wpcls, String id, boolean force) {
		//
		return this.getNewest(wpcls, NodeI.PK_ID, id, force);

	}

	/*
	 * Dec 8, 2012
	 */
	@Override
	public <T extends NodeWrapper> List<T> getNewestListById(Class<T> wpcls, List<String> idL, boolean force,
			boolean reserveNull) {
		//
		List<T> rt = new ArrayList<T>();
		for (String id : idL) {

			T ti = this.getNewestById(wpcls, id, force);
			if (ti == null && !reserveNull) {
				continue;
			}
			rt.add(ti);
		}

		return rt;
	}

	@Override
	public <T extends NodeWrapper> List<T> getListNewestFirst(Class<T> wpcls, String field, Object value,
			int from, int maxSize) {
		return this.getListNewestFirst(wpcls, new String[] { field }, new Object[] { value }, from, maxSize);
	}

	/*
	 * Dec 8, 2012
	 */
	@Override
	public <T extends NodeWrapper> List<T> getListNewestFirst(Class<T> wpcls, String[] fields,
			Object[] values, int from, int maxSize) {
		//
		NodeSearchOperationI<T> qo = this.prepareNodeSearch(wpcls);
		for (int i = 0; i < fields.length; i++) {
			qo.propertyEq(fields[i], values[i]);
		}
		qo.first(from);
		qo.maxSize(maxSize);
		qo.sortTimestamp(true);
		NodeSearchResultI<T> rst = qo.execute().getResult();

		return rst.list();
	}

	public void close() {
		this.client.close();
	}


}
