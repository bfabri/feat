package br.pucrio.inf.les.feat.core.domainmodel;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class Feature {
	
	private final String name;
	private final String description;
	
	private final Version version;
	
	private Set<Element> elements;
	
	public Feature(Version version, String name, String description) {
		this.version = version;
		this.name = name;
		this.description = description;
		this.elements = new HashSet<Element>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public Version getVersion() {
		return this.version;
	}
	
	public Element[] getElements() {
		return this.elements.toArray(new Element[this.elements.size()]);
	}
	
	public boolean addElement(Element element) {
		return this.elements.add(element);
	}
	
	public boolean removeElement(Element element) {
		return this.elements.remove(element);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Feature) {
				Feature other = (Feature) obj;
				if (this.name != null && other.name != null) {
					return this.name.equals(other.name);
				}
			}
		}
		return false;
	}
}
