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
package org.gololang.gldt.core.ui.outline;

import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;
import org.gololang.gldt.core.golo.AugmentDeclaration;
import org.gololang.gldt.core.golo.CompilationUnit;
import org.gololang.gldt.core.golo.FunctionDeclaration;
import org.gololang.gldt.core.golo.GoloPackage;
import org.gololang.gldt.core.golo.ImportDeclaration;
import org.gololang.gldt.core.golo.ModuleDeclaration;
import org.gololang.gldt.core.golo.StructDeclararation;
import org.gololang.gldt.core.golo.TopLevelDeclaration;

/**
 * Customization of the default outline structure.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#outline
 */
public class GoloOutlineTreeProvider extends org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider {
    protected void _createChildren(DocumentRootNode parentNode, CompilationUnit compilationUnit) {
    	createNode(parentNode, compilationUnit.getModule());
        if (compilationUnit.getImports().size() > 0) {
        	createEStructuralFeatureNode(parentNode, 
        			                     compilationUnit,
        			                     GoloPackage.Literals.COMPILATION_UNIT__IMPORTS,
        			                     null,
        			                     "Imports",
        			                     false);
        }
    	createEStructuralFeatureNode(parentNode, 
                compilationUnit,
                GoloPackage.Literals.COMPILATION_UNIT__DECLARATIONS,
                null,
                "Declarations",
                false);
    }
    
    /**
     * Displays a module declaration with only the name.
     * 
     * @param moduleDeclaration
     * @return the name as string
     */
    protected String _text(ModuleDeclaration moduleDeclaration) {
    	return moduleDeclaration.getName();
    }
    
    /**
     * Displays a module declaration alone.
     * 
     * @param moduleDeclaration
     * @return always true
     */
    protected boolean _isLeaf(ModuleDeclaration moduleDeclaration) {
    	return true;
    }

    /**
     * Displays an import declaration with only the name.
     * 
     * @param importDeclaration
     * @return the name as string
     */
    protected String _text(ImportDeclaration importDeclaration) {
    	return importDeclaration.getName();
    }
    
    /**
     * Displays an import declaration alone.
     * 
     * @param importDeclaration
     * @return always true
     */
    protected boolean _isLeaf(ImportDeclaration importDeclaration) {
    	return true;
    }
    
    /**
     * Displays a top level declaration as a function declaration.
     * 
     * @param topLevelDeclaration
     * @return the name as string
     */
    protected String _text(TopLevelDeclaration topLevelDeclaration) {
      if (topLevelDeclaration.getFunctionDecl() != null) {
        return _text(topLevelDeclaration.getFunctionDecl());
      } else {
        return _text(topLevelDeclaration.getStruct());
      }
    }
    
    /**
     * Displays a top level declaration only as a function name.
     * 
     * @param importDeclaration
     * @return always true
     */
    protected boolean _isLeaf(TopLevelDeclaration topLevelDeclaration) {
    	return true;
    }
    
    /**
     * Displays a function declaration as a name and parameters.
     * 
     * @param functionDeclaration
     * @return the name and parameters as string
     */
    protected String _text(FunctionDeclaration functionDeclaration) {
    	StringBuilder sb = new StringBuilder();
		if (functionDeclaration != null) {
			sb.append(functionDeclaration.getName());
			sb.append('(');
			if (functionDeclaration.getFunction() != null
					&& functionDeclaration.getFunction().getArguments() != null) {
				boolean first = true;
				for (String parm : functionDeclaration.getFunction()
						.getArguments().getParms()) {
					if (!first) {
						sb.append(',');
					}
					sb.append(parm);
					first = false;
				}
				if (functionDeclaration.getFunction().getVarargsToken() != null) {
					sb.append(',');
					sb.append(functionDeclaration.getFunction()
							.getVarargsToken());
				}
			}
			sb.append(')');
		}
		return sb.toString();
    }
    
    /**
     * Displays a function declaration alone.
     * 
     * @param functionDeclaration
     * @return always true
     */
    protected boolean _isLeaf(FunctionDeclaration functionDeclaration) {
    	return true;
    }
    
    /**
     * Displays an augment declaration as the target class name.
     * 
     * @param augmentDeclaration
     * @return the name and parameters as string
     */
    protected String _text(AugmentDeclaration augmentDeclaration) {
    	return augmentDeclaration.getTarget();
    }

    protected boolean _isLeaf(AugmentDeclaration augmentDeclaration) {
    	return augmentDeclaration.getFunc().size() == 0;
    }
    
    /**
     * Create children nodes for an augment declaration as the function declarations.
     * 
     * @param augmentDeclaration
     * @return the name and parameters as string
     */
    protected void _createChildren(IOutlineNode parentNode, AugmentDeclaration augmentDeclaration) {
    	for(FunctionDeclaration functionDeclaration : augmentDeclaration.getFunc()) {
    		createNode(parentNode, functionDeclaration);
    	}
    }
    
    /**
     * Displays a structure declaration as a name and members.
     * 
     * @param structDeclaration
     * @return the name and members as string
     */
    protected String _text(StructDeclararation structDeclaration) {
      StringBuilder sb = new StringBuilder();
      if (structDeclaration != null) {
        sb.append(structDeclaration.getName());
        sb.append('(');
        boolean first = true;
        for(String member : structDeclaration.getMember()) {
          if (!first) {
            sb.append(',');
          }
          sb.append(member);
          first = false;
        }
        sb.append(')');
      }
      return sb.toString();
    }

    protected boolean _isLeaf(StructDeclararation structDeclaration) {
      return true;
    }
}
