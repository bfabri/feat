package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * Responsável por apresentar um elemento gráficamente.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public interface IStyledLabel {
	static final ISharedImages SHARED_IMAGES = PlatformUI.getWorkbench().getSharedImages();
	
	/**
	 * <p>
	 * Obtêm o texto estilizado que representa um elemento específico.
	 * </p>
	 * 
	 * @return texto estilizado que representa um elemento específico.
	 */
	StyledString getStyledLabel();
	
	/**
	 * <p>
	 * Obtêm a imagem que representa um elemento específico.
	 * </p>
	 * 
	 * @return imagem que representa um elemento específico.
	 */
	Image getImage();
}
