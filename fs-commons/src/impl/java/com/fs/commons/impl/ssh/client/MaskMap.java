/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.ssh.client;

import java.util.List;

import com.fs.commons.api.mask.Mask;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author wu
 * 
 */
public class MaskMap {
	private BiMap<Mask, Mask> mmap;

	public MaskMap() {
		this(HashBiMap.<Mask, Mask> create());
	}

	private MaskMap(BiMap<Mask, Mask> mm) {
		this.mmap = mm;
	}

	public void put(Mask m1, Mask m2) {
		this.mmap.put(m1, m2);
	}

	public MaskMap inverse() {
		return new MaskMap(mmap.inverse());
	}

	public Mask get(Mask key) {
		Mask rt = this.mmap.get(key);
		if (rt != null) {
			return rt;
		}
		List<Mask> atomL = key.split();
		if (atomL.isEmpty()) {
			return null;//
		}

		for (Mask ak : atomL) {
			Mask rtAv = this.mmap.get(ak);
			if (rtAv == null) {
				return null;
			}
			if (rt == null) {
				rt = rtAv;
			} else {
				rt = rt.or(rtAv);
			}
		}
		return rt;
	}
}
