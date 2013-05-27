/**
 * Jul 9, 2012
 */
package com.fs.commons.impl.config.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public class XmlConfigElement extends XmlElement {

	private String initId;

	/** */
	public XmlConfigElement(String id, Element ele) {
		super(null, ele);
		this.initId = id;
	}

	public XmlConfigElement(XmlElement parent, Element ele) {
		super(parent, ele);

	}

	public String getType() {
		return this.element.getName();
	}

	public String getName() {
		if (this.initId != null) {
			return this.initId;
		}

		String name = this.element.attributeValue("name");// config name
		if (name == null) {
			name = this.getType();
		}
		return this.prefix(name);
	}

	public String getPropertyKey(boolean force) {
		String pname = this.element.attributeValue("property");
		if (pname == null && force) {
			throw new FsException("missing property in " + this);
		}
		return this.prefix(pname);

	}

	public static XmlConfigElement load(String prefix, String id, boolean force) {
		SAXReader reader = new SAXReader();

		InputStream is = getInputStream(prefix, id, force);
		if (is == null) {
			if (force) {
				throw new FsException("force:" + prefix + "," + id);
			}
			return null;
		}
		try {
			Document doc = reader.read(is);

			return new XmlConfigElement(id, doc.getRootElement());
		} catch (DocumentException e) {
			throw new FsException("error when load config,prefix:" + prefix + ",id:" + id, e);
		}
	}

	public static InputStream getInputStream(String prefix, String id,
			boolean force) {
		InputStream is = null;
		if (prefix.equals("env")) {//

			String userhome = System.getProperty("user.home");

			File f = new File(userhome + File.separator + ".fs"
					+ File.separator + "env" + File.separator + id + ".xml");
			if (f.exists()) {
				try {
					is = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					throw new FsException();
				}
			}

		} else {
			String resource = "/" + prefix + "/" + id + ".xml";
			is = XmlConfigElement.class.getResourceAsStream(resource);
		}

		if (is == null) {
			if (force) {
				throw new FsException();

			} else {
				return null;
			}
		}
		return is;
	}

	/**
	 * @return the properties
	 */
	public PropertiesI<String> getProperties() {
		PropertiesI<String> rt = new MapProperties<String>();
		// attributes
		for (Object aO : this.element.attributes()) {
			Attribute a = (Attribute) aO;
			String name = a.getName();
			if (name.equals("name") || name.equals("config") || name.endsWith("property")) {
				continue;
			}

			String value = a.getValue();
			rt.setProperty(name, value);
		}
		// properties
		for (XmlPropertyElement pe : this.getChildListIncludeThatOfPrefixElement(XmlPropertyElement.class)) {
			String key = pe.getKey();
			String value = pe.getValue();
			rt.setProperty(key, value);

		}

		// child config as propery
		for (XmlConfigElement ce : this.getChildListIncludeThatOfPrefixElement(XmlConfigElement.class)) {
			String key = ce.getPropertyKey(false);
			if (key == null) {
				continue;
			}
			String value = ce.getName();// name as the property value in parent.
			rt.setProperty(key, value);
		}

		return rt;

	}

	private <T extends XmlElement> List<T> getChildListIncludeThatOfPrefixElement(Class<T> cls) {
		List<T> rt = this.getChildList(cls);

		for (XmlPrefixElement pe : this.getPrefixElementList()) {
			List<T> cL = pe.getChildList(cls);
			rt.addAll(cL);
		}
		return rt;
	}

	protected List<XmlPrefixElement> getPrefixElementList() {
		return this.getChildList(XmlPrefixElement.class);
	}

	/**
	 * Find in child,offspring
	 * 
	 * @param id
	 * @return
	 */
	public XmlConfigElement findByConfigId(String id, boolean force) {

		if (this.getId().equals(id)) {
			return this;
		}
		for (XmlConfigElement ce : this.findList(XmlConfigElement.class, false)) {
			XmlConfigElement xce = ce.findByConfigId(id, false);

			if (xce != null) {
				return xce;
			}

		}
		if (force) {
			throw new FsException("no configuration found,id:" + id + ",in:" + this);
		}
		return null;

	}

	public Map<String, String> getConfigRefMap() {
		Map<String, String> rt = new HashMap<String, String>();
		for (XmlRefElement ce : this.getChildList(XmlRefElement.class)) {

			String old = rt.put(ce.getName(), ce.getReference());
			if (old != null) {
				throw new FsException("reference duplicated for key:" + ce.getName() + ",old:" + old);
			}
		}
		return rt;
	}

	public List<String> getChildConfigNameList() {
		List<String> rt = new ArrayList<String>();
		for (XmlConfigElement ce : this.getChildList(XmlConfigElement.class)) {
			String name = ce.getName();
			if (rt.contains(name)) {
				throw new FsException("duplicated child name:" + name + " under configuration:"
						+ this.getId());
			}
			rt.add(name);

		}
		return rt;
	}

	public String getId() {
		XmlConfigElement p = this.getParentConfig(false);

		return (p == null ? "" : p.getId() + ".") + this.getName();
	}
}
