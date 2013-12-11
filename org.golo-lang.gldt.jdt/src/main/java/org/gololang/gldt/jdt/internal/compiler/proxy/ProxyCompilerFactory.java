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
package org.gololang.gldt.jdt.internal.compiler.proxy;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.gololang.gldt.jdt.compiler.Compiler;
import org.gololang.gldt.jdt.compiler.CompilerFactory;
import org.gololang.gldt.jdt.compiler.CompilerInstantiationException;

/**
 * 
 */
public class ProxyCompilerFactory implements CompilerFactory {

  private static final String GOLO_COMPILER_CLASS_NAME = "fr.insalyon.citi.golo.compiler.GoloCompiler"; //$NON-NLS-1$

  private static final String GOLO_COMPILER_COMPILE_METHOD = "compile"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILATION_RESULT_CLASS_NAME = "fr.insalyon.citi.golo.compiler.CodeGenerationResult"; //$NON-NLS-1$
  
  private static final String GOLO_RESULT_BYTECODE_METHOD = "getBytecode"; //$NON-NLS-1$
  
  private static final String GOLO_RESULT_PACKAGE_AND_CLASS_METHOD = "getPackageAndClass"; //$NON-NLS-1$
  
  private static final String GOLO_PACKAGE_AND_CLASS_CLASS_NAME = "fr.insalyon.citi.golo.compiler.PackageAndClass"; //$NON-NLS-1$
  
  private static final String GOLO_PACKAGE_AND_CLASS_CLASS_NAME_METHOD = "className"; //$NON-NLS-1$
  
  private static final String GOLO_PACKAGE_AND_CLASS_PACKAGE_NAME_METHOD = "packageName"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_EXCEPTION_CLASS_NAME = "fr.insalyon.citi.golo.compiler.GoloCompilationException"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_EXCEPTION_PROBLEMS_METHOD_NAME = "getProblems"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_PROBLEM_CLASS_NAME = "fr.insalyon.citi.golo.compiler.GoloCompilationException$Problem"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_PROBLEM_DESCRIPTION_METHOD_NAME = "getDescription"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_PROBLEM_TOKEN_METHOD_NAME = "getToken"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_PROBLEM_FIRST_TOKEN_METHOD_NAME = "getFirstToken"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_PROBLEM_LAST_TOKEN_METHOD_NAME = "getLastToken"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_CLASS_NAME = "fr.insalyon.citi.golo.compiler.parser.Token"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_BEGIN_LINE_FIELD_NAME = "beginLine"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_BEGIN_COLUMN_FIELD_NAME = "beginColumn"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_START_OFFSET_FIELD_NAME = "startOffset"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_END_LINE_FIELD_NAME = "endLine"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_END_COLUMN_FIELD_NAME = "endColumn"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_END_OFFSET_FIELD_NAME = "endOffset"; //$NON-NLS-1$
  
  private static final String GOLO_COMPILER_TOKEN_IMAGE_FIELD_NAME = "image"; //$NON-NLS-1$
  
  /**
   * 
   */
  public ProxyCompilerFactory() {
  }

  /* (non-Javadoc)
   * @see org.golo_lang.gldt.jdt.compiler.CompilerFactory#getCompiler(org.eclipse.core.resources.IProject)
   */
  public Compiler getCompiler(IProject project) {
    try {
      Method compilerCompilerMethod;
      Method resultByteCodeMethod;
      Method resultPackageAndClassMethod;
      Method packageAndClassClassNameMethod;
      Method packageAndClassPackageNameMethod;
      Method exceptionProblemsMethod;
      Method problemDescriptionMethod;
      Method problemFirstTokenMethod;
      Method problemLastTokenMethod;
      Field tokenBeginLineField;
      Field tokenBeginColumnField;
      Field tokenStartOffsetField;
      Field tokenEndLineField;
      Field tokenEndColumnField;
      Field tokenEndOffsetField;
      Field tokenImageField;
      
      
      IJavaProject javaProject = JavaCore.create(project);
      String[] entries = JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
      int i=0;
      URL[] urls = new URL[entries.length];
      for(String entry : entries) {
        IPath path = new Path(entry);
        urls[i++] = path.toFile().toURI().toURL();
      }
      URLClassLoader cl = new URLClassLoader(urls, ProxyCompilerFactory.class.getClassLoader());
      Class<?> compilerClass = cl.loadClass(GOLO_COMPILER_CLASS_NAME);
      compilerCompilerMethod = compilerClass.getMethod(GOLO_COMPILER_COMPILE_METHOD, new Class[] {String.class, InputStream.class});
      Class<?> resultClass = cl.loadClass(GOLO_COMPILATION_RESULT_CLASS_NAME);
      resultByteCodeMethod = resultClass.getMethod(GOLO_RESULT_BYTECODE_METHOD, new Class[] {});
      resultPackageAndClassMethod = resultClass.getMethod(GOLO_RESULT_PACKAGE_AND_CLASS_METHOD, new Class[] {});
      Class<?> packageAndClassClass = cl.loadClass(GOLO_PACKAGE_AND_CLASS_CLASS_NAME);
      packageAndClassClassNameMethod = packageAndClassClass.getMethod(GOLO_PACKAGE_AND_CLASS_CLASS_NAME_METHOD, new Class[] {});
      packageAndClassPackageNameMethod = packageAndClassClass.getMethod(GOLO_PACKAGE_AND_CLASS_PACKAGE_NAME_METHOD, new Class[] {});
      Class<?> exceptionClass = cl.loadClass(GOLO_COMPILER_EXCEPTION_CLASS_NAME);
      exceptionProblemsMethod = exceptionClass.getMethod(GOLO_COMPILER_EXCEPTION_PROBLEMS_METHOD_NAME, new Class[] {});
      Class<?> problemClass = cl.loadClass(GOLO_COMPILER_PROBLEM_CLASS_NAME);
      problemDescriptionMethod = problemClass.getMethod(GOLO_COMPILER_PROBLEM_DESCRIPTION_METHOD_NAME, new Class[] {});
      try {
        problemFirstTokenMethod = problemClass.getMethod(GOLO_COMPILER_PROBLEM_TOKEN_METHOD_NAME, new Class[] {});
        problemLastTokenMethod = problemFirstTokenMethod;
      }
      catch (NoSuchMethodException e) {
        problemFirstTokenMethod = problemClass.getMethod(GOLO_COMPILER_PROBLEM_FIRST_TOKEN_METHOD_NAME, new Class[] {});
        problemLastTokenMethod = problemClass.getMethod(GOLO_COMPILER_PROBLEM_LAST_TOKEN_METHOD_NAME, new Class[] {});
        
      }
      Class<?> tokenClass = cl.loadClass(GOLO_COMPILER_TOKEN_CLASS_NAME);
      tokenBeginLineField = tokenClass.getField(GOLO_COMPILER_TOKEN_BEGIN_LINE_FIELD_NAME);
      tokenBeginColumnField = tokenClass.getField(GOLO_COMPILER_TOKEN_BEGIN_COLUMN_FIELD_NAME);
      tokenStartOffsetField = tokenClass.getField(GOLO_COMPILER_TOKEN_START_OFFSET_FIELD_NAME);
      tokenEndLineField = tokenClass.getField(GOLO_COMPILER_TOKEN_END_LINE_FIELD_NAME);
      tokenEndColumnField = tokenClass.getField(GOLO_COMPILER_TOKEN_END_COLUMN_FIELD_NAME);
      tokenEndOffsetField = tokenClass.getField(GOLO_COMPILER_TOKEN_END_OFFSET_FIELD_NAME);
      tokenImageField = tokenClass.getField(GOLO_COMPILER_TOKEN_IMAGE_FIELD_NAME);
      return new ProxyCompiler(compilerCompilerMethod,
                               resultByteCodeMethod,
                               resultPackageAndClassMethod,
                               packageAndClassClassNameMethod,
                               packageAndClassPackageNameMethod,
                               exceptionProblemsMethod,
                               problemDescriptionMethod,
                               problemFirstTokenMethod,
                               problemLastTokenMethod,
                               tokenBeginLineField,
                               tokenBeginColumnField,
                               tokenStartOffsetField,
                               tokenEndLineField,
                               tokenEndColumnField,
                               tokenEndOffsetField,
                               tokenImageField,
                               compilerClass.newInstance());
    }
    catch (MalformedURLException e) {
      throw new CompilerInstantiationException(e);
    }
    catch (ClassNotFoundException e) {
      throw new CompilerInstantiationException(e);
    }
    catch (NoSuchMethodException e) {
      throw new CompilerInstantiationException(e);
    }
    catch (SecurityException e) {
      throw new CompilerInstantiationException(e);
    }
    catch (CoreException e) {
      throw new CompilerInstantiationException(e);
    }
    catch (NoSuchFieldException e) {
      throw new CompilerInstantiationException(e);
    }
    catch (InstantiationException e) {
      throw new CompilerInstantiationException(e);
    }
    catch (IllegalAccessException e) {
      throw new CompilerInstantiationException(e);
    }
  }

}
