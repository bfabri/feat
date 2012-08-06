package br.pucrio.inf.les.feat.core.domainmodel;

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
 * Classe respons�vel por representar uma feature de um projeto em uma vers�o.
 * Guarda informa��es relevantes da feature para serem
 * exibidas para o usu�rio final do plug-in.
 * </p>
 * 
 * @author Bruno F�bri
 * @version 1.0
 */
public class Feature implements ITreeContentNode, IStyledLabel {
	
	/**
	 * <p>
	 * Nome da feature.
	 * </p>
	 */
	private final String name;
	
	/**
	 * <p>
	 * Descri��o da feature.
	 * </p>
	 */
	private final String description;
	
	/**
	 * <p>
	 * Vers�o da qual pertence a feature.
	 * </p>
	 */
	private final Version version;
	
	/**
	 * <p>
	 * Conjunto de elementos que implementam
	 * a feature.
	 * </p>
	 */
	private Set<Element> elements;
	
	/**
	 * <p>
	 * Construtor da feature.
	 * </p>
	 * 
	 * @param version vers�o que a feature pertence.
	 * @param name nome da feature.
	 * @param description descri��o da feature.
	 */
	public Feature(Version version, String name, String description) {
		this.version = version;
		this.name = name;
		this.description = description;
		this.elements = new HashSet<Element>();
	}
	
	/**
	 * <p>
	 * Obt�m o nome da feature.
	 * </p>
	 * 
	 * @return nome da feature.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * <p>
	 * Obt�m a descri��o da feature.
	 * </p>
	 * 
	 * @return descri��o da feature.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * <p>
	 * Obt�m a vers�o da qual pertence a feature.
	 * </p>
	 * 
	 * @return vers�o da qual pertence a feature.
	 */
	public Version getVersion() {
		return this.version;
	}
	
	/**
	 * <p>
	 * Obt�m os elementos que implementam a feature.
	 * </p>
	 * 
	 * @return elementos que implementam a feature.
	 */
	public Element[] getElements() {
		return this.elements.toArray(new Element[this.elements.size()]);
	}
	
	/**
	 * <p>
	 * Adiciona um elemento a uma feature.
	 * </p>
	 * 
	 * @param element elemento que implementa uma feature.
	 * @return true se o elemento for adicionado e false se o elemento n�o for adicionado.
	 */
	public boolean addElement(Element element) {
		return this.elements.add(element);
	}
	
	/**
	 * <p>
	 * Remove um elemento de uma feature.
	 * </p>
	 * 
	 * @param element elemento que implementa uma feature.
	 * @return true se o elemento for removido e false se o elemento n�o for removido.
	 */
	public boolean removeElement(Element element) {
		return this.elements.remove(element);
	}
	
	@Override
	public boolean hasChildren() {
		return getElements().length > 0;
	}
	
	@Override
	public ITreeContentNode[] getChildrens() {
		return getElements();
	}
	
	@Override
	public ITreeContentNode getParent() {
		return this.version;
	}
	
	@Override
	public Image getImage() {
		return SHARED_IMAGES.getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
	
	@Override
	public StyledString getStyledLabel() {
		StyledString styledLabel = new StyledString();
		styledLabel.append(this.name);
		styledLabel.append(String.format(" [%1s] ", this.description), new StyledString.Styler() {
			
			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA);
			}
		});
		styledLabel.append(String.format(" (%1s) ", this.elements.size()), StyledString.COUNTER_STYLER);
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
	/**
	 * <p>
	 * M�todo respons�vel por informar se duas features s�o
	 * iguais. Leva em considera��o o nome da feature
	 * para fazer a igualdade.
	 * </p>
	 */
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Feature) {
				Feature other = (Feature) obj;
				if (this.name != null && other.name != null) {
					return this.name.equals(other.name);
				}
			}
		}
		return false;
	}
}
