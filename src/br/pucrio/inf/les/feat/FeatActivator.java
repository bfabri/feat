package br.pucrio.inf.les.feat;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import br.pucrio.inf.les.feat.core.model.FeatManager;

public class FeatActivator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "br.pucrio.inf.les.inf.feat";
	
	private static FeatActivator plugin;
	
	public FeatActivator() { }

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		FeatManager.getManager().loadProjects();
		plugin = this;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		FeatManager.getManager().saveProjects();
		super.stop(context);
	}
	
	public static FeatActivator getDefault() {
		return plugin;
	}
}
