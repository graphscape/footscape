/**
 * Jul 25, 2012
 */
package com.fs.engine.impl.scenario;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.scenario.RoundI;
import com.fs.engine.api.scenario.ScenarioI;

/**
 * @author wu
 * 
 */
public class ScenarioImpl extends ConfigurableSupport implements ScenarioI {

	private List<RoundI> roundList;

	/* */
	@Override
	public List<RoundI> getRoundList() {

		return this.roundList;

	}

	/* */
	@Override
	public ContextI run() {

		ScenarioContext ctx = new ScenarioContext(this);//
		TemplateHelper th = new TemplateHelper(this.container);//
		for (int i = 0; i < this.roundList.size(); i++) {
			RoundI r = this.roundList.get(i);
			RoundContext rr = new RoundContext(ctx, r);//
			rr.setProperty("Helper", th);// TODO remove
			ctx.getRoundContextList().add(rr);

			r.run(rr);
		}
		return ctx;

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		//
		this.roundList = new ArrayList<RoundI>();
		List<PropertiesI<String>> rL = this.config.parseAsPropertiesList(
				"round", new String[] { "config" });
		for (int i = 0; i < rL.size(); i++) {

			PropertiesI<String> ps = rL.get(i);
			String cfgId = ps.getProperty("config");
			cfgId = this.configId + "." + cfgId;
			AssertRoundImpl r = new AssertRoundImpl();
			r.idx = i;

			ac.activitor().object(r).cfgId(cfgId).active();//
			this.roundList.add(r);
		}

	}

}
