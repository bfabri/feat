package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import br.pucrio.inf.les.feat.ui.util.LocationStyle;
import br.pucrio.inf.les.feat.ui.util.TypeInformationStyle;

/**
 * <p>
 * Classe filha a {@link Element} respons�vel por representar
 * um elemento espec�fico de c�digo. Representa um m�todo.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class Method extends Element {

	/**
	 * <p>
	 * Classe do m�todo.
	 * </p>
	 */
	private String methodClass;
	
	/**
	 * <p>
	 * Par�metros do m�todo.
	 * </p>
	 */
	private String[] parameters;
	
	/**
	 * <p>
	 * Flag que indica se o m�todo � um m�todo construtor.
	 * </p>
	 */
	private boolean constructor;
	
	/**
	 * <p>
	 * Tipo de retorno do m�todo.
	 * </p>
	 */
	private String returnType;
	
	/**
	 * <p>
	 * Modificador de acesso do m�todo.
	 * </p>
	 * @see {@link FeatModifier}
	 */
	private FeatModifier modifier;

	/**
	 * <p>
	 * Construtor de um m�todo.
	 * </p>
	 * 
	 * @param name nome do m�todo.
	 * @param elementPackage pacote do m�todo.
	 * @param startLine linha de in�cio do c�digo do m�todo.
	 * @param methodClass classe do m�todo.
	 * @param parameters par�metros do m�todo.
	 * @param constructor flag indicando se o m�todo � construtor.
	 * @param returnType tipo de retorno do m�todo.
	 * @param modifier modificador de acesso do m�todo.
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
