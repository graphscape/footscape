/**
 *  Jan 24, 2013
 */
package com.fs.websocket.api.mock;


/**
 * @author wuzhen
 * 
 */
public abstract class WSClientManager<T extends WSClient> {

	public abstract int size();
	
	public abstract T getFirstClient() ;

	public abstract T getLastClient() ;
	
	public abstract T getRandomClient() ;
	
	public abstract void removeRandomClient(boolean close);

	public abstract T createClient(boolean connect);
	
	public abstract void removeClient(boolean close) ;

	public abstract void removeClient(T client, boolean close) ;

	public abstract int total() ;
}
