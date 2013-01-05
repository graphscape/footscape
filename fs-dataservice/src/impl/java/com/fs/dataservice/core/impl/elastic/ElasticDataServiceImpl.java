/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.operations.DeleteAllOperationI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.dataservice.api.core.operations.NodeCreateOperationI;
import com.fs.dataservice.api.core.operations.NodeDeleteOperationI;
import com.fs.dataservice.api.core.operations.NodeGetOperationI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.operations.RefreshOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.support.DataServiceSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.ElasticTimeFormat;
import com.fs.dataservice.core.impl.elastic.operations.DeleteAllOperationE;
import com.fs.dataservice.core.impl.elastic.operations.DumpOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeCreateOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeDeleteOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeGetOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeQueryOperationE;
import com.fs.dataservice.core.impl.elastic.operations.RefreshOperationE;

/**
 * @author wu
 * 
 */
public class ElasticDataServiceImpl extends DataServiceSupport implements ElasticClientI {
	private static final Logger LOG = LoggerFactory.getLogger(ElasticDataServiceImpl.class);

	protected Client client;

	protected String dataVersion = "0.1";

	protected MetaInfo metaInfo;

	public ElasticDataServiceImpl() {
		// iso8601

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
	public void configure(Configuration cfg) {
		//
		super.configure(cfg);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

		TransportClient tc = new TransportClient();
		String host = this.getHost();
		int port = this.getPort();
		tc.addTransportAddress(new InetSocketTransportAddress(host, port));

		MetaInfo mi = MetaInfo.load(tc);
		LOG.info("meta info readed from data base:" + mi);
		if ("0.1".equals(mi.getVersion())) {
			LOG.info("data version is ok:" + mi.getVersion());
		} else {
			throw new FsException("data version not supported:" + mi.getVersion()
					+ ",please make sure your elastic server configureation is correct.");
		}
		this.metaInfo = mi;
		this.client = tc;
		//register building operations.
		this.registerOperation("core.nodeget", NodeGetOperationI.class, NodeGetOperationE.class);
		this.registerOperation("core.nodecreate", NodeCreateOperationI.class, NodeCreateOperationE.class);
		this.registerOperation("core.nodequery", NodeQueryOperationI.class, NodeQueryOperationE.class);
		this.registerOperation("core.deleteall", DeleteAllOperationI.class, DeleteAllOperationE.class);
		this.registerOperation("core.dump", DumpOperationI.class, DumpOperationE.class);
		this.registerOperation("core.refresh", RefreshOperationI.class, RefreshOperationE.class);
		this.registerOperation("core.delete", NodeDeleteOperationI.class, NodeDeleteOperationE.class);
		//
		boolean ci = this.config.getPropertyAsBoolean("cleanAtInit", false);
		if (ci) {
			this.deleteAll();
		}
	}

	private String getHost() {
		return this.config.getProperty("host", "locahost");
	}

	private int getPort() {
		return this.config.getPropertyAsInt("port", 9300);//
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
		return this.metaInfo.getNodeIndex();
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public void refresh() {
		this.prepareOperation(RefreshOperationI.class).execute().getResult().assertNoError();
	}

	/*
	 * Nov 18, 2012
	 */
	@Override
	public String getTimestampString() {
		String rt = ElasticTimeFormat.format(new Date());
		return rt;
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
		NodeQueryOperationI<T> qo = this.prepareNodeQuery(wpcls);
		for (int i = 0; i < fields.length; i++) {
			qo.propertyEq(fields[i], values[i]);
		}
		qo.first(from);
		qo.maxSize(maxSize);
		qo.sortTimestamp(true);
		NodeQueryResultI<T> rst = qo.execute().getResult();

		return rst.list();
	}

	/**
	 * Jan 5, 2013
	 */
	public void deleteAll() {
		DeleteAllOperationI da = this.prepareOperation(DeleteAllOperationI.class);
		da.execute().getResult().assertNoError();

	}

}
