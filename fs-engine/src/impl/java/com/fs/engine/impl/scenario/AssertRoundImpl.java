/**
 * Jul 25, 2012
 */
package com.fs.engine.impl.scenario;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.freemarker.TemplateI;
import com.fs.commons.api.service.ServiceI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.scenario.RoundI;
import com.fs.engine.api.scenario.ScenarioI.ContextI;
import com.fs.engine.api.support.RRContext;

/**
 * @author wu
 *
 */
public class AssertRoundImpl extends ConfigurableSupport implements RoundI{
	
	private String requestFtlId;
	
	private String responseFtlId;
	
	private List<String> validateExpressionList;
	
	private ServiceI<RequestI,ResponseI> engine;
	
	private CodecI.FactoryI jsonCodecFactory;
	
	private TemplateI.FactoryI tf;
	
	private String path;
	
	public int idx;
	
	/* */
	@Override
	public void run(ContextI ctx) {
		RequestI req = this.buildRequest(ctx);
		
		ctx.setProperty("request", req);//
		ResponseI actual = this.engine.service(req);
		ctx.setProperty("response",actual);
		
		ResponseI expected = this.buildResponse(ctx);//TODO validater
		PropertiesI<Object> apl = actual.getPayloads();
		PropertiesI<Object> epl = expected.getPayloads();
		String aJson = toJson(apl);
		String eJson = toJson(epl);
		
		if(!eJson.equals(aJson)){
			ctx.getErrorInfos().add(new ErrorInfo("round:"+this.idx,"expected:"+eJson+",but was:"+aJson));
		}

		
	}
	protected PropertiesI<Object> loadDataAndProcess(ContextI ctx,String ftl) {
		
		TemplateI t1 = tf.getTemplate(ftl);//TODO
		StringBuffer sb = new StringBuffer();
		t1.process(ctx, sb);
		//json/
		CodecI cd = this.jsonCodecFactory.getCodec(PropertiesI.class);
		
		InputStreamReader rd = new InputStreamReader(new ByteArrayInputStream(sb.toString().getBytes()));
		Object obj = JSONValue.parse(rd);
		//JSONArray l = (JSONArray) obj;
		PropertiesI<Object> rt = (PropertiesI<Object>) cd.decode(obj);//

		return rt;
	}
	public RequestI buildRequest(ContextI ctx) {
		PropertiesI<Object> pl = this.loadDataAndProcess(ctx,this.requestFtlId);
		RequestI rt = RRContext.newRequest();
		rt.setPath(this.path);//
		rt.setPayloads(pl);
		return rt;

	}

	public ResponseI buildResponse(ContextI ctx) {
		PropertiesI<Object> pl = this.loadDataAndProcess(ctx,this.responseFtlId);
		ResponseI rt = RRContext.newResponse();
		rt.setPayloads(pl);
		return rt;
	}
	/* */
	@Override
	public void active(ActiveContext ac) {
		
		super.active(ac);
		this.path = this.config.getProperty("path",true);
		this.jsonCodecFactory = this.container.finder(CodecI.FactoryI.class)
				.name("JSON_CODEC_FACTORY").find(true);
		this.engine = ac.getContainer().finder(ServiceI.class).name("engine-0").find(true);//TODO
		this.tf = ac.getContainer().find(TemplateI.FactoryI.class,true);
		this.requestFtlId = this.config.getProperty("requestFtl",true);
		this.responseFtlId = this.config.getProperty("expectedResponseFtl",true);
		
		
	}
	public String toJson(PropertiesI pts) {
		CodecI c = this.jsonCodecFactory.getCodec(PropertiesI.class);
		JSONArray ja = (JSONArray) c.encode(pts);

		return ja.toJSONString();
	}


}
