package br.pucrio.inf.les.feat.core.modelgenerator;

/**
 * <p>
 * Classe respons�vel por mapear exe��es que podem ocorrer
 * no m�dulo de gera��o.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class ProjectGeneratorException extends Exception {

	private static final long serialVersionUID = 1L;

	ProjectGeneratorException() {
		super();
	}
	
	ProjectGeneratorException(String message) {
		super(message);
	}
	
	ProjectGeneratorException(Throwable throwable) {
		super(throwable);
	}

	ProjectGeneratorException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
