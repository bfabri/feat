package br.pucrio.inf.les.feat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * Classe respons�vel por gravar informa��es
 * de log do sistema.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public class FeatLog {
	
	/**
	 * <p>
	 * M�todo respons�vel por gravar uma mensagem
	 * de informa��o no arquivo de log do sistema.
	 * </p>
	 * 
	 * @param message texto da mensagem que ser� gravada no log.
	 */
	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	/**
	 * <p>
	 * M�todo respons�vel por gravar uma exce��o
	 * de erro no arquivo de log do sistema.
	 * </p>
	 * 
	 * @param exception exce��o que foi lan�ada e ser� gravada no log.
	 */
	public static void logError(Throwable exception) {
		logError("Unexpected Exception", exception);
	}

	/**
	 * <p>
	 * M�todo respons�vel por gravar uma mensagem e a pilha
	 * de execu��o de uma exce��o que ocorreu no sistema, no
	 * arquivo de log do sistema.
	 * </p>
	 * 
	 * @param message texto da mensagem de erro que ser� gravada no log.
	 * @param exception pilha de execu��o da exce��o que foi lan�ada e ser� gravada no log.
	 */
	public static void logError(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	/**
	 * <p>
	 * M�todo flex�vel que guarda uma mensagem no log do sistema.
	 * </p>
	 * 
	 * @param severity severidade da mensagem (INFO, ERROR, etc.).
	 * @param code c�digo da mensagem.
	 * @param message texto da mensagem.
	 * @param exception pilha de execu��o da exce��o lan�ada.
	 */
	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	/**
	 * <p>
	 * M�todo utilizado para criar um status.
	 * </p>
	 * 
	 * @param severity severidade da mensagem (INFO, ERROR, etc.).
	 * @param code code c�digo da mensagem.
	 * @param message texto da mensagem.
	 * @param exception pilha de execu��o da exce��o lan�ada.
	 * @return IStatus novo.
	 */
	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, FeatActivator.PLUGIN_ID, code,
				message, exception);
	}

	/**
	 * <p>
	 * M�todo que grava no log do sistema um status.
	 * </p>
	 * 
	 * @param status status que ser� salvo no log do sistema.
	 */
	public static void log(IStatus status) {
		FeatActivator.getDefault().getLog().log(status);
	}
}
