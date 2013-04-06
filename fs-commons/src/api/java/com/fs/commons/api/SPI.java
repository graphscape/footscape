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

	public SPIManagerI getSPIManager();
	
	public void setSPIManager(SPIManagerI sm);
	
	public void beforeShutdown(int loop);
	
	public String getId();

	public List<String> getDependenceList();
}
