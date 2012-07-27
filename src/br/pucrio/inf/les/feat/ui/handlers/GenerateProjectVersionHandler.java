package br.pucrio.inf.les.feat.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import br.pucrio.inf.les.feat.core.domainmodel.Project;
import br.pucrio.inf.les.feat.core.modelgenerator.IProjectGeneratorFactory;
import br.pucrio.inf.les.feat.core.modelgenerator.ProjectGeneratorException;
import br.pucrio.inf.les.feat.ui.views.ProjectsView;
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
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final IJavaProject javaProject = (IJavaProject)selection.getFirstElement();
		final IProject project = javaProject.getProject();
		
		Shell shell = HandlerUtil.getActivePart(event).getSite().getShell();
		InputDialog dialog = new InputDialog(shell, "Which version you want to generate?", "Project version:", null, new IInputValidator() {
			@Override
			public String isValid(String newText) {
				String erro = null;
				if (newText.isEmpty()) {
					erro = "Please inform the project version.";
				}
				else {
					Project aux = new Project(project.getName());
					if (ProjectRepository.getProjectRepository().exists(aux)) {
						Project project = ProjectRepository.getProjectRepository().find(aux);
						if (project.getLastVersion().getVersion().equals(newText)) {
							erro = "This project already contain this version, please try a different one.";
						}
					}
				}
				return erro;
			}
		});
		
		dialog.create();
		if (dialog.open() == Window.OK) {
			final String version = dialog.getValue();
			IRunnableContext context = new ProgressMonitorDialog(shell);
			try {
				context.run(false, true, new IRunnableWithProgress() {
					
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						try {
							Project p = IProjectGeneratorFactory.createProjectGeneratorFor(project).generate(project, version);
							ProjectRepository.getProjectRepository().insert(new Project[]{p});
						} catch (ProjectGeneratorException e) {
							monitor.setCanceled(true);
						}
					}
				});
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(ProjectsView.ID);
			} catch (PartInitException e) {
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
