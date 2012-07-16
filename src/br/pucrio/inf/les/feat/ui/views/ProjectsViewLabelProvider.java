package br.pucrio.inf.les.feat.ui.views;

import org.eclipse.jface.viewers.LabelProvider;

import br.pucrio.inf.les.feat.core.model.Feature;
import br.pucrio.inf.les.feat.core.model.Project;
import br.pucrio.inf.les.feat.core.model.Version;

public class ProjectsViewLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Project) {
			Project p = (Project) element;
			return p.getName();
		}
		else if (element instanceof Version) {
			Version v = (Version) element;
			return v.getVersion() + " - " + v.getFormattedDate();
		}
		else if (element instanceof Feature) {
			Feature f = (Feature) element;
			return f.getName() + " - " + f.getDescription();
		}
		return "";
	}
}
