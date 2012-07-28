package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jdt.core.dom.Modifier;

public enum FeatModifier {

	DEFAULT,
	PUBLIC,
	PRIVATE,
	PROTECTED;
	
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
