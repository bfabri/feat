package br.pucrio.inf.les.feate.core.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.pucrio.inf.les.feat.FeatActivator;
import br.pucrio.inf.les.feat.FeatLog;
import br.pucrio.inf.les.feat.core.domainmodel.Project;
import br.pucrio.inf.les.feat.core.domainmodel.Version;

import com.thoughtworks.xstream.XStream;

public class ProjectRepository {

	private List<Project> projects;
	private List<ProjectChangeListener> listeners;
	private static ProjectRepository repository;
	
	private ProjectRepository() {
		listeners = new ArrayList<ProjectChangeListener>();
		projects = new ArrayList<Project>();
	};
	
	public static ProjectRepository getProjectRepository() {
		if (repository == null) {
			repository = new ProjectRepository();
		}
		return repository;
	}
	
	public Project[] findAll() {
		return projects.toArray(new Project[projects.size()]);
	}
	
	@SuppressWarnings("unchecked")
	public void loadAll() {
		projects = new ArrayList<Project>();
		FileReader reader = null;
		try {
			reader = new FileReader(getFeatFile());
			XStream xstream = new XStream();
			projects = (List<Project>) xstream.fromXML(reader);
		} catch (FileNotFoundException e) {
			FeatLog.logInfo("Arquivo não existe");
		} catch (Exception e) {
			FeatLog.logError("Problema no carregamento dos projetos", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				FeatLog.logError("Problema ao fechar o arquivo de carregamento dos projetos", e);
			}
		}
	}
	
	public void saveAll() {
		if (projects == null) {
			return;
		}

		XStream xstream = new XStream();
		xstream.alias("Projects", ArrayList.class);

		FileWriter writer = null;
		try {
			writer = new FileWriter(getFeatFile());
			xstream.toXML(projects, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void delete(Project project) {
		
		//Fire event
	}
	
	public void delete(Project[] projects) {
		Set<Project> projectsRemoved = new HashSet<Project>(projects.length);
		for (Project project : projects) {
			if (this.projects.remove(project)) {
				projectsRemoved.add(project);
			}
		}
		fireProjectChanged(new Project[0], projectsRemoved.toArray(new Project[projectsRemoved.size()]));
	}
	
	public Project find(Project project) {
		for (Project projectAux : projects) {
			if (projectAux.equals(project)) {
				return project;
			}
		}
		throw new IllegalArgumentException("Não existe nenhum projeto com esse nome!");
	}
	
	public void insert(Project[] projects) {
		Set<Project> projectsAdded = new HashSet<Project>(projects.length);
		for (Project project : projects) {
			Project result = project;
			if (exists(project)) {
				Version lastVersion = project.getLastVersion();
				result = find(project);
				result.addVersion(lastVersion);
			}
			else {
				this.projects.add(project);
			}
			projectsAdded.add(result);
		}
		fireProjectChanged(projectsAdded.toArray(new Project[projectsAdded.size()]), new Project[0]);
	}
	
	public boolean exists(Project project) {
		return projects.contains(project);
	}

	public void addProjectChangeListener(ProjectChangeListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void removerProjectChangeListener(ProjectChangeListener listener) {
		listeners.remove(listener);
	}

	private void fireProjectChanged(Project[] itemsAdded, Project[] itemsRemoved) {
		ProjectChangeEvent event = new ProjectChangeEvent(this, itemsAdded, itemsRemoved);
		for (ProjectChangeListener listener : listeners) {
			listener.projectChanged(event);
		}
	}

	private File getFeatFile() {
		return FeatActivator.getDefault().getStateLocation().append("feat.xml").toFile();
	}
}
