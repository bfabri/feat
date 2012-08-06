package br.pucrio.inf.les.feat.core.domainmodel;

/**
 * <p>
 * Respons�vel por prover o conte�do, em formato de �rvore, 
 * de cada objeto espec�fico do modelo de dom�nio da aplica��o.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public interface ITreeContentNode {
	/**
	 * <p>
	 * Verifica se o n� da �rvore possui filhos.
	 * </p>
	 * 
	 * @return true se possui filho e false se n�o possui filho.
	 */
	boolean hasChildren();
	
	/**
	 * <p>
	 * Obt�m o pai de um n� da �rvore.
	 * </p>
	 * 
	 * @return pai de um n� da �rvore.
	 */
	ITreeContentNode getParent();
	
	/**
	 * <p>
	 * Obt�m os filhos de um n� da �rvore.
	 * </p>
	 * 
	 * @return filhos de um n� da �rvore.
	 */
	ITreeContentNode[] getChildrens();
}
