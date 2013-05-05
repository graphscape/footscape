/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 7, 2012
 */
package com.fs.expector.gridservice.api;

import com.fs.commons.api.struct.Path;

/**
 * @author wu
 * 
 */
public interface Constants {

	public static final String HK_CLIENT_ID = "x-fs-client-id";

	public static Path P_ERROR = Path.valueOf("error");

	public static Path P_ERROR_INPUT = P_ERROR.getSubPath("input");

	public static Path P_ERROR_NOTALLOW = P_ERROR.getSubPath("notallow");

	public static Path P_ERROR_NOTFOUND = P_ERROR.getSubPath("notfound");

	public static Path P_ERROR_SIGNUP = P_ERROR.getSubPath("signup").getSubPath("failure");

	public static Path P_ERROR_SIGNUP_NICK = P_ERROR.getSubPath("signup").getSubPath("nick");

	public static Path P_ERROR_SIGNUP_EMAIL = P_ERROR.getSubPath("signup").getSubPath("email");

	public static Path P_ERROR_SIGNUP_PASSWORD = P_ERROR.getSubPath("signup").getSubPath("password");

	public static Path P_ERROR_EXPE = P_ERROR.getSubPath("expe");

	public static Path P_ERROR_EXPE_SUBMIT = P_ERROR_EXPE.getSubPath("submit");

	public static Path P_ERROR_EXPE_SUBMIT_TITLE = P_ERROR_EXPE_SUBMIT.getSubPath("title");

	public static Path P_ERROR_EXPE_SUBMIT_SUMMARY = P_ERROR_EXPE_SUBMIT.getSubPath("summary");

	public static Path P_ERROR_EXPE_SUBMIT_FORMAT = P_ERROR_EXPE_SUBMIT.getSubPath("format");

	public static Path P_ERROR_EXPE_SUBMIT_BODY = P_ERROR_EXPE_SUBMIT.getSubPath("body");

	public static Path P_ERROR_EXP_OVERFLOW = P_ERROR_EXPE.getSubPath("overflow");

	public static Path P_ERROR_PASSWORD = P_ERROR.getSubPath("password");

	public static Path P_ERROR_PASSWORD_EMAIL = P_ERROR_PASSWORD.getSubPath("email");

	public static Path P_ERROR_PASSWORD_NEWPASSWORD = P_ERROR_PASSWORD.getSubPath("newPassword");

	public static Path P_ERROR_PASSWORD_PFID = P_ERROR_PASSWORD.getSubPath("pfId");

	public static Path P_ERROR_PROFILE = P_ERROR.getSubPath("profile");

	public static Path P_ERROR_PROFILE_BIRTHDAY = P_ERROR_PROFILE.getSubPath("birthDay");

	public static Path P_ERROR_PROFILE_GENDER = P_ERROR_PROFILE.getSubPath("gender");

	public static Path P_ERROR_PROFILE_ICON = P_ERROR_PROFILE.getSubPath("icon");

	//
	public static Path P_ERROR_CTTMSG = P_ERROR.getSubPath("cttmsg");

	public static Path P_ERROR_CTTMSG_MESSAGE = P_ERROR_CTTMSG.getSubPath("submit");

	public static Path P_ERROR_CTTMSG_MESSAGE_SUBJECT = P_ERROR_CTTMSG_MESSAGE.getSubPath("subject");
	public static Path P_ERROR_CTTMSG_MESSAGE_NAME = P_ERROR_CTTMSG_MESSAGE.getSubPath("name");
	public static Path P_ERROR_CTTMSG_MESSAGE_EMAIL = P_ERROR_CTTMSG_MESSAGE.getSubPath("email");
	public static Path P_ERROR_CTTMSG_MESSAGE_BODY = P_ERROR_CTTMSG_MESSAGE.getSubPath("body");

}
