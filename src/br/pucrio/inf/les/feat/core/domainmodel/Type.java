package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import br.pucrio.inf.les.feat.ui.util.LocationStyle;

/**
 * <p>
 * Classe filha a {@link Element} responsável por representar
 * um elemento específico de código. Um Type pode ser uma classe,
 * interface ou enumerado.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class Type extends Element {
	/**
	 * <p>
	 * Representa o tipo (Classe, Interface ou Enumerado).
	 * </p>
	 * @see {@link FeatType}
	 */
	private FeatType type;

	/**
	 * <p>
	 * Construtor de um tipo.
	 * </p>
	 * 
	 * @param name nome do tipo.
	 * @param elementPackage pacote do tipo.
	 * @param startLine linha de início do código.
	 * @param type tipo (Classe, Interface ou Enum).
	 */
	public Type(String name, String elementPackage, int startLine, FeatType type) {
		super(name, elementPackage, startLine);
		this.type = type;
	}

	@Override
	public Image getImage() {
		Image result;
		switch (type) {
		case CLASS:
			result = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS);
			break;
		case ENUM:
			result = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_ENUM);
			break;
		default:
			result = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_INTERFACE);
			break;
		}
		return result;
	}

	@Override
	public StyledString getStyledLabel() {
		StyledString styledLabel = new StyledString();
		styledLabel.append(super.name);
		styledLabel.append(
				String.format(" [%1s.%2s] ", super.elementPackage, super.name),
				new LocationStyle());
		return styledLabel;
	}
}
