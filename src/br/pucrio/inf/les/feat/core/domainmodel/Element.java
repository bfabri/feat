package br.pucrio.inf.les.feat.core.domainmodel;

public class Element implements ITreeNode {

	private final String name;
	private final int startLine;
	private final int endLine;
	private final String location;
	private final ElementType type;
	
	private Feature feature;

	public Element(String name, int startLine, int endLine, String location,
			ElementType type) {
		this.name = name;
		this.startLine = startLine;
		this.endLine = endLine;
		this.location = location;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public int getStartLine() {
		return startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public String getLocation() {
		return location;
	}

	public String getType() {
		return this.type.getDescription();
	}
	
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	
	@Override
	public ITreeNode[] getChildrens() {
		return null;
	}
	
	@Override
	public ITreeNode getParent() {
		return feature;
	}
	
	@Override
	public String getPrintName() {
		return String.format("%1$s: %2$s", getType(), getName());
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
}
