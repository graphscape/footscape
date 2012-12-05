/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.test.handler;

import java.util.Arrays;

import com.fs.commons.api.lang.ObjectUtil;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class TestHandler1 extends HandlerSupport {

	public static class CommonBean implements java.io.Serializable {
		private String stringValue;

		private int intValue;

		private long longValue;

		/**
		 * @return the stringValue
		 */
		public String getStringValue() {
			return stringValue;
		}

		/**
		 * @param stringValue
		 *            the stringValue to set
		 */
		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

		/**
		 * @return the intValue
		 */
		public int getIntValue() {
			return intValue;
		}

		/**
		 * @param intValue
		 *            the intValue to set
		 */
		public void setIntValue(int intValue) {
			this.intValue = intValue;
		}

		/**
		 * @return the longValue
		 */
		public long getLongValue() {
			return longValue;
		}

		/**
		 * @param longValue
		 *            the longValue to set
		 */
		public void setLongValue(long longValue) {
			this.longValue = longValue;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}

			if (!(o instanceof CommonBean)) {
				return false;
			}
			CommonBean cb = (CommonBean) o;
			return Arrays.equals(new Object[] { this.intValue, this.longValue,
					this.stringValue }, new Object[] { cb.intValue,
					cb.longValue, cb.stringValue });

		}
	}

	public static class ReqBean extends CommonBean {
		@Override
		public boolean equals(Object obj) {
			boolean rt = super.equals(obj);
			if (!rt) {
				return false;
			}
			ReqBean rb = (ReqBean) obj;
			return true;
		}
	}

	public static class ResBean extends CommonBean {
		private CommonBean bean;

		/**
		 * @return the bean
		 */
		public CommonBean getBean() {
			return bean;
		}

		/**
		 * @param bean
		 *            the bean to set
		 */
		public void setBean(CommonBean bean) {
			this.bean = bean;
		}

		@Override
		public boolean equals(Object obj) {
			boolean rt = super.equals(obj);
			if (!rt) {
				return false;
			}
			if (!(obj instanceof ResBean)) {
				return false;
			}
			ResBean rb = (ResBean) obj;
			return ObjectUtil.nullSafeEquals(rb.bean, this.bean);
		}
	}

	/**
	 * @param cfg
	 */
	public TestHandler1() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void handle(HandleContextI sc) {
		ReqBean req = (ReqBean)sc.getFilterContext().getRequest()
				.getPayload();
		ResBean res = newResponse(req);
		sc.getFilterContext().getResponse().setPayload(res);
	}

	public static ReqBean newRequest() {
		ReqBean rt = new ReqBean();
		rt.setIntValue(1);
		rt.setLongValue(1);
		rt.setStringValue("Request string value");
		return rt;
	}

	public static ResBean newResponse(ReqBean req) {
		ResBean res = new ResBean();
		res.bean = req;
		//
		res.setIntValue(2);
		res.setLongValue(2);
		res.setStringValue("Response string value");
		return res;
	}

}
