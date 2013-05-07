/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.api.gobject;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.GridedObjectI;

/**
 * @author wu
 * 
 */
public interface EndPointGoI extends GridedObjectI {

	public static final Path P_SERVER_IS_READY = Path.valueOf("/control/status/serverIsReady");

	public static final Path P_CLIENT_IS_READY = Path.valueOf("/control/status/clientIsReady");

	public void sendReady(String sourceMsageId, String termId, String clientId);

	public void sendMessage(MessageI msg);

	public void sendTextMessage(String msg);

	public String getTerminalId(boolean b);

	public String getClientId(boolean b);

}
