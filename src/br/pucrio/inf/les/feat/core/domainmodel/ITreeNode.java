package br.pucrio.inf.les.feat.core.domainmodel;

public interface ITreeNode {
	String getPrintName();
	boolean hasParent();
	ITreeNode getParent();
	ITreeNode[] getChildrens();
}
