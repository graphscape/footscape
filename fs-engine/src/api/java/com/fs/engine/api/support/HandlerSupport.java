/**
 * Jun 14, 2012
 */
package com.fs.engine.api.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.context.support.ContextSupport;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestHandlerI;
import com.fs.engine.api.annotation.Handle;

/**
 * @author wuzhen
 * 
 */
public abstract class HandlerSupport extends ConfigurableSupport implements
		RequestHandlerI {

	protected static class MethodWrapper extends ContextSupport {
		public Method method;

		public Class[] ptypes;

		public ConverterI<HandleContextI, Object>[] converters;

		public String key;

		public MethodWrapper(String key, Method md) {
			this.method = md;
			this.key = key;
			this.ptypes = this.method.getParameterTypes();

		}

		public void invoke(HandlerSupport obj, HandleContextI hc)
				throws IllegalAccessException, IllegalArgumentException,
				InvocationTargetException {
			Object[] args = this.toArgs(hc);
			this.method.invoke(obj, args);
		}

		public Object[] toArgs(HandleContextI hc) {
			Object[] rt = new Object[this.ptypes.length];
			for (int i = 0; i < rt.length; i++) {
				Object oi = this.converters[i].convert(hc);
				rt[i] = oi;
			}
			return rt;
		}

		public String toString() {
			return this.key + ":" + this.method;
		}

	}

	protected ConverterI.FactoryI factory;
	// methods to dispatch
	protected Map<String, MethodWrapper> methodEntryMap;

	/**
	 * @param cfg
	 */
	public HandlerSupport() {
		super();

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		this.factory = this.container.find(ConverterI.FactoryI.class, true);// TODO
																			// use
																			// seperated
																			// factory
																			// by
																			// name

		this.methodEntryMap = new HashMap<String, MethodWrapper>();

		Method[] ms = this.getClass().getDeclaredMethods();// TODO merge
															// getMethods
		for (Method m : ms) {
			String key = this.getPathOfMethod(m);//
			if (key == null) {// not a handle method
				continue;
			}

			MethodWrapper old = this.methodEntryMap.get(key);
			if (old != null) {
				throw new FsException("path duplicated:" + key
						+ " for handler:" + this);
			}

			MethodWrapper me = new MethodWrapper(key, m);// TODO
			me.converters = this.getConverterArray(me);// ?
			// remove
			// parameter
			// factory

			this.methodEntryMap.put(key, me);
		}

	}

	protected MethodWrapper getMethodWrapper(String key, boolean force) {
		MethodWrapper rt = this.methodEntryMap.get(key);
		if (force && rt == null) {
			throw new FsException("force:" + key);
		}
		return rt;
	}

	private ConverterI getConverter(Class ptype, MethodWrapper me) {

		ConverterI rt = this.factory.getConverter(HandleContextI.class, ptype,
				false);//
		if (rt == null) {
			rt = this.getDefaultConverter(ptype, me);
		}

		if (rt == null) {

			throw new FsException("no converter found for type:" + ptype
					+ ",in method:" + me);
		}
		return rt;
	}

	/**
	 * @param endpath
	 * @return
	 */
	protected ConverterI getDefaultConverter(Class ptype, MethodWrapper me) {
		throw new FsException("to be override.");

	}

	private ConverterI<HandleContextI, Object>[] getConverterArray(
			MethodWrapper me) {
		Class[] ptypes = me.ptypes;
		ConverterI<HandleContextI, Object>[] rt = new ConverterI[ptypes.length];
		for (int i = 0; i < ptypes.length; i++) {
			Class ptype = ptypes[i];// target type

			rt[i] = this.getConverter(ptype, me);

		}
		return rt;
	}

	private String getPathOfMethod(Method m) {
		Handle pa = m.getAnnotation(Handle.class);
		if (pa != null) {
			String[] vs = pa.value();
			return vs[0];//
		}
		String name = m.getName();

		if (!name.startsWith("handle") || name.equals("handle")) {
			return null;
		}

		name = name.substring("handle".length());
		String fist = name.substring(0, 1);

		name = fist.toLowerCase() + name.substring(1);

		return name;
	}

	private String getEndPath(String path) {
		if (path == null) {
			return null;
		}
		int idx = path.lastIndexOf("/");
		return path.substring(idx + 1);
	}

	@Override
	public void handle(HandleContextI sc) {
		String path = sc.getRequest().getPath();
		String endp = this.getEndPath(path);
		MethodWrapper me = this.methodEntryMap.get(endp);
		if (me == null) {
			this.none(sc);
			return;
		}

		try {
			me.invoke(this, sc);
		} catch (IllegalAccessException e) {
			throw new FsException(e);
		} catch (IllegalArgumentException e) {
			throw new FsException(e);
		} catch (InvocationTargetException e) {
			Throwable t = e.getCause();
			if (t == null) {
				throw FsException.toRtE(e);
			} else {
				throw FsException.toRtE(t);
			}
		}

	}

	protected void none(HandleContextI sc) {
		throw new FsException("handler:" + this
				+ " has no method for request with path:"
				+ sc.getRequest().getPath());
	}

}
