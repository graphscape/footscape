/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import java.util.Date;

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
import com.fs.dataservice.api.core.operations.RefreshOperationI;
import com.fs.dataservice.api.core.support.DataServiceSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.ElasticTimeFormat;

/**
 * @author wu
 * 
 */
public class ElasticDataServiceImpl extends DataServiceSupport implements
		ElasticClientI {
	private static final Logger LOG = LoggerFactory
			.getLogger(ElasticDataServiceImpl.class);

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
	public <T extends OperationI> T perpareOperationInternal(Class<T> itf,
			Class<? extends T> cls2) {

		T rt = ClassUtil.newInstance(cls2, new Class[] { DataServiceI.class },
				new Object[] { this });

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
			throw new FsException(
					"data version not supported:"
							+ mi.getVersion()
							+ ",please make sure your elastic server configureation is correct.");
		}
		this.metaInfo = mi;
		this.client = tc;

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
		this.prepareOperation(RefreshOperationI.class).execute().getResult()
				.assertNoError();
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
	public <T extends NodeWrapper> T getNewestById(Class<T> wpcls, String id,
			boolean force) {
		//
		return this.getNewest(wpcls, NodeI.PK_ID, id, force);

	}

}
