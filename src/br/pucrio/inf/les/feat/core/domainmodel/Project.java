package br.pucrio.inf.les.feat.core.domainmodel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class Project implements ITreeNode {

	private final String name;
	private Set<Version> versions; 
	
	public Project(String name) {
		this.name = name;
		this.versions = new HashSet<Version>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Version[] getVersions() {
		return this.versions.toArray(new Version[this.versions.size()]);
	}
	
	public Version getLastVersion() {
		if (this.versions.size() > 0) {
			Iterator<Version> it = this.versions.iterator();
			Version lastVersion = it.next();
			while (it.hasNext()) {
				Version lastVersionCandidate = it.next();
				if (lastVersion.getDate().before(lastVersionCandidate.getDate())) {
					lastVersion = lastVersionCandidate;
				}
			}
			return lastVersion;
		}
		throw new UnsupportedOperationException("This project do not contain any versions yet.");
	}

	public boolean addVersion(Version version) {
		return this.versions.add(version);
	}
	
	public boolean removeVersion(Version version) {
		return this.versions.remove(version);
	}
	
	@Override
	public ITreeNode[] getChildrens() {
		return this.getVersions();
	}
	
	@Override
	public ITreeNode getParent() {
		return null;
	}
	
	@Override
	public String getPrintName() {
		return String.format("Project: %1$s", this.name);
	}
	
	@Override
	public boolean hasChildren() {
		return getVersions().length > 0;
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
