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
package org.gololang.gldt.jdt.compiler;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class CompilerCompilationException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static class Problem {
    private String description;
    
    private int beginLine;
    
    private int beginColumn;
    
    private int beginOffset;
    
    private int endLine;
    
    private int endColumn;
    
    private int endOffset;
    
    private String text;

    public int getBeginLine() {
      return beginLine;
    }

    public int getBeginColumn() {
      return beginColumn;
    }

    public int getBeginOffset() {
      return beginOffset;
    }

    public int getEndLine() {
      return endLine;
    }

    public int getEndColumn() {
      return endColumn;
    }

    public int getEndOffset() {
      return endOffset;
    }

    public String getText() {
      return text;
    }

    public String getDescription() {
      return description;
    }
  }
  
  private List<Problem> problems = new ArrayList<CompilerCompilationException.Problem>();
  /**
   * 
   */
  public CompilerCompilationException() {
  }

  /**
   * @param message
   */
  public CompilerCompilationException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public CompilerCompilationException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public CompilerCompilationException(String message, Throwable cause) {
    super(message, cause);
  }

  public List<Problem> getProblems() {
    return problems;
  }
  
  public void report(int beginLine, int beginColumn, int beginOffset, int endLine, int endColumn,
                     int endOffset, String text, String description) {
    Problem problem = new Problem();
    problem.beginLine = beginLine;
    problem.beginColumn = beginColumn;
    problem.beginOffset = beginOffset;
    problem.endLine = endLine;
    problem.endColumn = endColumn;
    problem.endOffset = endOffset;
    problem.text = text;
    problem.description = description;
    problems.add(problem);
  }
}
