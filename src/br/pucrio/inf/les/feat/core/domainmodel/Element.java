package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * Classe responsável por representar um elemento de código
 * que implementa uma feature de uma versão de um projeto.
 * Guarda informações básicas de um elemento de código.
 * Informações específicas de cada elemento devem ser guardadas
 * em classes filhas.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public abstract class Element implements ITreeContentNode, IStyledLabel {
	/**
	 * <p>
	 * Nome do elemento.
	 * </p>
	 */
	protected final String name;
	
	/**
	 * <p>
	 * Pacote do elemento.
	 * </p>
	 */
	protected final String elementPackage;
	
	/**
	 * <p>
	 * Linha de código inicial do elemento.
	 * </p>
	 */
	protected final int startLine;
	
	/**
	 * <p>
	 * Feature que o elemento implementa.
	 * </p>
	 */
	protected Feature feature;

	/**
	 * <p>
	 * Construtor de um elemento de código.
	 * </p>
	 * 
	 * @param name nome do elemento.
	 * @param elementPackage pacote do elemento.
	 * @param startLine linha de início do código do elemento.
	 */
	protected Element(String name, String elementPackage, int startLine) {
		this.name = name;
		this.elementPackage = elementPackage;
		this.startLine = startLine;
	}
	
	public abstract Image getImage();
	public abstract StyledString getStyledLabel();
	
	/**
	 * <p>
	 * Atribui a feature que o elemento implementa.
	 * </p>
	 * @param feature
	 */
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
	
	@Override
	public ITreeContentNode[] getChildrens() {
		return null;
	}
	
	@Override
	public ITreeContentNode getParent() {
		return this.feature;
	}
}
