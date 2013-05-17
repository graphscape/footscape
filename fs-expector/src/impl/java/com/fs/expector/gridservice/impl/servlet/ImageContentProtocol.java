/**
 * All right is from Author of the file,to be explained in comming days.
 * May 17, 2013
 */
package com.fs.expector.gridservice.impl.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.fs.commons.api.util.ImageUrl;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.dataservice.api.wrapper.ImageContent;

/**
 * @author wu
 * 
 */
public class ImageContentProtocol extends ImageProtocol {

	private static final String CACHE_CONTROL = "Cache-Control:public, max-age=31536000";

	private DataServiceI dataService;

	public ImageContentProtocol(DataServiceI ds) {
		this.dataService = ds;
	}

	@Override
	public void writeImage(ImageUrl iu, HttpServletResponse res) throws IOException {
		String id = iu.getData();
		ImageContent ic = this.dataService.getNewestById(ImageContent.class, id, false);

		if (ic == null) {
			res.sendError(404);//
			return;
		}

		String body = ic.getData();
		res.setContentType(ic.getFormat());// image/png
		res.setHeader("Cache-Control", CACHE_CONTROL);// second
		byte[] bts = DatatypeConverter.parseBase64Binary(body);
		res.getOutputStream().write(bts);
	}
}
