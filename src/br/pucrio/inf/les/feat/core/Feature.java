package br.pucrio.inf.les.feat.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Anota��o que � usada para instrumentar o c�digo do sistema
 * que ser� manipulado pelo plug-in.
 * </p>
 * 
 * @author Bruno F�bri
 * @version 1.0
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {
	String name();
	String description();
}
