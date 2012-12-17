/**
 * Jun 19, 2012
 */
package com.fs.commons.impl.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.FinderI;
import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.describe.Describe;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.factory.ObjectConfigI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.ContainerAwareSupport;
import com.fs.commons.api.support.DescribedSupport;

/**
 * @author wu
 * 
 */
public class ConfigFactoryImpl extends ContainerAwareSupport implements
		ConfigFactoryI {

	public static class ObjectConfig extends DescribedSupport implements
			ObjectConfigI {

		public ObjectConfig() {

		}

		@Override
		public <T> T newInstance() {
			Class oclass = this.getObjectClass();
			Object rt = ClassUtil.newInstance(oclass, new Class[] {},
					new Object[] {});
			

			return (T) rt;

		}

		public String getId() {
			return this.getPrtCfgId() + "." + this.getType() + "."
					+ this.getName();
		}

		/* */
		@Override
		public Describe getDescribe() {

			return this.describe;

		}

		/* (non-Javadoc)
		 * @see com.fs.commons.api.factory.ObjectConfigI#getCfgId()
		 */
		@Override
		public String getCfgId() {
			// TODO Auto-generated method stub
			return this.getId();
		}

	}

	// Cache.
	private Map<String, Configuration> configMap = new HashMap<String, Configuration>();

	public ConfigFactoryImpl() {

	}

	public List<ObjectConfigI> find(ConfigFinderImpl of) {

		List<ObjectConfigI> rt = new ArrayList<ObjectConfigI>();
		String pcid = of.getPrtCfgId();
		String type = of.getType();
		if (pcid == null || type == null) {
			throw new FsException("not supported");
		}

		Configuration cfg = this.getConfiguration(pcid, true);
		Map<String, ObjectConfig> cmap = this.getObjectConfigMapFrom(type, cfg);

		for (ObjectConfig oc : cmap.values()) {//
			boolean ism = oc.getDescribe().isMatchTo(of.getDescribe());

			if (ism) {//
				rt.add(oc);
			}
		}
		return rt;
	}

	private Configuration getConfiguration(String configId, boolean useCache) {
		Configuration rt = this.configMap.get(configId);
		if (rt != null) {
			if (useCache) {
				return rt;
			}
		}
		rt = Configuration.properties(configId);
		this.configMap.put(configId, rt);
		return rt;
	}

	private Map<String, ObjectConfig> getObjectConfigMapFrom(String type,
			Configuration cfg) {

		Map<String, ObjectConfig> rt = new HashMap<String, ObjectConfig>();

		List<String> kL = cfg.keyList();

		List<String> issueKs = new ArrayList<String>();
		for (String k : kL) {
			String[] ks = k.split("\\.");
			if (ks.length < 3) {//NOTE type.name.key
				issueKs.add(k);
				continue;
			}
			if (!ks[0].equals(type)) {// startWith type
				issueKs.add(k);
				continue;
			}
			String name = ks[1];

			String id = type + "." + name;// NOTE

			ObjectConfig hcfg = rt.get(id);
			if (hcfg == null) {
				hcfg = new ObjectConfig();
				hcfg.prtCfgId(cfg.getId()).type(type).name(name);
				rt.put(id, hcfg);
			}
			String pname = ks[2];
			String pvalue = cfg.getProperty(k);
			if (pname.equals("class")) {
				Class cls = ClassUtil.forName(pvalue);
				hcfg.objectClass(cls);
			} else {
				throw new FsException("TODO:" + pname + ",in:" + cfg.getId());
			}
		}

		return rt;
	}

	/*
	
	 */
	@Override
	public PopulatorI newPopulator() {

		return new PopulatorImpl(this);

	}

	/*
	
	 */
	@Override
	public FinderI<ObjectConfigI> finder() {

		return new ConfigFinderImpl(this);
	}

}
