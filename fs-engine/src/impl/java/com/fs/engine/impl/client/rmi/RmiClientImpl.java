/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.client.rmi;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.fs.commons.api.ActiveContext;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.impl.exporter.rmi.RmiServiceExporterImpl;
import com.fs.engine.impl.support.ServiceSupport;

/**
 * @author wu
 *         <code>http://static.springsource.org/spring/docs/2.0.x/reference/remoting.html</code>
 */
public class RmiClientImpl extends ServiceSupport {

	private RmiServiceExporterImpl.RmiServiceI rmi;

	/** */
	public RmiClientImpl() {
		super();

	}

	/* */
	@Override
	public void service(RequestI req, ResponseI res) {
		ResponseI res2 = this.rmi.service(req);
		res.setProperties(res2);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		RmiProxyFactoryBean fb = new RmiProxyFactoryBean();
		fb.setServiceInterface(RmiServiceExporterImpl.RmiServiceI.class);
		fb.setServiceUrl(this.config.getProperty("service.url", true));
		fb.afterPropertiesSet();

		this.rmi = (RmiServiceExporterImpl.RmiServiceI) fb.getObject();

	}

}
