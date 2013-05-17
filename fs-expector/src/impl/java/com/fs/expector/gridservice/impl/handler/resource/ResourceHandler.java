/**
 */
package com.fs.expector.gridservice.impl.handler.resource;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.util.FileUtil;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * @see
 */
public class ResourceHandler extends ExpectorTMREHSupport {

	public static Path[] allowedPathSegments = new Path[] { Path.valueOf("/open/resource") };

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
	}

	public static boolean isAllowed(Path p) {
		for (Path pi : allowedPathSegments) {
			if (p.contains(pi)) {
				return true;
			}

		}
		return false;
	}

	@Handle("get")
	public void handleGet(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		MessageI req = ew.getMessage();// request

		String urlS = req.getHeader("url", true);
		URI uri;
		try {
			uri = new URI(urlS);
		} catch (URISyntaxException e) {
			res.getErrorInfos().addError(Constants.P_ERROR_INPUT);
			return;
		}
		String pathS = uri.getPath();
		Path path = Path.valueOf(pathS);
		if (!isAllowed(path)) {
			res.getErrorInfos().addError(Constants.P_ERROR_NOTALLOW);
			return;
		}

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(path.toString(false));
		if (is == null) {
			res.getErrorInfos().addError(Constants.P_ERROR_NOTFOUND);
			return;
		}
		// TODO base 64 encode binary string
		String rt = FileUtil.readAsString(is, "utf-8");

		res.setPayload("resource", rt);

	}
	

}
