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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab;
import org.eclipse.jdt.internal.debug.ui.actions.ControlAccessibleListener;
import org.eclipse.jdt.internal.debug.ui.launcher.LauncherMessages;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.gololang.gldt.jdt.GoloJdtConstants;

/**
 * 
 */
public class GoloScriptLauncherTab extends JavaMainTab {

  private Text fGoloScriptText;
  private Button fGoloScriptButton;

  private ModifyListener modifyListener = new ModifyListener() {
    
    public void modifyText(ModifyEvent e) {
      updateLaunchConfigurationDialog();
    }
  };
  
  private SelectionListener selectionListener = new SelectionAdapter() {

    @Override
    public void widgetSelected(SelectionEvent e) {
      IAdaptable root = getJavaProject();
      if (root == null) {
        root = getWorkspaceRoot();
      } else {
        root = ((IJavaProject)root).getProject();
      }
      ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
                                                                         new WorkbenchLabelProvider(),
                                                                         new WorkbenchContentProvider());
      dialog.setAllowMultiple(false);
      dialog.setInput(root);
      dialog.setTitle("Golo script");
      dialog.setMessage("Select the Golo script to run");
      dialog.addFilter(new ViewerFilter() {
        
        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
          IResource resource = (IResource) Platform.getAdapterManager().getAdapter(element, IResource.class);
          return (resource != null && GoloJdtConstants.GOLO_FILE_EXTENSION.equals(resource.getFileExtension())) || resource instanceof IContainer;
        }
      });
      if ((dialog.open() == Window.OK) && (dialog.getResult().length > 0)) {
        IResource resource = (IResource) Platform.getAdapterManager().getAdapter(dialog.getFirstResult(), IResource.class);
        if (resource != null) {
          fGoloScriptText.setText(resource.getFullPath().toString());
        }
      }
    }
  };
  
  @Override
  protected void createMainTypeEditor(Composite parent, String text) {
    super.createMainTypeEditor(parent, text);
    fMainText.setEnabled(false);
    createGoloScriptEditor(parent);
  }

  @Override
  public void setDefaults(ILaunchConfigurationWorkingCopy config) {
    super.setDefaults(config);
    config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, GoloJdtConstants.GOLO_CLI_CLASS_NAME);
  }

  protected void createGoloScriptEditor(Composite parent) {
    Group group = SWTFactory.createGroup(parent, "Golo script", 2, 1, GridData.FILL_HORIZONTAL);
    fGoloScriptText = SWTFactory.createSingleText(group, 1);
    fGoloScriptText.addModifyListener(modifyListener);
    fGoloScriptButton = createPushButton(group, LauncherMessages.AbstractJavaMainTab_1, null); 
    fGoloScriptButton.addSelectionListener(selectionListener);
  }

  @Override
  public void performApply(ILaunchConfigurationWorkingCopy config) {
    super.performApply(config);
    config.setAttribute(GoloJdtConstants.GOLO_LAUNCH_SCRIPT_PARAMETER_NAME, fGoloScriptText.getText());
  }

  @Override
  public void initializeFrom(ILaunchConfiguration config) {
    try {
      super.initializeFrom(config);
      fGoloScriptText.setText(config.getAttribute(GoloJdtConstants.GOLO_LAUNCH_SCRIPT_PARAMETER_NAME,
          GoloJdtConstants.GOLO_LAUNCH_SCRIPT_PARAMETER_DEFAULT_VALUE));
    }
    catch (CoreException e) {
      // TODO dump exception in the error log
      e.printStackTrace();
    }
  }
  
  

}
