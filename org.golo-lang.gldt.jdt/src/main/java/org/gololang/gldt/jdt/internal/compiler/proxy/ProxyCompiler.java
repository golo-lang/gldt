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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.gololang.gldt.jdt.compiler.Compiler;
import org.gololang.gldt.jdt.compiler.CompilerCompilationException;
import org.gololang.gldt.jdt.compiler.CompilerResult;

/**
 * 
 */
public class ProxyCompiler implements Compiler {

  private Method compilerCompilerMethod;
  private Method resultByteCodeMethod;
  private Method resultPackageAndClassMethod;
  private Method packageAndClassClassNameMethod;
  private Method packageAndClassPackageNameMethod;
  private Method exceptionProblemsMethod;
  private Method problemDescriptionMethod;
  private Method problemTokenMethod;
  private Field tokenBeginLineField;
  private Field tokenBeginColumnField;
  private Field tokenStartOffsetField;
  private Field tokenEndLineField;
  private Field tokenEndColumnField;
  private Field tokenEndOffsetField;
  private Field tokenImageField;
  private Object compiler;

  public ProxyCompiler(Method compilerCompilerMethod,
                       Method resultByteCodeMethod,
                       Method resultPackageAndClassMethod,
                       Method packageAndClassClassNameMethod,
                       Method packageAndClassPackageNameMethod,
                       Method exceptionProblemsMethod,
                       Method problemDescriptionMethod,
                       Method problemTokenMethod,
                       Field tokenBeginLineField,
                       Field tokenBeginColumnField,
                       Field tokenStartOffsetField,
                       Field tokenEndLineField,
                       Field tokenEndColumnField,
                       Field tokenEndOffsetField,
                       Field tokenImageField,
                       Object compiler) {
    this.compilerCompilerMethod = compilerCompilerMethod;
    this.resultByteCodeMethod = resultByteCodeMethod;
    this.resultPackageAndClassMethod = resultPackageAndClassMethod;
    this.packageAndClassClassNameMethod = packageAndClassClassNameMethod;
    this.packageAndClassPackageNameMethod = packageAndClassPackageNameMethod;
    this.exceptionProblemsMethod = exceptionProblemsMethod;
    this.problemDescriptionMethod = problemDescriptionMethod;
    this.problemTokenMethod = problemTokenMethod;
    this.tokenBeginLineField = tokenBeginLineField;
    this.tokenBeginColumnField = tokenBeginColumnField;
    this.tokenStartOffsetField = tokenStartOffsetField;
    this.tokenEndLineField = tokenEndLineField;
    this.tokenEndColumnField = tokenEndColumnField;
    this.tokenEndOffsetField = tokenEndOffsetField;
    this.tokenImageField = tokenImageField;
    this.compiler = compiler;
  }

  /* (non-Javadoc)
   * @see org.golo_lang.gldt.jdt.compiler.Compiler#process(org.eclipse.core.resources.IFile)
   */
  public List<CompilerResult> process(IFile goloSource)
      throws CompilerCompilationException {
    try {
      List<Object> results = (List<Object>) compilerCompilerMethod.invoke(compiler, new Object[]{goloSource.getName(), goloSource.getContents()});
      return wrap(results);
    }
    catch (Exception e) {
      Throwable cause = e;
      if (e instanceof InvocationTargetException) {
        cause = ((InvocationTargetException)e).getTargetException();
      }
      CompilerCompilationException ce = new CompilerCompilationException(e.getLocalizedMessage(), cause);
      completeException(ce, cause);
      throw ce;
    }
  }

  /**
   * Complete the description of the compilation exception with problems from the native exception.
   * 
   * @param ce the return exception
   * @param e the native Golo compiler exception
   */
  private void completeException(CompilerCompilationException ce, Throwable e) {
    try {
      List<Object> problems = (List<Object>) exceptionProblemsMethod.invoke(e, new Object[] {});
      for(Object problem : problems) {
        final Object token = problemTokenMethod.invoke(problem, new Object[] {});
        final int beginLine = (token!=null)?tokenBeginLineField.getInt(token):1;
        final int beginColumn = (token!=null)?tokenBeginColumnField.getInt(token):1;
        final int startOffset = (token!=null)?tokenStartOffsetField.getInt(token):0;
        final int endLine = (token!=null)?tokenEndLineField.getInt(token):1;
        final int endColumn = (token!=null)?tokenEndColumnField.getInt(token):1;
        final int endOffset = (token!=null)?tokenEndOffsetField.getInt(token):0;
        final String image = (token!=null)?(String) tokenImageField.get(token):""; //$NON-NLS-1$
        final String description = (String) problemDescriptionMethod.invoke(problem, new Object[] {});
        ce.report(beginLine,
                  beginColumn,
                  startOffset,
                  endLine,
                  endColumn,
                  endOffset,
                  image,
                  description);
      }
    }
    catch (IllegalAccessException e1) {}
    catch (IllegalArgumentException e1) {}
    catch (InvocationTargetException e1) {}
  }

  private List<CompilerResult> wrap(List<Object> results) {
    try {
      List<CompilerResult> wrapped = new ArrayList<CompilerResult>(results.size());
      for(Object result : results) {
        wrapped.add(wrapResult(result));
      }
      return wrapped;
    }
    catch (IllegalAccessException e) {
      throw new CompilerCompilationException(e);
    }
    catch (IllegalArgumentException e) {
      throw new CompilerCompilationException(e);
    }
    catch (InvocationTargetException e) {
      throw new CompilerCompilationException(e);
    }
  }

  private CompilerResult wrapResult(Object result) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    final byte[] data = (byte[]) resultByteCodeMethod.invoke(result, new Object[] {});
    final Object packageAndClass = resultPackageAndClassMethod.invoke(result, new Object[] {});
    final String packageName = (String) packageAndClassPackageNameMethod.invoke(packageAndClass, new Object[] {});
    final String className = (String) packageAndClassClassNameMethod.invoke(packageAndClass, new Object[] {});
    return new CompilerResult() {
      public byte[] getResult() {
        return data;
      }
      
      public String getPackageName() {
        return packageName;
      }
      
      public String getClassName() {
        return className;
      }
    };
  }
}
