/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.test.cases;

import junit.framework.TestCase;

/**
 * @author wu
 * 
 */
public class RegTest extends TestCase {

	public void test() {
		assertTrue("/abc/deffffgea".matches("/abc/.*"));
		assertTrue("/abc/".matches("/abc/.*"));
		assertTrue("/abc/def".matches("/.*/.*"));

	}
}
