package br.pucrio.inf.les.feat.core.domainmodel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ide.IDE;

/**
 * <p>
 * Classe respons�vel por representar um projeto.
 * Guarda informa��es relevantes do projeto para serem
 * exibidas para o usu�rio final do plug-in.
 * </p>
 * 
 * @author Bruno F�bri
 * @version 1.0
 */
public class Project implements ITreeContentNode, IStyledLabel {

	/**
	 * <p>
	 * Nome do projeto.
	 * </p>
	 */
	private final String name;
	
	/**
	 * <p>
	 * Conjunto de vers�es do projeto.
	 * </p>
	 */
	private Set<Version> versions; 
	
	/**
	 * <p>
	 * Construtor do projeto.
	 * </p>
	 * 
	 * @param name nome do projeto a ser criado.
	 */
	public Project(String name) {
		this.name = name;
		this.versions = new HashSet<Version>();
	}
	
	/**
	 * <p>
	 * Obt�m o nome do projeto.
	 * </p>
	 * 
	 * @return nome do projeto.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * <p>
	 * Obt�m as vers�es do projeto. 
	 * </p>
	 * 
	 * @return vers�es de um projeto.
	 */
	public Version[] getVersions() {
		return this.versions.toArray(new Version[this.versions.size()]);
	}
	
	/**
	 * <p>
	 * Obt�m a �ltima vers�o do projeto.
	 * </p>
	 * 
	 * @throws UnsupportedOperationException quando o projeto n�o possui vers�es.
	 * @return �ltima vers�o do projeto.
	 */
	public Version getLastVersion() {
		if (this.versions.size() > 0) {
			Iterator<Version> it = this.versions.iterator();
			Version lastVersion = it.next();
			while (it.hasNext()) {
				Version lastVersionCandidate = it.next();
				if (lastVersion.getDate().before(lastVersionCandidate.getDate())) {
					lastVersion = lastVersionCandidate;
				}
			}
			return lastVersion;
		}
		throw new UnsupportedOperationException("This project do not contain any versions yet.");
	}

	/**
	 * <p>
	 * Adiciona uma nova vers�o ao projeto.
	 * </p>
	 * 
	 * @param version nova vers�o que ser� adicionada.
	 * @return true se foi adicionada e false se n�o foi adicionada.
	 */
	public boolean addVersion(Version version) {
		return this.versions.add(version);
	}
	
	/**
	 * <p>
	 * Remove uma vers�o do projeto.
	 * </p>
	 * 
	 * @param version vers�o que ser� removida.
	 * @return true se foi removida e false se n�o foi removida.
	 */
	public boolean removeVersion(Version version) {
		return this.versions.remove(version);
	}
	
	@Override
	public boolean hasChildren() {
		return getVersions().length > 0;
	}
	
	@Override
	public ITreeContentNode[] getChildrens() {
		return getVersions();
	}
	
	@Override
	public ITreeContentNode getParent() {
		return null;
	}
	
	@Override
	public Image getImage() {
		return SHARED_IMAGES.getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
	}
	
	@Override
	public StyledString getStyledLabel() {
		StyledString styledLabel = new StyledString();
		styledLabel.append(this.name);
		styledLabel.append(String.format(" (%1$s) ", this.versions.size()), StyledString.COUNTER_STYLER);
		return styledLabel;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	/**
	 * <p>
	 * M�todo respons�vel por informar se dois projetos
	 * s�o iguais. Leva em considera��o o nome do projeto
	 * para definir a igualdade.
	 * </p>
	 */
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Project) {
				Project other = (Project) obj;
				if (this.name != null && other.name != null) {
					return this.name.equals(other.name);
				}
			}
		}
		return false;
	}
}
