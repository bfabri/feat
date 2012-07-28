package br.pucrio.inf.les.feat.ui.views;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

import br.pucrio.inf.les.feat.core.domainmodel.IStyledLabel;

public class ProjectsViewLabelProvider extends StyledCellLabelProvider {

	@Override
	public void update(ViewerCell cell) {
		IStyledLabel node = (IStyledLabel) cell.getElement();
		StyledString text = node.getStyledLabel();
		cell.setImage(node.getImage());
		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);
	}
}
