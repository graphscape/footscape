/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.exporter.rmi;

import java.rmi.RemoteException;

import org.springframework.remoting.rmi.RmiServiceExporter;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.support.ExporterSupport;

/**
 * @author wu
 * 
 */
public class RmiServiceExporterImpl extends ExporterSupport implements
		ActivableI {

	/**
	 * 
	 * @author wu
	 * 
	 */
	public static interface RmiServiceI {
		public ResponseI service(RequestI req);
	}

	/**
	 * 
	 * @author wu
	 * 
	 */
	public static class RmiServiceImpl implements RmiServiceI {
		RmiServiceExporterImpl exp;

		public RmiServiceImpl(RmiServiceExporterImpl exp) {
			this.exp = exp;
		}

		/* */
		@Override
		public ResponseI service(RequestI req) {
			ResponseI rt = this.exp.service(req);
			return rt;
		}

	}

	//

	private ServiceEngineI engine;

	private RmiServiceI rmi;

	/** */
	public RmiServiceExporterImpl() {
		super();

	}

	public ResponseI service(RequestI req) {
		ResponseI rt = this.engine.service(req);
		return rt;

	}

	@Override
	public void start() {
		RmiServiceExporter rse = new RmiServiceExporter();

		this.engine = this.container.find(ServiceEngineI.class);//

		this.rmi = new RmiServiceImpl(this);
		rse.setServiceName(this.config.getProperty("service.name", "service"));

		rse.setService(this.rmi);
		rse.setServiceInterface(RmiServiceI.class);
		rse.setRegistryPort(this.config.getPropertyAsInt("registry.port", 1099));
		try {
			rse.afterPropertiesSet();
		} catch (RemoteException e) {
			throw new FsException(e);
		}
	}

	/* */
	@Override
	public void shutdown() {
	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.start();// TODO
	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {

	}

}
