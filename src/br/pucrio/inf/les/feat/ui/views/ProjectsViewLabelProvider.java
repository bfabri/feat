package br.pucrio.inf.les.feat.ui.views;

import org.eclipse.jface.viewers.LabelProvider;

import br.pucrio.inf.les.feat.core.domainmodel.ITreeNode;

public class ProjectsViewLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		ITreeNode node = (ITreeNode) element;
		return node.getPrintName();
	}
}
