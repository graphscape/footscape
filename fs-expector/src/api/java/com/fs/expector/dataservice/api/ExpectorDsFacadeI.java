/**
 *  
 */
package com.fs.expector.dataservice.api;

import java.util.Date;
import java.util.List;

import com.fs.commons.api.util.ImageUrl;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.Expectation;

/**
 * @author wu
 * 
 */
public interface ExpectorDsFacadeI {
	
	public static final String IMG_PROTOCOL_IID = "iid";
	
	public static final String IMG_PROTOCOL_RES = "res";
	

	public int getOverflowConnectedExpCount(String expId1);

	public int getConnectedExpCount(String expId);

	public ImageUrl getIconByAccountId(String accId1);

	public Account getAccountByEmail(String email);

	public Account updatePassword(String aid, String pass);

	/**
	 * @param pts
	 *            Expectation record
	 */
	public void processExpIcon(PropertiesI<Object> pts);

	public List<PropertiesI<Object>> convertToClientFormat(List<Expectation> el);

	public void acknowledgeExpMessage(String accId, String expId, Date ts);

	/**
	 * May 3, 2013
	 */

	public int getExpectationOpenCount(String aid);

	public int getMaxLimitOfExpectationOpenCount();

	public ImageUrl saveImage(ImageUrl data);

	/**
	 *May 24, 2013
	 */
	public ImageUrl getDefaultUserIconImageUrl();

}
