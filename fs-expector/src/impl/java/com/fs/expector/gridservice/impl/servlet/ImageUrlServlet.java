/**
 *  
 */
package com.fs.expector.gridservice.impl.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.util.ImageUrl;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.dataservice.api.ExpectorDsFacadeI;
import com.fs.expector.dataservice.api.wrapper.ImageContent;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 * 
 */
public class ImageUrlServlet extends ConfigurableServletSupport {

	private static final Logger LOG = LoggerFactory.getLogger(ImageUrlServlet.class);

	protected DataServiceI dataService;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);

		DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		
		String str = req.getRequestURI();
		int idxSlash = str.lastIndexOf("/");//
		//get only the last part of path as the image url.
		String urlEncoded = str.substring(idxSlash+1);
		
		String urlS = URLDecoder.decode(urlEncoded);
		
		ImageUrl iu = ImageUrl.parse(urlS, true);
		
		if (!ExpectorDsFacadeI.PROTOCOL_IID.equals(iu.getProtocol())) {
			res.sendError(404, "protocol not support:"+iu.getProtocol());//
			return;
		}
		
		String id = iu.getData();
		ImageContent ic = this.dataService.getNewestById(ImageContent.class, id, false);

		if (ic == null) {
			res.sendError(404);//
			return;
		}

		String body = ic.getData();
		res.setContentType(ic.getFormat());//
		res.setHeader("Cache-Control", "public, max-age=3600");//second
		byte[] bts = DatatypeConverter.parseBase64Binary(body);
		res.getOutputStream().write(bts);

	}
}
