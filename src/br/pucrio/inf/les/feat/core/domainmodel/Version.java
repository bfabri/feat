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
 * Classe respons�vel por representar uma vers�o de um projeto.
 * Guarda informa��es relevantes da vers�o para serem
 * exibidas para o usu�rio final do plug-in.
 * </p>
 * 
 * @author Bruno F�bri
 * @version 1.0
 */
public class Version implements ITreeContentNode, IStyledLabel {

	/**
	 * <p>
	 * Nome da vers�o.
	 * </p>
	 */
	private final String version;
	
	/**
	 * <p>
	 * Data que foi gerada a vers�o.
	 * </p>
	 */
	private final Date date;
	
	/**
	 * <p>
	 * Projeto ao qual pertence a vers�o.
	 * </p>
	 */
	private final Project project;
	
	/**
	 * <p>
	 * Conjunto de features que a vers�o possui.
	 * </p>
	 */
	private Set<Feature> features;
	
	/**
	 * <p>
	 * Construtor da vers�o.
	 * </p>
	 * 
	 * @param project projeto da vers�o.
	 * @param version nome da vers�o.
	 * @param date data que foi gerada a vers�o.
	 */
	public Version(Project project, String version, Date date) {
		this.project = project;
		this.version = version;
		this.date = date;
		this.features = new HashSet<Feature>();
	}
	
	/**
	 * <p>
	 * Obt�m o nome da vers�o.
	 * </p>
	 * 
	 * @return nome da vers�o.
	 */
	public String getVersion() {
		return this.version;
	}
	
	/**
	 * <p>
	 * Obt�m a data da vers�o com
	 * a formata��o dd/MM/yyyy.
	 * </p>
	 * 
	 * @return data da vers�o formatada.
	 */
	public String getFormattedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(this.date);
	}

	/**
	 * <p>
	 * Obt�m a data da vers�o no
	 * formato do objeto Calendar.
	 * </p>
	 * 
	 * @return data da vers�o no formato de um Calendar.
	 */
	public Calendar getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		return calendar;
	}
	
	/**
	 * <p>
	 * Obt�m o projeto da vers�o.
	 * </p>
	 * 
	 * @return projeto da vers�o.
	 */
	public Project getProject() {
		return this.project;
	}
	
	/**
	 * <p>
	 * Obt�m as features de uma
	 * vers�o.
	 * </p>
	 * 
	 * @return features de uma vers�o.
	 */
	public Feature[] getFeatures() {
		return this.features.toArray(new Feature[this.features.size()]);
	}
	
	/**
	 * <p>
	 * Adiciona uma feature na vers�o.
	 * </p>
	 * 
	 * @param feature feature que vai ser adicionada.
	 * @return true se adicionou a feature e false se n�o adicionou a feature.
	 */
	public boolean addFeature(Feature feature) {
		return this.features.add(feature);
	}
	
	/**
	 * <p>
	 * Remove uma feature de uma vers�o.
	 * </p>
	 * 
	 * @param feature feature que ser� removida.
	 * @return true se removeu a feature e false se n�o removeu a feature.
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
	 * M�todo respons�vel por informar se duas vers�es s�o
	 * iguais. Leva em considera��o o nome da vers�o
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
