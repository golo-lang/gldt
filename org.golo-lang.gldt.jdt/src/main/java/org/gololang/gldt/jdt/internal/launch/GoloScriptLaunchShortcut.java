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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.gololang.gldt.jdt.GoloJdtConstants;

/**
 * @author jeffmaury
 *
 */
public class GoloScriptLaunchShortcut implements ILaunchShortcut2 {

  private void launch(IResource resource, String mode) throws CoreException {
    ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
    ILaunchConfigurationType type = launchManager.getLaunchConfigurationType(GoloJdtConstants.GOLO_LAUNCH_SCRIPT_CONFIGURATION_TYPE);
    ILaunchConfiguration[] configurations = launchManager.getLaunchConfigurations(type);
    for(ILaunchConfiguration configuration : configurations) {
      String path = configuration.getAttribute(GoloJdtConstants.GOLO_LAUNCH_SCRIPT_PARAMETER_NAME, GoloJdtConstants.GOLO_LAUNCH_SCRIPT_PARAMETER_DEFAULT_VALUE);
      if (resource.getFullPath().toPortableString().equals(path)) {
        configuration.launch(mode, null);
        return;
      }
    }
    ILaunchConfigurationWorkingCopy copy = type.newInstance(null, resource.getFullPath().removeFileExtension().lastSegment());
    copy.setAttribute(GoloJdtConstants.GOLO_LAUNCH_SCRIPT_PARAMETER_NAME, resource.getFullPath().toPortableString());
    copy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, resource.getProject().getName());
    copy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, GoloJdtConstants.GOLO_CLI_CLASS_NAME);
    copy.doSave().launch(mode, null);
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.jface.viewers.ISelection, java.lang.String)
   */
  public void launch(ISelection selection, String mode) {
    try {
      launch(getLaunchableResource(selection), mode);
    }
    catch (CoreException e) {
      MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "An error occurred while launching Golo script");
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.ui.IEditorPart, java.lang.String)
   */
  public void launch(IEditorPart editor, String mode) {
    try {
      launch(getLaunchableResource(editor), mode);
    }
    catch (CoreException e) {
      MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "An error occurred while launching Golo script");
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchConfigurations(org.eclipse.jface.viewers.ISelection)
   */
  public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
    /*
     * let the debug framework do the mapping using resource
     */
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchConfigurations(org.eclipse.ui.IEditorPart)
   */
  public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
    /*
     * let the debug framework do the mapping using resource
     */
    return null;
  }

  private IResource getLaunchableResource(IAdaptable adaptable) {
    return (IResource) adaptable.getAdapter(IResource.class);
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse.jface.viewers.ISelection)
   */
  public IResource getLaunchableResource(ISelection selection) {
    if (selection instanceof IStructuredSelection) {
      IStructuredSelection sselection = (IStructuredSelection)selection;
      if (sselection.size() == 1) {
        Object result = sselection.getFirstElement();
        if (result instanceof IAdaptable) {
          return getLaunchableResource((IAdaptable)result);
        }
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse.ui.IEditorPart)
   */
  public IResource getLaunchableResource(IEditorPart editorpart) {
    return getLaunchableResource(editorpart.getEditorInput());
  }
}
