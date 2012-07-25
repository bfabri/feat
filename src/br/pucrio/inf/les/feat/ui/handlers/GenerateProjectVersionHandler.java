package br.pucrio.inf.les.feat.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import br.pucrio.inf.les.feat.core.domainmodel.Project;
import br.pucrio.inf.les.feat.core.modelgenerator.IProjectGeneratorFactory;
import br.pucrio.inf.les.feat.core.modelgenerator.ProjectGeneratorException;
import br.pucrio.inf.les.feat.ui.views.ProjectVersionDialog;
import br.pucrio.inf.les.feate.core.repository.ProjectRepository;

public class GenerateProjectVersionHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActivePart(event).getSite().getShell();
		ProjectVersionDialog dialog = new ProjectVersionDialog(shell);
		dialog.create();
		if (dialog.open() == Window.OK) {
			String version = dialog.getProjectVersion();
			IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
			IJavaProject javaProject = (IJavaProject)selection.getFirstElement();
			IProject project = javaProject.getProject();
		    
			try {
				Project p = IProjectGeneratorFactory.createProjectGeneratorFor(project).generate(project, version);
				ProjectRepository.getProjectRepository().insert(new Project[]{p});
			} catch (ProjectGeneratorException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
