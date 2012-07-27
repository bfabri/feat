package br.pucrio.inf.les.feat.ui.views;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import br.pucrio.inf.les.feat.core.domainmodel.Feature;
import br.pucrio.inf.les.feat.core.domainmodel.ITreeNode;
import br.pucrio.inf.les.feat.core.domainmodel.Project;
import br.pucrio.inf.les.feat.core.domainmodel.Version;
import br.pucrio.inf.les.feate.core.repository.ProjectChangeEvent;
import br.pucrio.inf.les.feate.core.repository.ProjectChangeListener;
import br.pucrio.inf.les.feate.core.repository.ProjectRepository;

public class ProjectsViewContentProvider implements ITreeContentProvider,
		ProjectChangeListener {

	private TreeViewer viewer;
	private ProjectRepository repository;
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer) viewer;
		if (repository != null) {
			repository.removerProjectChangeListener(this);
		}
		repository = (ProjectRepository) newInput;
		if (repository != null) {
			repository.addProjectChangeListener(this);
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return repository.findAll();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		ITreeNode node = (ITreeNode) parentElement;
		return node.getChildrens();
	}

	@Override
	public Object getParent(Object element) {
		ITreeNode node = (ITreeNode) element;
		return node.getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		ITreeNode node = (ITreeNode) element;
		return node.hasChildren();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void projectChanged(ProjectChangeEvent event) {
		viewer.getTree().setRedraw(false);
		try {
			viewer.remove(event.getItemsRemoved());
			for (Project project : event.getItemsAdded()) {
				viewer.add(project, project.getVersions());
				for (Version version : project.getVersions()) {
					viewer.add(version, version.getFeatures());
					for (Feature feature : version.getFeatures()) {
						viewer.add(feature, feature.getElements());
					}
				}
			}
		} finally {
			viewer.getTree().setRedraw(true);
		}
	}
}
