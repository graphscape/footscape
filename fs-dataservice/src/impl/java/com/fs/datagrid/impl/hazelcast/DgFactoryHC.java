/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.datagrid.api.DataGridI;
import com.fs.datagrid.api.DgFactoryI;
import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;

/**
 * @author wuzhen
 * 
 */
public class DgFactoryHC extends ConfigurableSupport implements DgFactoryI {

	protected List<String> addressList;

	protected HazelcastClient client;

	protected DataGridHC instance;

	protected CodecI propertiesCodec;

	protected CodecI messageCodec;

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

		this.addressList = this.config.getPropertyAsCsv("addressCsv");
		if (this.addressList.isEmpty()) {
			this.addressList.add("127.0.0.1:5701");// add a default to try.
		}

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
		this.instance = new DataGridHC(client, this);

	}

	@Override
	public void deactive(ActiveContext ac) {
		super.deactive(ac);
		this.instance.dettach();
	}

	@Override
	public DataGridI getInstance() {
		return this.instance;
	}
}
