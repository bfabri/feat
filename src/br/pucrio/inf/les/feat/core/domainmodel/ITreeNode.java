package br.pucrio.inf.les.feat.core.domainmodel;

public interface ITreeNode {
	String getPrintName();
	boolean hasChildren();
	ITreeNode getParent();
	ITreeNode[] getChildrens();
}
