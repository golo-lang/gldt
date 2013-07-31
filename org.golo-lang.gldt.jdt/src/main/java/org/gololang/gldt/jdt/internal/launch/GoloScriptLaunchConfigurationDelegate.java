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
package org.gololang.gldt.jdt.internal.launch;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

/**
 * 
 */
public class GoloScriptLaunchConfigurationDelegate extends JavaLaunchDelegate {

  @Override
  public String getProgramArguments(ILaunchConfiguration configuration)
      throws CoreException {
    String params = super.getProgramArguments(configuration).trim();
    StringBuilder prefix = new StringBuilder("golo --files ");
    String scriptPath = configuration.getAttribute("script", "");
    IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(scriptPath);
    if (resource != null) {
      prefix.append(resource.getLocation().toString());
      prefix.append(' ');
    }
    if (params.length() > 0) {
      prefix.append("--args ");
      prefix.append(params);
    }
    return prefix.toString();
  }

}
