package br.pucrio.inf.les.feat.core.domainmodel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ide.IDE;

/**
 * <p>
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class Project implements ITreeContentNode, IStyledLabel {

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
	public boolean hasChildren() {
		return getVersions().length > 0;
	}
	
	@Override
	public ITreeContentNode[] getChildrens() {
		return getVersions();
	}
	
	@Override
	public ITreeContentNode getParent() {
		return null;
	}
	
	@Override
	public Image getImage() {
		return SHARED_IMAGES.getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
	}
	
	@Override
	public StyledString getStyledLabel() {
		StyledString styledLabel = new StyledString();
		styledLabel.append(this.name);
		styledLabel.append(String.format(" (%1$s) ", this.versions.size()), StyledString.COUNTER_STYLER);
		return styledLabel;
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
