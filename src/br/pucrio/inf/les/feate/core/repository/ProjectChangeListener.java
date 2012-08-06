package br.pucrio.inf.les.feate.core.repository;

/**
 * <p>
 * Responsável por representar um listener interessado
 * em alterações no repositório de projetos.
 * Implementa o padrão de projeto Observer.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public interface ProjectChangeListener {
	/**
	 * <p>
	 * Método de callback chamado quando existe uma alteração no repositório.
	 * </p>
	 * 
	 * @param event objeto que encapsula o evento de alteração ocorrido.
	 */
	public void projectChanged(ProjectChangeEvent event);
}
