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
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.gololang.gldt.jdt.GoloJdtConstants;

import com.google.common.base.Joiner;

/**
 * 
 */
public class GoloProject implements IProjectNature {

  /**
   * The associated workspace project
   */
  private IProject project;

  /**
   * Add the Golo builder to the associated project. No op if the builder is already
   * present.
   * 
   * @throws CoreException
   */
  protected void addGoloBuilder() throws CoreException {
    IProjectDescription description = project.getDescription();
    ICommand[] commands = description.getBuildSpec();
    for(ICommand command : commands) {
      if (command.getBuilderName().equals(GoloJdtConstants.GOLO_BUILDER_ID)) {
        return;
      }
    }
    ICommand[] newCommands = new ICommand[commands.length + 1];
    System.arraycopy(commands, 0, newCommands, 0, commands.length);
    newCommands[commands.length] = description.newCommand();
    newCommands[commands.length].setBuilderName(GoloJdtConstants.GOLO_BUILDER_ID);
    description.setBuildSpec(newCommands);
    project.setDescription(description, null);
  }
  
  protected void removeGoloBuilder() throws CoreException {
    IProjectDescription description = this.project.getDescription();
    ICommand[] commands = description.getBuildSpec();
    for (int i = 0; i < commands.length; ++i) {
      if (commands[i].getBuilderName().equals(GoloJdtConstants.GOLO_BUILDER_ID)) {
        ICommand[] newCommands = new ICommand[commands.length - 1];
        System.arraycopy(commands, 0, newCommands, 0, i);
        System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
        description.setBuildSpec(newCommands);
        this.project.setDescription(description, null);
        return;
      }
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#configure()
   */
  public void configure() throws CoreException {
    addGoloBuilder();

  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#deconfigure()
   */
  public void deconfigure() throws CoreException {
    removeGoloBuilder();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#getProject()
   */
  public IProject getProject() {
    return project;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
   */
  public void setProject(IProject project) {
    this.project = project;
  }

  /**
   * Add the Golo nature to the project if it has not the Golo nature, otherwise do nothing.
   * 
   * @param project the project to update
   * @throws CoreException if an update error occurs
   */
  public static void addGoloNature(IProject project) throws CoreException {
    if (!project.hasNature(GoloJdtConstants.GOLO_NATURE_ID)) {
      IProjectDescription description = project.getDescription();
      String[] oldNatures = description.getNatureIds();
      String[] newNatures = new String[oldNatures.length + 1];
      System.arraycopy(oldNatures, 0, newNatures, 0, oldNatures.length);
      newNatures[oldNatures.length] = GoloJdtConstants.GOLO_NATURE_ID;
      description.setNatureIds(newNatures);
      project.setDescription(description, null);
    }
  }

  /**
   * Remove the Golo nature from the project if it has the Golo nature, otherwise do nothing.
   * 
   * @param project the project to update
   * @throws CoreException if an update error occurs
   */
  public static void removeGoloNature(IProject project) throws CoreException {
    if (project.hasNature(GoloJdtConstants.GOLO_NATURE_ID)) {
      IProjectDescription description = project.getDescription();
      String[] oldNatures = description.getNatureIds();
      String[] newNatures = new String[oldNatures.length - 1];
      for(int i=0,j=0; i < oldNatures.length;++i) {
        if (!oldNatures[i].equals(GoloJdtConstants.GOLO_NATURE_ID)) {
          newNatures[j++] = oldNatures[i];
        }
      }
      description.setNatureIds(newNatures);
      project.setDescription(description, null);
    }
  }

  public static void toggleNature(IProject project) throws CoreException {
    if (project.hasNature(GoloJdtConstants.GOLO_NATURE_ID)) {
      includeGoloFilesInOutput(project);
      removeGoloNature(project);
    } else {
      excludeGoloFilesFromOutput(project);
      addGoloNature(project);
    }
  }
  
  /**
   * Exclude all Golo files from compilation
   * 
   * @param javaProject
   */
  public static void excludeGoloFilesFromOutput(final IProject project) {
    // make sure .golo files are not copied to the output dir
    IJavaProject javaProject = JavaCore.create(project);
    if (javaProject != null) {
      String excludedResources = javaProject.getOption(
          JavaCore.CORE_JAVA_BUILD_RESOURCE_COPY_FILTER, true);
      if (excludedResources.indexOf(GoloJdtConstants.GOLO_FILE_FILTER) == -1) {
        excludedResources = excludedResources.length() == 0 ? GoloJdtConstants.GOLO_FILE_FILTER
            : excludedResources + ',' + GoloJdtConstants.GOLO_FILE_FILTER;
        javaProject.setOption(JavaCore.CORE_JAVA_BUILD_RESOURCE_COPY_FILTER,
            excludedResources);
      }
    }
  }

  /**
   * Include all Golo files for compilation
   * 
   * @param project
   */
  public static void includeGoloFilesInOutput(final IProject project) {
    // make sure .golo files are not copied to the output dir
    IJavaProject javaProject = JavaCore.create(project);
    if (javaProject != null) {
      final String[] excludedResourcesArray = javaProject.getOption(
          JavaCore.CORE_JAVA_BUILD_RESOURCE_COPY_FILTER, true).split(",");
      final List<String> excludedResources = new ArrayList<String>();
      for (int i = 0; i < excludedResourcesArray.length; i++) {
        final String excluded = excludedResourcesArray[i].trim();
        if (excluded.endsWith(GoloJdtConstants.GOLO_FILE_FILTER))
          continue;
        excludedResources.add(excluded);
      }
      javaProject.setOption(JavaCore.CORE_JAVA_BUILD_RESOURCE_COPY_FILTER,
          Joiner.on(',').join(excludedResources));
    }
  }

}
