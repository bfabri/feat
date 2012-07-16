package br.pucrio.inf.les.feat.core.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.pucrio.inf.les.feat.FeatActivator;
import br.pucrio.inf.les.feat.FeatLog;

import com.thoughtworks.xstream.XStream;

public class FeatManager {

	private static FeatManager manager;
	
	private List<Project> projects;
	private List<FeatManagerListener> listeners;

	private FeatManager() {
		listeners = new ArrayList<FeatManagerListener>();
		projects = new ArrayList<Project>();
	}

	public static FeatManager getManager() {
		if (manager == null) {
			manager = new FeatManager();
		}
		return manager;
	}

	public Project[] getProjects() {
		if (projects == null) {
			loadProjects();
		}
		return projects.toArray(new Project[projects.size()]);
	}

	public void addProjects(Project[] projectsAdded) {
		for (Project project : projectsAdded) {
			if (projects.contains(project)) {
				projects.remove(project);
			}
			projects.add(project);
		}
		fireFeatChanged(projectsAdded, new Project[]{});
	}
	
	public void removeProjects(Project[] projectsRemoved) {
		for (Project project : projectsRemoved) {
			if (projects.contains(project)) {
				projects.remove(project);
			}
		}
		fireFeatChanged(new Project[]{}, projectsRemoved);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void loadProjects() {
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
	
	public void saveProjects() {
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

	public void addFeatManagerListener(FeatManagerListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void removeFeatManagerListener(FeatManagerListener listener) {
		listeners.remove(listener);
	}

	private void fireFeatChanged(Project[] itemsAdded, Project[] itemsRemoved) {
		FeatManagerEvent event = new FeatManagerEvent(this, itemsAdded, itemsRemoved);
		for (FeatManagerListener listener : listeners) {
			listener.featChanged(event);
		}
	}

	private File getFeatFile() {
		return FeatActivator.getDefault().getStateLocation().append("feat.xml")
				.toFile();
	}

}
