package br.pucrio.inf.les.feat.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Anotação que é usada para instrumentar o código do sistema
 * que será manipulado pelo plug-in.
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {
	String name();
	String description();
}
