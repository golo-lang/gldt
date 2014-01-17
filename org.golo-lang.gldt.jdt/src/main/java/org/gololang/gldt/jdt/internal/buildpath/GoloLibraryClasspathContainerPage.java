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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.gololang.gldt.jdt.GoloJdtConstants;
import org.gololang.gldt.jdt.internal.GoloJdtPlugin;
import org.osgi.framework.Bundle;

public class GoloLibraryClasspathContainerPage extends WizardPage implements
    IClasspathContainerPage {

  private IClasspathEntry selection;
  
  private Combo librariesCombo;
  
  public GoloLibraryClasspathContainerPage() {
    super("GoloLibraryPage");
    setTitle("Golo library");
    setDescription("Configure a Golo library for use in your project");
  }
  
  public GoloLibraryClasspathContainerPage(String pageName) {
    super(pageName);
  }

  public GoloLibraryClasspathContainerPage(String pageName, String title,
      ImageDescriptor titleImage) {
    super(pageName, title, titleImage);
  }

  public void createControl(Composite parent) {
    Composite composite = SWTFactory.createComposite(parent, 1, 1, GridData.FILL_BOTH);
    setControl(composite);
    Label label = new Label(composite, SWT.NONE);
    label.setText("Golo version");
    librariesCombo = new Combo(composite, SWT.NONE);
    librariesCombo.add(GoloJdtConstants.GOLO_LATEST_LIBRARY_ID);
    Collection<Bundle> availables = GoloJdtPlugin.getDefault().getGoloBundles();
    for(Bundle bundle : availables) {
      librariesCombo.add(bundle.getVersion().toString());
    }
    if (selection != null) {
      int index = librariesCombo.indexOf(selection.getPath().segment(1));
      if (index != (-1)) {
        librariesCombo.select(index);
      }
    } else {
      Bundle bundle = GoloJdtPlugin.getDefault().getBundle(GoloJdtConstants.GOLO_RUNTIME_OSGI_IDENTIFIER, null, false);
      int index = 0;
      if (bundle != null){
        index = librariesCombo.indexOf(bundle.getVersion().toString());
      }
      librariesCombo.select(index);
    }
    if (!GoloJdtPlugin.getDefault().isAetherAvailable()) {
      Link link = new Link(composite, SWT.NONE);
      link.setText("Aether is not available so using LATEST will lead to library resolution errors. If you want to install Aether, please follow the instructions on this <a href=\"http://github.com/golo-lang/gldt#Aether\">page</a>.");
      link.addSelectionListener(new SelectionAdapter() {
        
        public void widgetSelected(SelectionEvent event) {
          try {
            PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(event.text));
            GoloLibraryClasspathContainerPage.this.getShell().close();
          }
          catch (PartInitException e) {}
          catch (MalformedURLException e) {}
          
        }
        
      });
    }
  }

  public boolean finish() {
    IPath path = new Path(GoloJdtConstants.GOLO_LIBRARY_CONTAINER_IDENTIFIER).addTrailingSeparator().append(librariesCombo.getItem(librariesCombo.getSelectionIndex()));
    selection = JavaCore.newContainerEntry(path);
    return true;
  }

  public IClasspathEntry getSelection() {
    return selection;
  }

  public void setSelection(IClasspathEntry containerEntry) {
    this.selection = containerEntry;
  }

}
