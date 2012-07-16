package br.pucrio.inf.les.feat.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class Version {

	private final String version;
	private final Date date;
	
	private final Project project;
	private List<Feature> features;
	
	public Version(Project project, String version, Date date) {
		this.project = project;
		this.version = version;
		this.date = date;
		this.features = new ArrayList<Feature>();
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public String getFormattedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(this.date);
	}

	public Project getProject() {
		return this.project;
	}
	
	public Feature[] getFeatures() {
		return features.toArray(new Feature[features.size()]);
	}
	
	public void addFeature(Feature feature) {
		if (!features.contains(feature)) {
			this.features.add(feature);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Version) {
				Version other = (Version) obj;
				if (this.version != null && other.version != null) {
					return this.version.equals(other.version);
				}
			}
		}
		return false;
	}
}
