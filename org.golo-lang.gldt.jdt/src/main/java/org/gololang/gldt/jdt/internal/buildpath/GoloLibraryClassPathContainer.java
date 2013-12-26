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

import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.gololang.gldt.jdt.GoloJdtConstants;
import org.osgi.framework.Bundle;

/**
 * @author jeffmaury
 *
 */
public class GoloLibraryClassPathContainer implements IClasspathContainer {

  private Bundle goloBundle;
  private Bundle asmBundle;
  private Bundle jcommanderBundle;

  /**
   * 
   */
  public GoloLibraryClassPathContainer(Bundle goloBundle, Bundle asmBundle, Bundle jcommanderBundle) {
    this.goloBundle = goloBundle;
    this.asmBundle = asmBundle;
    this.jcommanderBundle = jcommanderBundle;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jdt.core.IClasspathContainer#getClasspathEntries()
   */
  public IClasspathEntry[] getClasspathEntries() {
    try {
      IClasspathEntry goloEntry = JavaCore.newLibraryEntry(new Path(FileLocator.getBundleFile(goloBundle).getAbsolutePath()), null, null);
      IClasspathEntry asmEntry = JavaCore.newLibraryEntry(new Path(FileLocator.getBundleFile(asmBundle).getAbsolutePath()), null, null);
      IClasspathEntry[] entries;
      if (jcommanderBundle != null) {
        IClasspathEntry jcommanderEntry = JavaCore.newLibraryEntry(new Path(FileLocator.getBundleFile(jcommanderBundle).getAbsolutePath()), null, null);
        entries = new IClasspathEntry[] { goloEntry, asmEntry, jcommanderEntry };
      } else {
        entries = new IClasspathEntry[] { goloEntry, asmEntry };
      }
      return entries;
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.jdt.core.IClasspathContainer#getDescription()
   */
  public String getDescription() {
    return "Golo library [" + goloBundle.getVersion().toString() + "]";
  }

  /* (non-Javadoc)
   * @see org.eclipse.jdt.core.IClasspathContainer#getKind()
   */
  public int getKind() {
    return K_APPLICATION;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jdt.core.IClasspathContainer#getPath()
   */
  public IPath getPath() {
    return new Path(GoloJdtConstants.GOLO_LIBRARY_CONTAINER_IDENTIFIER).append(goloBundle.getVersion().toString());
  }

}
