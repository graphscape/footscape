/**
 *  
 */
package com.fs.integrated;

import com.fs.commons.api.SPIManagerI;

/**
 * @author wu
 * 
 */
public class Server {

	private SPIManagerI spiManager;

	public static void main(String[] args) {
		new Server().start();

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

	}

	public void start() {
		String res = "/fs-spim.properties";
		this.spiManager = SPIManagerI.FACTORY.get();
		this.spiManager.load(res);
	}
}
