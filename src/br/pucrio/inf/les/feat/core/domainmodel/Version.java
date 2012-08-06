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
 * Classe responsável por representar uma versão de um projeto.
 * Guarda informações relevantes da versão para serem
 * exibidas para o usuário final do plug-in.
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class Version implements ITreeContentNode, IStyledLabel {

	/**
	 * <p>
	 * Nome da versão.
	 * </p>
	 */
	private final String version;
	
	/**
	 * <p>
	 * Data que foi gerada a versão.
	 * </p>
	 */
	private final Date date;
	
	/**
	 * <p>
	 * Projeto ao qual pertence a versão.
	 * </p>
	 */
	private final Project project;
	
	/**
	 * <p>
	 * Conjunto de features que a versão possui.
	 * </p>
	 */
	private Set<Feature> features;
	
	/**
	 * <p>
	 * Construtor da versão.
	 * </p>
	 * 
	 * @param project projeto da versão.
	 * @param version nome da versão.
	 * @param date data que foi gerada a versão.
	 */
	public Version(Project project, String version, Date date) {
		this.project = project;
		this.version = version;
		this.date = date;
		this.features = new HashSet<Feature>();
	}
	
	/**
	 * <p>
	 * Obtêm o nome da versão.
	 * </p>
	 * 
	 * @return nome da versão.
	 */
	public String getVersion() {
		return this.version;
	}
	
	/**
	 * <p>
	 * Obtêm a data da versão com
	 * a formatação dd/MM/yyyy.
	 * </p>
	 * 
	 * @return data da versão formatada.
	 */
	public String getFormattedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(this.date);
	}

	/**
	 * <p>
	 * Obtêm a data da versão no
	 * formato do objeto Calendar.
	 * </p>
	 * 
	 * @return data da versão no formato de um Calendar.
	 */
	public Calendar getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		return calendar;
	}
	
	/**
	 * <p>
	 * Obtêm o projeto da versão.
	 * </p>
	 * 
	 * @return projeto da versão.
	 */
	public Project getProject() {
		return this.project;
	}
	
	/**
	 * <p>
	 * Obtêm as features de uma
	 * versão.
	 * </p>
	 * 
	 * @return features de uma versão.
	 */
	public Feature[] getFeatures() {
		return this.features.toArray(new Feature[this.features.size()]);
	}
	
	/**
	 * <p>
	 * Adiciona uma feature na versão.
	 * </p>
	 * 
	 * @param feature feature que vai ser adicionada.
	 * @return true se adicionou a feature e false se não adicionou a feature.
	 */
	public boolean addFeature(Feature feature) {
		return this.features.add(feature);
	}
	
	/**
	 * <p>
	 * Remove uma feature de uma versão.
	 * </p>
	 * 
	 * @param feature feature que será removida.
	 * @return true se removeu a feature e false se não removeu a feature.
	 */
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
	/**
	 * <p>
	 * Método responsável por informar se duas versões são
	 * iguais. Leva em consideração o nome da versão
	 * para fazer a igualdade.
	 * </p>
	 */
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
