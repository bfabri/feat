package br.pucrio.inf.les.feat.core.modelgenerator;

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
