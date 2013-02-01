/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 11, 2012
 */
package com.fs.uiclient.impl.gwt.client;

/**
 * @author wuzhen
 * 
 */
public class Constants {

	public static final String X_FS_TEST_CASE = "X_FS_TEST_CASE";

	public static final String X_FS_SESSION_ID = "X_FS_SESSION_ID";// header

	public static final String SESSION = "_SESSION";//

	public static class IndexControlConstants {
	}

	public static class MainControlConstants {
		public static final String USERID_PROP = "userid";
		public static final String SESSIONID_PROP = "sessionid";
		public static final String DISPLAYNAME_PROP = "displayName";
	}

	public static class SearchActivityControlConstants {
		public static final String SEARCH_ACTION = "search";
		public static final String SEARCH_RESULT_ACTIS_PROP = "searchResultActivities";
	}

	public static class MyActivitiesControlConstants {
		public static final String INIT_ACTION = "init";
		public static final String MY_ACTIS_PROP = "myActivities";

	}

	public static class HotActivityDefsControlConstants {
		public static final String INIT_ACTION = "init";
		public static final String HOT_ACTI_DEF_PROP = "top10ActivityDef";
	}

}
