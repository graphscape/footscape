/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package com.fs.expector.gridservice.impl;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.expector.gridservice.impl.util.I18nUtil;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.terminal.MessageSendingContext;

/**
 * @author wu
 * @see TerminalManagerI.addBeforeXXX
 */
public class ErrorInfoLocalizerTMSH implements HandlerI<MessageSendingContext>{

	public ErrorInfoLocalizerTMSH(){
		
	}
	/*
	 *Apr 4, 2013
	 */
	@Override
	public void handle(MessageSendingContext sc) {
		ClientGd c = sc.getClient();
		if(c == null){
			return;
		}
		String locale = c.getLocale();
		MessageI msg = sc.getMessage();
		ErrorInfos eis = msg.getErrorInfos();
		
		for(ErrorInfo ei : eis.getErrorInfoList()){
			String key = ei.getCode();
			String text = I18nUtil.localized(locale, key);
			ei.setMessage(text);			
		}
	}

}
