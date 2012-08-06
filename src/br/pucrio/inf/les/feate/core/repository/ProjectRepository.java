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

/**
 * <p>
 * Classe respons�vel por efetuar uma fachada para a base de dados.
 * Prov� m�todos de acesso a essa base de dados.
 * Implementa o padr�o de projeto Singleton.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class ProjectRepository {

	/**
	 * <p>
	 * Projetos do reposit�rio.
	 * </p>
	 */
	private List<Project> projects;
	
	/**
	 * <p>
	 * Listeners interessados em altera��es no reposit�rio.
	 * </p>
	 */
	private List<ProjectChangeListener> listeners;
	
	/**
	 * <p>
	 * Inst�ncia do Singleton.
	 * </p>
	 */
	private static ProjectRepository singleton;
	
	private ProjectRepository() {
		listeners = new ArrayList<ProjectChangeListener>();
		projects = new ArrayList<Project>();
	};
	
	/**
	 * <p>
	 * Obt�m a inst�ncia do Singleton.
	 * </p>
	 * 
	 * @return inst�ncia do Singleton.
	 */
	public static ProjectRepository getProjectRepository() {
		if (singleton == null) {
			singleton = new ProjectRepository();
		}
		return singleton;
	}
	
	/**
	 * <p>
	 * Busca todos os projetos do reposit�rio.
	 * </p>
	 * 
	 * @return todos os projetos do reposit�rio.
	 */
	public Project[] findAll() {
		return projects.toArray(new Project[projects.size()]);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * <p>
	 * Carrega todos os projetos da base de dados para a mem�ria.
	 * </p>
	 */
	public void loadAll() {
		projects = new ArrayList<Project>();
		FileReader reader = null;
		try {
			reader = new FileReader(getFeatFile());
			XStream xstream = new XStream();
			projects = (List<Project>) xstream.fromXML(reader);
		} catch (FileNotFoundException e) {
			FeatLog.logInfo("Arquivo n�o existe");
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
	
	/**
	 * <p>
	 * Salva todos os projetos da mem�ria na base de dados.
	 * </p>
	 */
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
	
	/**
	 * <p>
	 * Deleta uma lista de projetos.
	 * </p>
	 * 
	 * @param projects projetos a serem deletados.
	 */
	public void delete(Project[] projects) {
		Set<Project> projectsRemoved = new HashSet<Project>(projects.length);
		for (Project project : projects) {
			if (this.projects.remove(project)) {
				projectsRemoved.add(project);
			}
		}
		fireProjectChanged(new Project[0], projectsRemoved.toArray(new Project[projectsRemoved.size()]));
	}
	
	/**
	 * <p>
	 * Acha um projeto.
	 * </p>
	 * 
	 * @param project projeto a ser procurado.
	 * @return projeto procurado.
	 */
	public Project find(Project project) {
		for (Project projectAux : projects) {
			if (projectAux.equals(project)) {
				return projectAux;
			}
		}
		throw new IllegalArgumentException("N�o existe nenhum projeto com esse nome!");
	}
	
	/**
	 * <p>
	 * Insere uma lista de projetos.
	 * </p>
	 * 
	 * @param projects projetos a serem inseridos.
	 */
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
	
	/**
	 * <p>
	 * Verifica a exist�ncia de um projeto.
	 * </p>
	 * 
	 * @param project projeto que se deseja verificar a exist�ncia.
	 * @return true se existe e false se n�o existe.
	 */
	public boolean exists(Project project) {
		return projects.contains(project);
	}

	/**
	 * <p>
	 * Adiciona os listener interessados em altera��es no reposit�rio.
	 * </p>
	 * 
	 * @param listener listener interessados em altera��es no reposit�rio.
	 */
	public void addProjectChangeListener(ProjectChangeListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * <p>
	 * Remove os listener interessados em altera��es no reposit�rio.
	 * </p>
	 * 
	 * @param listener listener interessados em altera��es no reposit�rio.
	 */
	public void removerProjectChangeListener(ProjectChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * <p>
	 * Dispara informa��es para os listeners que houve altera��o no reposit�rio.
	 * </p>
	 * 
	 * @param projectsAdded projetos adicionados.
	 * @param projectsRemoved projetos removidos.
	 */
	private void fireProjectChanged(Project[] projectsAdded, Project[] projectsRemoved) {
		ProjectChangeEvent event = new ProjectChangeEvent(this, projectsAdded, projectsRemoved);
		for (ProjectChangeListener listener : listeners) {
			listener.projectChanged(event);
		}
	}

	/**
	 * <p>
	 * Obt�m a base de dados.
	 * </p>
	 * 
	 * @return arquivo xml que representa a base de dados.
	 */
	private File getFeatFile() {
		return FeatActivator.getDefault().getStateLocation().append("feat.xml").toFile();
	}
}
