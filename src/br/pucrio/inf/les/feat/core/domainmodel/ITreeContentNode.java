package br.pucrio.inf.les.feat.core.domainmodel;

public interface ITreeContentNode {
	boolean hasChildren();
	ITreeContentNode getParent();
	ITreeContentNode[] getChildrens();
}
