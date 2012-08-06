package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * Respons�vel por apresentar um elemento gr�ficamente.
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
	 * Obt�m o texto estilizado que representa um elemento espec�fico.
	 * </p>
	 * 
	 * @return texto estilizado que representa um elemento espec�fico.
	 */
	StyledString getStyledLabel();
	
	/**
	 * <p>
	 * Obt�m a imagem que representa um elemento espec�fico.
	 * </p>
	 * 
	 * @return imagem que representa um elemento espec�fico.
	 */
	Image getImage();
}
