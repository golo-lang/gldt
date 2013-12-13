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
package org.gololang.gldt.jdt.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.wizards.NewElementWizard;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageTwo;
import org.gololang.gldt.jdt.internal.GoloProject;

/**
 * @author jeffmaury
 *
 */
@SuppressWarnings("restriction")
public class GoloProjectWizard extends NewElementWizard {

  NewJavaProjectWizardPageOne firstPage;
  NewJavaProjectWizardPageTwo secondPage;
  
  /**
   * 
   */
  public GoloProjectWizard() {
    setWindowTitle("New Golo project");
  }
  
  
  @Override
  public void addPages() {
    super.addPages();
    addPage(firstPage = new NewJavaProjectWizardPageOne());
    addPage(secondPage = new NewJavaProjectWizardPageTwo(firstPage));
  }


  @Override
  protected void finishPage(IProgressMonitor monitor)
      throws InterruptedException, CoreException {
    secondPage.performFinish(monitor);    
  }
  @Override
  public IJavaElement getCreatedElement() {
    return secondPage.getJavaProject();
  }


  @Override
  public boolean performFinish() {
    boolean ok = super.performFinish();
    try {
      if (ok) {
        GoloProject.addGoloNature(secondPage.getJavaProject().getProject());
      }
    }
    catch (CoreException e) {
      ok = false;
    }
    return ok;
  }

 }
