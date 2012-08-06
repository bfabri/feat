package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jdt.core.dom.Modifier;

/**
 * <p>
 * Representa os possíveis modificadores de acesso.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public enum FeatModifier {

	DEFAULT,
	PUBLIC,
	PRIVATE,
	PROTECTED;
	
	/**
	 * <p>
	 * Obtêm o modificador de acesso baseado em uma flag.
	 * </p>
	 * 
	 * @param flag inteiro que representa o modificador de acesso de um elemento.
	 * @return modificador de acesso.
	 */
	public static FeatModifier getModifier(int flag) {
		if (Modifier.isPrivate(flag)) {
			return PRIVATE;
		}
		else if (Modifier.isPublic(flag)) {
			return PUBLIC;
		}
		else if (Modifier.isProtected(flag)) {
			return PROTECTED;
		}
		return DEFAULT;
	}
}
