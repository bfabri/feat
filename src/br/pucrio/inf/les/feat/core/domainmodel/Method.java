package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import br.pucrio.inf.les.feat.ui.util.LocationStyle;
import br.pucrio.inf.les.feat.ui.util.TypeInformationStyle;

/**
 * <p>
 * Classe filha a {@link Element} responsável por representar
 * um elemento específico de código. Representa um método.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class Method extends Element {

	/**
	 * <p>
	 * Classe do método.
	 * </p>
	 */
	private String methodClass;
	
	/**
	 * <p>
	 * Parâmetros do método.
	 * </p>
	 */
	private String[] parameters;
	
	/**
	 * <p>
	 * Flag que indica se o método é um método construtor.
	 * </p>
	 */
	private boolean constructor;
	
	/**
	 * <p>
	 * Tipo de retorno do método.
	 * </p>
	 */
	private String returnType;
	
	/**
	 * <p>
	 * Modificador de acesso do método.
	 * </p>
	 * @see {@link FeatModifier}
	 */
	private FeatModifier modifier;

	/**
	 * <p>
	 * Construtor de um método.
	 * </p>
	 * 
	 * @param name nome do método.
	 * @param elementPackage pacote do método.
	 * @param startLine linha de início do código do método.
	 * @param methodClass classe do método.
	 * @param parameters parâmetros do método.
	 * @param constructor flag indicando se o método é construtor.
	 * @param returnType tipo de retorno do método.
	 * @param modifier modificador de acesso do método.
	 */
	public Method(String name, String elementPackage, int startLine,
			String methodClass, String[] parameters, boolean constructor,
			String returnType, FeatModifier modifier) {
		super(name, elementPackage, startLine);
		this.methodClass = methodClass;
		this.parameters = parameters;
		this.constructor = constructor;
		this.returnType = returnType;
		this.modifier = modifier;
	}

	@Override
	public Image getImage() {
		Image resultImage;
		switch (modifier) {
		case PUBLIC:
			resultImage = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PUBLIC);
			break;
		case PRIVATE:
			resultImage = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PRIVATE);
			break;
		case PROTECTED:
			resultImage = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PROTECTED);
			break;
		default:
			resultImage = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_DEFAULT);
			break;
		}
		return resultImage;
	}

	@Override
	public StyledString getStyledLabel() {
		StyledString labelResult = new StyledString();
		labelResult.append(name);
		labelResult.append("(");
		for (int i = 0; i < parameters.length; i++) {
			labelResult.append(parameters[i]);
			if ((i + 1) < parameters.length) {
				labelResult.append(", ");
			}
		}
		labelResult.append(") ");
		if (!constructor) {
			labelResult.append(String.format(": %1s", this.returnType),
					new TypeInformationStyle());
		}
		labelResult.append(String.format(" [%1s.%2s] ", super.elementPackage,
				this.methodClass), new LocationStyle());
		return labelResult;
	}
}
