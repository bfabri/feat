package br.pucrio.inf.les.feat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * Classe responsável por gravar informações
 * de log do sistema.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public class FeatLog {
	
	/**
	 * <p>
	 * Método responsável por gravar uma mensagem
	 * de informação no arquivo de log do sistema.
	 * </p>
	 * 
	 * @param message texto da mensagem que será gravada no log.
	 */
	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	/**
	 * <p>
	 * Método responsável por gravar uma exceção
	 * de erro no arquivo de log do sistema.
	 * </p>
	 * 
	 * @param exception exceção que foi lançada e será gravada no log.
	 */
	public static void logError(Throwable exception) {
		logError("Unexpected Exception", exception);
	}

	/**
	 * <p>
	 * Método responsável por gravar uma mensagem e a pilha
	 * de execução de uma exceção que ocorreu no sistema, no
	 * arquivo de log do sistema.
	 * </p>
	 * 
	 * @param message texto da mensagem de erro que será gravada no log.
	 * @param exception pilha de execução da exceção que foi lançada e será gravada no log.
	 */
	public static void logError(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	/**
	 * <p>
	 * Método flexível que guarda uma mensagem no log do sistema.
	 * </p>
	 * 
	 * @param severity severidade da mensagem (INFO, ERROR, etc.).
	 * @param code código da mensagem.
	 * @param message texto da mensagem.
	 * @param exception pilha de execução da exceção lançada.
	 */
	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	/**
	 * <p>
	 * Método utilizado para criar um status.
	 * </p>
	 * 
	 * @param severity severidade da mensagem (INFO, ERROR, etc.).
	 * @param code code código da mensagem.
	 * @param message texto da mensagem.
	 * @param exception pilha de execução da exceção lançada.
	 * @return IStatus novo.
	 */
	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, FeatActivator.PLUGIN_ID, code,
				message, exception);
	}

	/**
	 * <p>
	 * Método que grava no log do sistema um status.
	 * </p>
	 * 
	 * @param status status que será salvo no log do sistema.
	 */
	public static void log(IStatus status) {
		FeatActivator.getDefault().getLog().log(status);
	}
}
