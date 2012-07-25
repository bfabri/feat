package br.pucrio.inf.les.feat.core.domainmodel;

public enum ElementType {
	CLASS("Class"),
	INTERFACE("Interface"),
	ENUM("Enumeration"),
	METHOD("Method"),
	ATTRIBUTE("Attribute"),
	ENUM_CONSTANT("Enumeration Constant");
	
	private String description;
	
	private ElementType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
