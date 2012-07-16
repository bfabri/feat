package br.pucrio.inf.les.feat.core.model;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class Project {

	private final String name;
	private List<Version> versions; 
	
	public Project(String name) {
		this.name = name;
		this.versions = new ArrayList<Version>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Version[] getVersions() {
		return versions.toArray(new Version[versions.size()]);
	}
	
	public void addVersion(Version version) {
		if (!versions.contains(version)) {
			this.versions.add(version);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Project) {
				Project other = (Project) obj;
				if (this.name != null && other.name != null) {
					return this.name.equals(other.name);
				}
			}
		}
		return false;
	}
}
