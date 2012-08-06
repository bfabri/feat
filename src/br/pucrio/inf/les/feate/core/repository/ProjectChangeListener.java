package br.pucrio.inf.les.feate.core.repository;

/**
 * <p>
 * Respons�vel por representar um listener interessado
 * em altera��es no reposit�rio de projetos.
 * Implementa o padr�o de projeto Observer.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public interface ProjectChangeListener {
	/**
	 * <p>
	 * M�todo de callback chamado quando existe uma altera��o no reposit�rio.
	 * </p>
	 * 
	 * @param event objeto que encapsula o evento de altera��o ocorrido.
	 */
	public void projectChanged(ProjectChangeEvent event);
}
