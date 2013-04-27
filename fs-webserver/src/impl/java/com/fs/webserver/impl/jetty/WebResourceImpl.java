/**
 * Jul 13, 2012
 */
package com.fs.webserver.impl.jetty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.webserver.api.WebResourceI;
import com.fs.webserver.impl.util.ZipUtil;

/**
 * @author wu
 * 
 */
public class WebResourceImpl extends ConfigurableSupport implements WebResourceI {

	private JettyWebAppImpl jettyWebApp;

	/** */
	public WebResourceImpl(JettyWebAppImpl jettWebAppImpl) {
		this.jettyWebApp = jettWebAppImpl;
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		String urlS = this.config.getProperty("url", true);
		String cpath = this.config.getProperty("contextPath", true);
		String format = this.config.getProperty("format");
		if (!"zip".equals(format)) {
			throw new FsException("todo");
		}
		if (!urlS.startsWith("classpath:")) {
			throw new FsException("TODO");
		}

		String res = urlS.substring("classpath:".length());
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(res);
		if (is == null) {
			throw new FsException("web resource not found:" + urlS);
		}
		// extract to web app directory;
		File whome = this.jettyWebApp.getHome();//
		// TODO check exists
		String destS = whome.getAbsolutePath();
		File dest = new File(destS);
		if (cpath.length()>0) {

			destS = destS + File.separator + cpath;
			dest = new File(destS);
			if (dest.exists()) {
				throw new FsException("dest:" + dest + " already exist.");
			}
			dest.mkdir();//
		}

		try {
			ZipUtil.extract(is, dest);
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

}
