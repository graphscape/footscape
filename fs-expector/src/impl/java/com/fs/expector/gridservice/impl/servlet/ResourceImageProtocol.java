/**
 * All right is from Author of the file,to be explained in comming days.
 * May 17, 2013
 */
package com.fs.expector.gridservice.impl.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.fs.commons.api.struct.Path;
import com.fs.commons.api.util.FileUtil;
import com.fs.commons.api.util.ImageUrl;
import com.fs.expector.gridservice.impl.handler.resource.ResourceHandler;

/**
 * @author wu
 * 
 */
public class ResourceImageProtocol extends ImageProtocol {

	@Override
	public void writeImage(ImageUrl uri, HttpServletResponse res) throws IOException {

		String pathS = uri.getData();

		Path path = Path.valueOf(pathS);

		if (!ResourceHandler.isAllowed(path)) {
			res.sendError(404);
			return;//
		}

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(path.toString(false));
		if (is == null) {
			res.sendError(404);
			return;
		}

		String name = path.getName();
		int idx = name.indexOf(".");
		String format = name.substring(idx + 1);
		format = "image/" + format;
		res.setContentType(format);// content type

		OutputStream os = res.getOutputStream();
		FileUtil.copy(is, os);

	}
}
