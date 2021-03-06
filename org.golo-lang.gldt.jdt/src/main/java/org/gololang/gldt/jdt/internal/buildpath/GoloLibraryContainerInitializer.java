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
package org.gololang.gldt.jdt.internal.buildpath;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.gololang.gldt.jdt.GoloJdtConstants;
import org.gololang.gldt.jdt.internal.GoloJdtPlugin;
import org.gololang.gldt.jdt.internal.aether.AetherHelper;
import org.osgi.framework.Bundle;

/**
 * @author jeffmaury
 *
 */
public class GoloLibraryContainerInitializer extends
    ClasspathContainerInitializer {

  /**
   * 
   */
  public GoloLibraryContainerInitializer() {
  }

  private boolean isGoloLibraryContainerPath(IPath path) {
    return path != null && path.segmentCount() == 2 && path.segment(0).equals(GoloJdtConstants.GOLO_LIBRARY_CONTAINER_IDENTIFIER);
  }
  
  @Override
  public Object getComparisonID(IPath containerPath, IJavaProject project) {
    return containerPath;
  }

  @Override
  public String getDescription(IPath containerPath, IJavaProject project) {
    if (isGoloLibraryContainerPath(containerPath)) {
      return "Golo library [" + containerPath.segment(1) + "]";
    }
    return super.getDescription(containerPath, project);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jdt.core.ClasspathContainerInitializer#initialize(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject)
   */
  @Override
  public void initialize(IPath containerPath, IJavaProject project)
      throws CoreException {
    GoloJdtPlugin plugin = GoloJdtPlugin.getDefault();
    
    try {
      if (isGoloLibraryContainerPath(containerPath)) {
        String goloVersion = containerPath.segment(1);
        
        File goloFile = null;
        if (GoloJdtConstants.GOLO_LATEST_LIBRARY_ID.equals(goloVersion)) {
          if (plugin.isAetherAvailable()) {
            goloFile = AetherHelper.INSTANCE.resolveLatestGoloJarFile();
          } else {
            throw new CoreException(new Status(IStatus.ERROR, GoloJdtPlugin.PLUGIN_ID, "Eclipse Aether not available, can't resolve LATEST libraries"));
          }
        } else {
          Bundle goloBundle = plugin.getBundle(GoloJdtConstants.GOLO_RUNTIME_OSGI_IDENTIFIER, goloVersion, true);
          if (goloBundle != null) {
            goloFile = FileLocator.getBundleFile(goloBundle);
          }
        }
        Bundle asmBundle = plugin.getBundle(GoloJdtConstants.ASM_OSGI_IDENTIFIER, GoloJdtConstants.ASM_MINIMUM_VERSION, false);
        if ((goloFile != null) && (asmBundle != null)) {
          Bundle jcommanderBundle = plugin.getBundle(GoloJdtConstants.JCOMMANDER_OSGI_IDENTIFIER, null, false);
          IClasspathContainer container = new GoloLibraryClassPathContainer(goloFile, goloVersion, asmBundle, jcommanderBundle);
          JavaCore.setClasspathContainer(containerPath,
                                         new IJavaProject[] { project },
                                         new IClasspathContainer[] { container },
                                         null);
        }
      }
    }
    catch (IOException e) {
      throw new CoreException(new Status(IStatus.ERROR, GoloJdtPlugin.PLUGIN_ID, e.getLocalizedMessage(), e));
    }
  }

}
