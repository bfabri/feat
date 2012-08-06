package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import br.pucrio.inf.les.feat.ui.util.LocationStyle;
import br.pucrio.inf.les.feat.ui.util.TypeInformationStyle;

/**
 * <p>
 * Classe filha a {@link Element} responsável por representar
 * um elemento específico de código. Um Field pode ser por exemplo
 * um atributo.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class Field extends Element {
	/**
	 * <p>
	 * Classe onde está presente.
	 * </p>
	 */
	private String fieldClass;
	
	/**
	 * <p>
	 * Tipo do campo.
	 * </p>
	 */
	private String fieldType;
	
	/**
	 * <p>
	 * Flag que indica se o campo é um enum constant.
	 * </p>
	 */
	private boolean enumConstant;
	
	/**
	 * <p>
	 * Modificador de acesso do campo.
	 * </p>
	 * 
	 * @see {@link FeatModifier}
	 */
	private FeatModifier modifier;

	/**
	 * <p>
	 * Construtor de um Field.
	 * </p>
	 * 
	 * @param name nome do campo.
	 * @param elementPackage pacote do campo.
	 * @param startLine linha de início do código.
	 * @param fieldClass classe do campo.
	 * @param fieldType tipo do campo.
	 * @param enumConstant flag que verifica se o campo é um enum constant.
	 * @param modifier modificador de acesso do campo.
	 */
	public Field(String name, String elementPackage, int startLine,
			String fieldClass, String fieldType, boolean enumConstant,
			FeatModifier modifier) {
		super(name, elementPackage, startLine);
		this.fieldClass = fieldClass;
		this.fieldType = fieldType;
		this.enumConstant = enumConstant;
		this.modifier = modifier;
	}

	@Override
	public Image getImage() {
		Image imageResult;
		switch (modifier) {
		case PUBLIC:
			imageResult = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_PUBLIC);
			break;
		case PRIVATE:
			imageResult = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_PRIVATE);
			break;
		case PROTECTED:
			imageResult = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_PROTECTED);
			break;
		default:
			imageResult = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_DEFAULT);
			break;
		}
		return imageResult;
	}

	@Override
	public StyledString getStyledLabel() {
		StyledString resultLabel = new StyledString();
		resultLabel.append(super.name);
		if (!this.enumConstant) {
			resultLabel.append(String.format(" : %1s ", this.fieldType),
					new TypeInformationStyle());
		}
		resultLabel.append(String.format(" [%1s.%2s] ", super.elementPackage,
				this.fieldClass), new LocationStyle());
		return resultLabel;
	}
}
