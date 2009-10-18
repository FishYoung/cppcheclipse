package com.googlecode.cppcheclipse.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CppcheclipsePlugin extends AbstractUIPlugin {

	// The shared instance
	private static CppcheclipsePlugin plugin;
	
	private IPreferenceStore workspacePreferenceStore, configurationPreferenceStore;

	/**
	 * The constructor
	 */
	public CppcheclipsePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static CppcheclipsePlugin getDefault() {
		return plugin;
	}

	public static String getId() {
		return getDefault().getBundle().getSymbolicName();
	}

	public static IPreferenceStore getProjectPreferenceStore(IProject project, boolean useExtendedSearchContext) {
		// Create an overlay preference store and fill it with properties
		ProjectScope ps = new ProjectScope(project);
		ScopedPreferenceStore scoped = new ScopedPreferenceStore(ps, getId());
		if (useExtendedSearchContext) {
			scoped.setSearchContexts(new IScopeContext[] { ps,
				new InstanceScope() });
		}
		return scoped;
	}

	public static IPreferenceStore getWorkspacePreferenceStore() {
		return getDefault().getInternalWorkspacePreferenceStore();
	}
	
	private IPreferenceStore getInternalWorkspacePreferenceStore() {
		if (workspacePreferenceStore == null) {
			workspacePreferenceStore = new ScopedPreferenceStore(new InstanceScope(), getId());
		}
		return workspacePreferenceStore;
	}
	
	public static IPreferenceStore getConfigurationPreferenceStore() {
		return getDefault().getInternalConfigurationPreferenceStore();
	}
	
	private IPreferenceStore getInternalConfigurationPreferenceStore() {
		if (configurationPreferenceStore == null) {
			configurationPreferenceStore = new ScopedPreferenceStore(new ConfigurationScope(), getId());
		}
		return configurationPreferenceStore;
	}

	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status
	 *            status to log
	 */
	public static void log(int severity, int style, String message,
			Throwable exception) {

		IStatus status = new Status(severity, getId(), 1, message, exception);
		StatusManager.getManager().handle(status, style);
	}

	/**
	 * Logs an internal error with the specified throwable
	 * 
	 * @param e
	 *            the exception to be logged
	 */
	public static void log(Throwable e) {
		log(IStatus.ERROR, StatusManager.LOG, "Internal Error", e); //$NON-NLS-1$
	}

	/**
	 * Logs an internal error with the specified message.
	 * 
	 * @param message
	 *            the error message to log
	 */
	public static void log(String message) {
		log(IStatus.ERROR, StatusManager.LOG, message, null);
	}

	public static void showError(String message, Throwable e) {
		log(IStatus.ERROR, StatusManager.SHOW|StatusManager.LOG, message, e);
	}
}