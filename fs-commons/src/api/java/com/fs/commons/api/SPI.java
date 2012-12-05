/**
 * Jun 15, 2012
 */
package com.fs.commons.api;

import java.util.List;

/**
 * @author wuzhen
 * 
 */
public interface SPI extends ActivableI {

	public String getId();

	public List<String> getDependenceList();
}
