package br.pucrio.inf.les.feat.core.domainmodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	private Set<Feature> features;
	
	public Version(Project project, String version, Date date) {
		this.project = project;
		this.version = version;
		this.date = date;
		this.features = new HashSet<Feature>();
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public String getFormattedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(this.date);
	}

	public Calendar getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		return calendar;
	}
	
	public Project getProject() {
		return this.project;
	}
	
	public Feature[] getFeatures() {
		return this.features.toArray(new Feature[this.features.size()]);
	}
	
	public boolean addFeature(Feature feature) {
		return this.features.add(feature);
	}
	
	public boolean removeFeature(Feature feature) {
		return this.features.remove(feature);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
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
