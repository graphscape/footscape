/**
 * All right is from Author of the file,to be explained in comming days.
 * May 17, 2013
 */
package com.fs.expector.gridservice.impl.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fs.commons.api.util.ImageUrl;

/**
 * @author wu
 * 
 */
public abstract class ImageProtocol {

	public abstract void writeImage(ImageUrl uri, HttpServletResponse res) throws IOException;
}
