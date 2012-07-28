package br.pucrio.inf.les.feat.core.domainmodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;

/**
 * <p>
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class Version implements ITreeContentNode, IStyledLabel {

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
	public boolean hasChildren() {
		return getFeatures().length > 0;
	}
	
	@Override
	public ITreeContentNode[] getChildrens() {
		return getFeatures();
	}
	
	@Override
	public ITreeContentNode getParent() {
		return this.project;
	}
	
	@Override
	public Image getImage() {
		return SHARED_IMAGES.getImage(ISharedImages.IMG_OBJ_ADD);
	}
	
	@Override
	public StyledString getStyledLabel() {
		StyledString styledLabel = new StyledString();
		styledLabel.append(this.version);
		styledLabel.append(String.format(" [%1s] ", getFormattedDate()), new StyledString.Styler() {
			
			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA);
			}
		});
		styledLabel.append(String.format(" (%1s) ", this.features.size()), StyledString.COUNTER_STYLER);
		return styledLabel;
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
