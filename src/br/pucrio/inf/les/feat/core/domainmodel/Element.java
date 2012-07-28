package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public abstract class Element implements ITreeContentNode, IStyledLabel {
	protected final String name;
	protected final String elementPackage;
	protected final int startLine;
	protected Feature feature;

	protected Element(String name, String elementPackage, int startLine) {
		this.name = name;
		this.elementPackage = elementPackage;
		this.startLine = startLine;
	}
	
	public abstract Image getImage();
	public abstract StyledString getStyledLabel();
	
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
	
	@Override
	public ITreeContentNode[] getChildrens() {
		return null;
	}
	
	@Override
	public ITreeContentNode getParent() {
		return this.feature;
	}
}
