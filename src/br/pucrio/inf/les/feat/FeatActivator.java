package br.pucrio.inf.les.feat;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import br.pucrio.inf.les.feate.core.repository.ProjectRepository;

/**
 * <p>
 * Classe respons�vel por efetuar toda a inicialiaza��o
 * necess�ria para o plug-in funcionar corretamente.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 */
public class FeatActivator extends AbstractUIPlugin {

	/** Id do plug-in **/
	public static final String PLUGIN_ID = "br.pucrio.inf.les.inf.feat";
	
	private static FeatActivator plugin;
	
	public FeatActivator() { }

	@Override
	/**
	 * <p>
	 * M�todo executado na inicializa��o do plug-in.
	 * Tem a responsabilidade de carregar todos os projetos
	 * que foram gerados pelo plug-in.
	 * </p>
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ProjectRepository.getProjectRepository().loadAll();
	}
	
	@Override
	/**
	 * <p>
	 * M�todo executado quando o eclipse � fechado.
	 * Tem a responsabilidade de salvar todos os projetos
	 * gerados pelo plug-in na base de dados.
	 * </p>
	 */
	public void stop(BundleContext context) throws Exception {
		ProjectRepository.getProjectRepository().saveAll();
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * <p>
	 * M�todo respons�vel por retornar a inst�ncia do
	 * ativador do plug-in.
	 * </p>
	 * @return
	 */
	public static FeatActivator getDefault() {
		return plugin;
	}
}
