/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.impl.hazelcast;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.core.api.DataGridI;
import com.fs.gridservice.core.api.DgFactoryI;
import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Instance;

/**
 * @author wuzhen
 * 
 */
public class DgFactoryHC extends ConfigurableSupport implements DgFactoryI {

	private static final Logger LOG = LoggerFactory.getLogger(DgFactoryHC.class);

	protected List<String> addressList;

	protected HazelcastClient client;

	protected DataGridHC instance;

	protected CodecI propertiesCodec;

	protected CodecI messageCodec;

	protected boolean cleanGridAtInit;

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

		this.addressList = this.config.getPropertyAsCsv("addressCsv");
		if (this.addressList.isEmpty()) {
			this.addressList.add("127.0.0.1:5701");// add a default to try.
		}
		this.cleanGridAtInit = this.config.getPropertyAsBoolean("cleanGridAtInit", false);//
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.addAddress(this.addressList.toArray(new String[] {}));
		client = HazelcastClient.newHazelcastClient(clientConfig);
		CodecI.FactoryI cf = this.container.find(CodecI.FactoryI.class, true);
		this.propertiesCodec = cf.getCodec(PropertiesI.class, true);
		this.messageCodec = cf.getCodec(MessageI.class, true);

		//
		if (this.cleanGridAtInit) {
			this.destroyAll(client);//
		}
		this.instance = new DataGridHC(client, this);

	}

	public void destroyAll(HazelcastClient client) {
		LOG.warn("clean grid,detroy all the grid object.");
		// clean remains
		Collection<Instance> is = client.getInstances();
		for (Instance ins : is) {
			try {
				ins.destroy();

				LOG.info("instance destroyed,type:" + ins.getInstanceType());
			} catch (Throwable t) {
				LOG.error("cannot destroy,type:" + ins.getInstanceType(), t);
			}
		}

	}

	@Override
	public void close() {
		this.instance.close();
	}

	@Override
	public DataGridI getInstance() {

		return this.instance;
	}

	/*
	 * Dec 24, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

	}
}
