/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.AnalyzerType;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.meta.FieldMeta;
import com.fs.dataservice.api.core.meta.FieldType;
import com.fs.dataservice.api.core.meta.NodeMeta;

/**
 * @author wu
 * 
 */
public class ElasticDataServiceFactoryImpl extends ConfigurableSupport implements DataServiceFactoryI {

	private static final Logger LOG = LoggerFactory.getLogger(ElasticDataServiceFactoryImpl.class);

	protected String defaultIndex = "nodes";

	protected String dataVersion = "0.1";

	protected DataSchema schema;

	protected ElasticDataServiceImpl dataService;

	protected final ReentrantLock dsLock = new ReentrantLock();

	public ElasticDataServiceFactoryImpl() {
		this.schema = new DataSchema();
	}

	/*
	 * Jan 20, 2013
	 */
	@Override
	public DataSchema getSchema() {
		return this.schema;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

	}

	@Override
	public void deactive(ActiveContext ac) {
		super.deactive(ac);
		if (this.dataService == null) {
			return;
		}
		this.dataService.close();
		this.dataService = null;
	}

	private String getHost() {
		return this.config.getProperty("host", "locahost");
	}

	private int getPort() {
		return this.config.getPropertyAsInt("port", 9300);//
	}

	/*
	 * Jan 20, 2013
	 */
	@Override
	public DataServiceI getDataService() {
		return this.getDataService(this.defaultIndex);
	}

	private DataServiceI getDataService(String index) {
		//
		if (!this.defaultIndex.equals(index)) {
			throw new FsException("todo");
		}
		if (this.dataService != null) {
			return this.dataService;
		}
		this.dsLock.lock();
		try {
			if (this.dataService != null) {
				return this.dataService;
			}

			return this.doCreateDataService(index);

		} finally {
			this.dsLock.unlock();
		}
	}

	private DataServiceI doCreateDataService(String index) {

		this.schema.freeze();// not allow change
		TransportClient client = new TransportClient();
		String host = this.getHost();
		int port = this.getPort();
		client.addTransportAddress(new InetSocketTransportAddress(host, port));

		boolean exist = this.isIndexExist(client, index);
		boolean ci = this.config.getPropertyAsBoolean("cleanAtInit", false);
		if (exist && ci) {
			this.deleteIndex(client, index);
		}

		MetaInfo mi = this.loadDataVersion(client, index, false);
		LOG.info("meta info readed from data base:" + mi);
		if (mi == null) {// no index init
			this.createIndex(client, index);
		} else {
			if ("0.1".equals(mi.getVersion())) {
				LOG.info("data version is ok:" + mi.getVersion());
			} else {
				throw new FsException("data version not supported:" + mi.getVersion()
						+ ",please make sure your elastic server configureation is correct.");
			}
		}

		this.dataService = new ElasticDataServiceImpl(this.schema, client, index);

		return this.dataService;
	}

	/**
	 * <code>
	 wu@thinkpad:~/git/fsi/fs-uiclient$ curl -XGET 'http://localhost:9200/node-index/expectation/_mapping?pretty=true'
{
  "expectation" : {
    "properties" : {
      "accountId" : {
        "type" : "string"
      },
      "body" : {
        "type" : "string",
        "analyzer" : "text",
        "store" : "yes"
      },
      "id_" : {
        "type" : "string"
      },
      "timestamp_" : {
        "type" : "date",
        "format" : "dateOptionalTime"
      },
      "type_" : {
        "type" : "string"
      },
      "uniqueId_" : {
        "type" : "string"
      }
    }
  }
}
	 * </code> Jan 20, 2013
	 */
	private void createIndex(Client client, String index) {
		LOG.info("createIndex:" + index + ",...");
		IndicesAdminClient iac = client.admin().indices();
		{
			CreateIndexRequestBuilder cib = iac.prepareCreate(index);

			{
				XContentBuilder jb;
				try {
					jb = JsonXContent.contentBuilder();
					jb.startObject();
					{

						jb.field("index.number_of_shards", 2);
						// jb.field("number_of_replicas", 1);//
						jb.field("index.analysis.analyzer.default.type", "keyword");//
						jb.field("analysis.analyzer." + AnalyzerType.TEXT + ".type", "standard");
						jb.field("analysis.analyzer." + AnalyzerType.TEXT + ".max_token_length", 255);

					}
					jb.endObject();
				} catch (IOException e) {
					throw new FsException(e);
				}
				cib.setSettings(jb);
			}
			ListenableActionFuture<CreateIndexResponse> af = cib.execute();
			// Throwable t = af.getRootFailure();
			// if (t != null) {
			// throw FsException.toRtE(t);
			// }
			CreateIndexResponse res = af.actionGet();

			boolean ack = res.acknowledged();

			if (!ack) {
				throw new FsException("failed to create index:" + index);
			}
		}

		List<NodeMeta> nmL = this.schema.getNodeMetaList();
		for (NodeMeta nm : nmL) {

			PutMappingRequestBuilder mrb = iac.preparePutMapping(index);
			NodeType ntype = nm.getNodeType();
			String type = ntype.toString();
			mrb.setType(type);
			XContentBuilder jb;
			try {
				jb = JsonXContent.contentBuilder();
				jb.startObject();

				jb.startObject(type);

				jb.startObject("properties");
				for (FieldMeta fm : nm.getFieldList()) {
					String fname = fm.getName();
					jb.startObject(fname);

					FieldType ftype = fm.getType();
					jb.field("type", ftype.toString());

					if (ftype.equals(FieldType.DATE)) {
						jb.field("format", "dateOptionalTime");
					}

					jb.field("store", "yes");// TODO?

					AnalyzerType analyzer = fm.getAnalyzer();
					if (analyzer != null) {
						jb.field("analyzer", analyzer.toString());
					}

					jb.endObject();//
				}
				jb.endObject();//

				jb.endObject();//

				jb.endObject();
			} catch (IOException e) {
				throw new FsException(e);
			}

			mrb.setSource(jb);
			ListenableActionFuture<PutMappingResponse> af = mrb.execute();

			PutMappingResponse res = af.actionGet();
			if (!res.acknowledged()) {
				throw new FsException("failed mapping for type:" + type + " with config:" + nm);
			}
			LOG.info("mapping type:" + type + ",meta:" + nm);

		}// end mapping
			// create version meta info
		{
			try {
				XContentBuilder jb = JsonXContent.contentBuilder();
				jb.startObject();
				jb.field("version", "0.1");//
				jb.endObject();
				IndexResponse response = client.prepareIndex(index, "version", "0").setSource(jb).execute()
						.actionGet();
				String rid = response.getId();
			} catch (IOException e) {
				throw new FsException(e);
			}
		}
	}

	private void deleteIndex(Client client, String index) {
		LOG.warn("delete index:" + index);
		IndicesAdminClient iac = client.admin().indices();
		// this.dataService.refresh();//
		DeleteIndexRequestBuilder rb = iac.prepareDelete(index);

		DeleteIndexResponse gr = rb.execute().actionGet();
		boolean ack = gr.acknowledged();
		if (!ack) {
			throw new FsException("failed to delete index :" + index);
		}
	}

	private boolean isIndexExist(Client client, String index) {
		IndicesAdminClient iac = client.admin().indices();
		IndicesExistsResponse ier = iac.prepareExists(index).execute().actionGet();
		return ier.isExists();
	}

	private MetaInfo loadDataVersion(Client client, String index, boolean force) {
		if (!this.isIndexExist(client, index)) {
			return null;
		}

		GetRequestBuilder gr = client.prepareGet();

		gr = gr.setIndex(index).setType("version").setId("0");

		GetResponse res = gr.execute().actionGet();

		Map<String, Object> src = res.getSource();

		if (src == null) {
			if (force) {
				throw new FsException("no version info found for index:" + index);
			}
			return null;
		}
		String version = (String) src.get("version");

		return new MetaInfo(version);
	}

}
