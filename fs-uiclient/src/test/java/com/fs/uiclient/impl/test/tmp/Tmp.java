/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
*/
package com.fs.uiclient.impl.test.tmp;

import junit.framework.TestCase;

/**
 * @author wuzhen
 *
 */
public class Tmp extends TestCase{
	public void testMask(){
		int mask = 1|2;
		System.out.print(mask&1);
		System.out.print(mask&2);
		System.out.print(mask&4);
		
	}
}
