/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.test.cases;

import junit.framework.TestCase;

import com.fs.commons.api.mask.Mask;
import com.fs.commons.impl.ssh.client.MaskMap;

/**
 * @author wu
 * 
 */
public class MaskMapTest extends TestCase {
	public void test() {
		Mask mf1 = Mask.valueOf(0x1);
		Mask mf2 = Mask.valueOf(0x2);
		Mask mf4 = Mask.valueOf(0x4);
		Mask mf8 = Mask.valueOf(0x8);

		Mask mt1 = Mask.valueOf(0x2);
		Mask mt2 = Mask.valueOf(0x4);
		Mask mt4 = Mask.valueOf(0x1);
		Mask mt8 = Mask.valueOf(0x8);

		MaskMap mm = new MaskMap();
		mm.put(mf1, mt1);
		mm.put(mf2, mt2);
		mm.put(mf4, mt4);
		mm.put(mf8, mt8);
		
		assertEquals(mt1, mm.get(mf1));
		assertEquals(mt2, mm.get(mf2));
		assertEquals(mt4, mm.get(mf4));
		assertEquals(mt8, mm.get(mf8));
		
		assertEquals(mt1.or(mt2), mm.get(mf1.or(mf2)));
		assertEquals(mt2.or(mt4), mm.get(mf2.or(mf4)));
		assertEquals(mt8.or(mt1), mm.get(mf8.or(mf1)));
		assertEquals(mt1.or(mt2).or(mt8), mm.get(mf1.or(mf2).or(mf8)));

		//
		assertEquals(mt1.or(mt1), mm.get(mf1.or(mf1)));

	}
}
