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
		plugin = this;
		FeatManager.getManager().loadProjects();
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		FeatManager.getManager().saveProjects();
		plugin = null;
		super.stop(context);
	}
	
	public static FeatActivator getDefault() {
		return plugin;
	}
}
