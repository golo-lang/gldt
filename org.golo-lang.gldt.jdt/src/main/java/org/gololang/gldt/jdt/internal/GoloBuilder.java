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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.gololang.gldt.jdt.GoloJdtConstants;
import org.gololang.gldt.jdt.compiler.Compiler;
import org.gololang.gldt.jdt.compiler.CompilerCompilationException;
import org.gololang.gldt.jdt.compiler.CompilerResult;
import org.gololang.gldt.jdt.compiler.CompilerCompilationException.Problem;
import org.gololang.gldt.jdt.internal.compiler.proxy.ProxyCompilerFactory;

/**
 * 
 */
public class GoloBuilder extends IncrementalProjectBuilder {

  private static class ResourceEntry {
    private IFile resource;
    private IPath target;
    
    private ResourceEntry(IFile resource, IPath target) {
      this.resource = resource;
      this.target = target;
    }
  }
  
	/**
	 * 
	 */
	public GoloBuilder() {
	}

	private IPath isOnSourceRoot(IResource resource, IJavaProject javaProject) throws JavaModelException {
	  IPath result = null;
	  IClasspathEntry[] entries = javaProject.getRawClasspath();
	  
	  for(IClasspathEntry entry :  entries) {
	    if ((entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) && entry.getPath().isPrefixOf(resource.getFullPath())) {
	      result = entry.getOutputLocation();
	      if (result == null) {
	        result = javaProject.getOutputLocation();
	      }
	      break;
	    }
	  }
	  return result;
	}
	
  private IPath isGoloResource(IResource resource,
      IJavaProject javaProject) {
    try {
      if (resource instanceof IFile && 
          GoloJdtConstants.GOLO_FILE_EXTENSION.equals(resource.getProjectRelativePath().getFileExtension())) {
        return isOnSourceRoot(resource, javaProject);
      } else {
        return null;
      }
    }
    catch (JavaModelException e) {
      return null;
    }
  }
  
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(int kind, Map args,
			IProgressMonitor monitor) throws CoreException {
	  final IJavaProject javaProject = JavaCore.create(getProject());
	  final List<ResourceEntry> tobeCompiled = new ArrayList<ResourceEntry>();
	  final List<ResourceEntry> toBeDeleted = new ArrayList<ResourceEntry>();
	  
	  switch (kind) {
	  case AUTO_BUILD:
	  case INCREMENTAL_BUILD:
	    IResourceDelta delta = getDelta(getProject());
	    delta.accept(new IResourceDeltaVisitor() {
        public boolean visit(IResourceDelta delta) throws CoreException {
          IPath target;
          
          if ((target = isGoloResource(delta.getResource(), javaProject)) != null) {
            if ((delta.getKind() == IResourceDelta.ADDED) || (delta.getKind() == IResourceDelta.CHANGED)) {
              tobeCompiled.add(new ResourceEntry((IFile) delta.getResource(), target));
            } else if (delta.getKind() == IResourceDelta.REMOVED) {
              toBeDeleted.add(new ResourceEntry((IFile) delta.getResource(), target));
            }
          }
          return true;
        }
      });
	    break;
	  case FULL_BUILD:
	    getProject().accept(new IResourceVisitor() {
        
        public boolean visit(IResource resource) throws CoreException {
          IPath target;
          
          if ((target = isGoloResource(resource, javaProject)) != null) {
            tobeCompiled.add(new ResourceEntry((IFile) resource, target));
          }
          return true;
        }
      });
	  }
	  Compiler compiler = null;
	  for(ResourceEntry entry : tobeCompiled) {
	    if (compiler == null) {
	      compiler = new ProxyCompilerFactory().getCompiler(getProject());
	    }
	    processFile(compiler, entry, false);
	  }
    /*for(ResourceEntry entry : toBeDeleted) {
      processFile(compiler, entry, true);
    }*/
		return null;
	}

  private void processFile(Compiler compiler, ResourceEntry entry, boolean delete) {
    try {
      try {
        entry.resource.deleteMarkers(GoloJdtConstants.GOLO_COMPILATION_MARKER_ID, true, IResource.DEPTH_ZERO);
      }
      catch (CoreException e1) {}
      List<CompilerResult> results = compiler.process(entry.resource);
      for(CompilerResult result : results) {
        IFolder targetFolder = entry.resource.getWorkspace().getRoot().getFolder(entry.target);
        String fileName = getResultFileName(result.getPackageName(), result.getClassName());
        IFile file = targetFolder.getFile(fileName);
        try {
          if (!delete) {
            CreateFileOperation op = new CreateFileOperation(file, null, new ByteArrayInputStream(result.getResult()), "Create Golo compilation result");
            op.execute(null, null);
          } else {
            file.delete(true, null);
          }
        } catch (CoreException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (ExecutionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    catch (CompilerCompilationException e) {
      for(Problem problem : e.getProblems()) {
        try {
          IMarker marker = entry.resource.createMarker(GoloJdtConstants.GOLO_COMPILATION_MARKER_ID);
          marker.setAttribute(IMarker.LINE_NUMBER, problem.getBeginLine() + 1);
          marker.setAttribute(IMarker.MESSAGE, problem.getDescription());
          marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
          //marker.setAttribute(IMarker.CHAR_START, problem.getBeginOffset());
          //marker.setAttribute(IMarker.CHAR_END, problem.getEndOffset());
        }
        catch (CoreException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    }
    
  }

  private String getResultFileName(String packageName, String className) {
    StringBuilder builder = new StringBuilder();
    if (packageName != null) {
      builder.append(packageName.replace('.', '/'));
      builder.append('/');
    }
    builder.append(className);
    builder.append(".class");
    return builder.toString();
  }

}
