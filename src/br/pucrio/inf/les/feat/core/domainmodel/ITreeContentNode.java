package br.pucrio.inf.les.feat.core.domainmodel;

/**
 * <p>
 * Responsável por prover o conteúdo, em formato de árvore, 
 * de cada objeto específico do modelo de domínio da aplicação.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public interface ITreeContentNode {
	/**
	 * <p>
	 * Verifica se o nó da árvore possui filhos.
	 * </p>
	 * 
	 * @return true se possui filho e false se não possui filho.
	 */
	boolean hasChildren();
	
	/**
	 * <p>
	 * Obtêm o pai de um nó da árvore.
	 * </p>
	 * 
	 * @return pai de um nó da árvore.
	 */
	ITreeContentNode getParent();
	
	/**
	 * <p>
	 * Obtêm os filhos de um nó da árvore.
	 * </p>
	 * 
	 * @return filhos de um nó da árvore.
	 */
	ITreeContentNode[] getChildrens();
}
