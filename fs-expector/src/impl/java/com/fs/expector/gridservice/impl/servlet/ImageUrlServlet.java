/**
 *  
 */
package com.fs.expector.gridservice.impl.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.util.ImageUrl;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.dataservice.api.ExpectorDsFacadeI;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 * 
 */
public class ImageUrlServlet extends ConfigurableServletSupport {

	private static final Logger LOG = LoggerFactory.getLogger(ImageUrlServlet.class);

	protected DataServiceI dataService;

	protected Map<String, ImageProtocol> imageProviderMap;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);

		DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//
		this.imageProviderMap = new HashMap<String, ImageProtocol>();
		this.imageProviderMap.put(ExpectorDsFacadeI.IMG_PROTOCOL_IID, new ImageContentProtocol(
				this.dataService));
		this.imageProviderMap.put(ExpectorDsFacadeI.IMG_PROTOCOL_RES, new ResourceImageProtocol());

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {

		String str = req.getRequestURI();
		int idxSlash = str.lastIndexOf("/");//
		// get only the last part of path as the image url.
		String urlEncoded = str.substring(idxSlash + 1);

		String urlS = URLDecoder.decode(urlEncoded);

		ImageUrl iu = ImageUrl.parse(urlS, true);
		String pro = iu.getProtocol();
		ImageProtocol ip = this.imageProviderMap.get(pro);

		if (ip == null) {
			res.sendError(404, "protocol not support:" + ip);//
			return;
		}

		ip.writeImage(iu, res);

	}
}
