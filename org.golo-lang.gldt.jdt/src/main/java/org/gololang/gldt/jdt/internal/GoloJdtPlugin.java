/*******************************************************************************
* Copyright 2013 Jeff MAURY
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package org.gololang.gldt.jdt.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.gololang.gldt.jdt.GoloJdtConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;

/**
 * The activator class controls the plug-in life cycle
 */
public class GoloJdtPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.golo-lang.gldt.jdt"; //$NON-NLS-1$

	// The shared instance
	private static GoloJdtPlugin plugin;
	
	/**
	 * The constructor
	 */
	public GoloJdtPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
	public static GoloJdtPlugin getDefault() {
		return plugin;
	}

	/**
	 * Locate a bundle given its id and optional version. If version is null, the highest version is
	 * returned.
	 * 
	 * @param id the bundle id
	 * @param version the version to select
	 * @param strict request strict equality on version
	 * @return the bundle
	 */
	public Bundle getBundle(String id, String version, boolean strict) {
    BundleContext ctx = getBundle().getBundleContext();
    Bundle[] bundles = ctx.getBundles();
    Version v = (version != null)?Version.parseVersion(version):null;
    Bundle selectedBundle = null;
    
    for(Bundle bundle : bundles) {
      if (bundle.getSymbolicName().equals(id)) {
        if (v != null) {
          if (strict && bundle.getVersion().equals(v)) {
            return bundle;
          } else if (!strict && bundle.getVersion().compareTo(v) > 0) {
            selectedBundle = (selectedBundle == null || selectedBundle.getVersion().compareTo(bundle.getVersion()) < 0)?bundle:selectedBundle; 
          }
        } else {
         selectedBundle = (selectedBundle == null || selectedBundle.getVersion().compareTo(bundle.getVersion()) < 0)?bundle:selectedBundle; 
        }
      }
    }
    return selectedBundle;
	}
	
	/**
	 * Return the list of available Golo runtime. Used to provide a list of Golo libraries
	 * by version.
	 * 
	 * @return the list of available Golo runtime JARs.
	 */
	public Collection<Bundle> getGoloBundles() {
	  Collection<Bundle> selected = new ArrayList<Bundle>();
	  BundleContext ctx = getBundle().getBundleContext();
	  Bundle[] bundles = ctx.getBundles();
	  for(Bundle bundle : bundles) {
	    if (bundle.getSymbolicName().equals(GoloJdtConstants.GOLO_RUNTIME_OSGI_IDENTIFIER)) {
	      selected.add(bundle);
	    }
	  }
	  return selected;
	}
}
