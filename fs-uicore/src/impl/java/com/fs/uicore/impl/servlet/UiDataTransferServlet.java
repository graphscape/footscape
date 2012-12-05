/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.ConfigurationProviderI;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.service.ServiceI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.EngineAPI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.RRContext;

/**
 * @author wu
 *         <p>
 *         this servlet used for product env,embedded in jetty web server.
 */
public class UiDataTransferServlet extends HttpServlet implements
		ConfigurableI, ActivableI {

	public static final String PATH = "X_FS_PATH";

	private static final Logger LOG = LoggerFactory
			.getLogger(UiDataTransferServlet.class);

	protected CodecI.FactoryI cf;

	protected ServiceI<RequestI, ResponseI> engine;

	protected boolean traceMsg;

	protected Configuration config;

	/* */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	/* */
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		//
		Reader rd = arg0.getReader();
		Object obj = JSONValue.parse(rd);
		if (this.traceMsg) {
			LOG.info("obj:" + obj);
		}

		JSONArray l = (JSONArray) obj;
		String type = (String) l.get(0);

		CodecI c = this.cf.getCodec(type);
		// TODO
		String path = arg0.getHeader(PATH);// TODO

		PropertiesI<Object> reqPl = (PropertiesI<Object>) c.decode(obj);

		RequestI req = RRContext.newRequest();
		for (Enumeration<String> e = arg0.getHeaderNames(); e.hasMoreElements();) {
			String key = e.nextElement();
			String value = arg0.getHeader(key);
			req.setHeader(key, value);//
		}
		req.setPath(path);//
		req.setPayloads(reqPl);//

		ResponseI res = this.engine.service(req);

		PropertiesI<Object> resPl = res.getPayloads();

		//
		Object jo = c.encode(resPl);
		if (this.traceMsg) {
			LOG.info("<<" + jo);
		}
		Writer out = arg1.getWriter();

		JSONValue.writeJSONString(jo, out);
	}

	/* */
	@Override
	public void destroy() {

		super.destroy();

	}

	@Override
	public void active(ActiveContext ac) {
		//
		ContainerI ctn = ac.getContainer();
		this.engine = ctn.finder(ServiceI.class).name(EngineAPI.RMI_CLIENT)
				.find(true);// TODO not rmi.
		this.cf = ctn.find(CodecI.FactoryI.class, true);//

		this.traceMsg = Boolean.getBoolean(this.config.getProperty("traceMsg",
				"true"));
		//
	}

	@Override
	public void deactive(ActiveContext ac) {
		//
		//
	}

	@Override
	public void configure(Configuration cfg) {
		//
		this.config = cfg;
		//
	}

	@Override
	public void configure(String id, ConfigurationProviderI cp) {
		//
		throw new FsException("TODO");
		//
	}

	@Override
	public Configuration getConfiguration() {
		//
		return null;
		//
	}

}
