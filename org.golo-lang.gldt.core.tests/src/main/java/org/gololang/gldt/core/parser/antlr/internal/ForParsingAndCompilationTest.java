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
package org.gololang.gldt.core.parser.antlr.internal;

import org.junit.Test;

public class ForParsingAndCompilationTest {

	@Test
	public void checkThatAugmentationsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/augmentations.golo");
	}
	
	@Test
	public void checkThatClosuresParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/closures.golo");
	}
	
	@Test
	public void checkThatConditionalsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/conditionals.golo");
	}
	
	@Test
	public void checkThatExceptionsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/exceptions.golo");
	}
	
	@Test
	public void checkThatFibonacciParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/fibonacci.golo");
	}
	
	@Test
	public void checkThatFunctionCallParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/function-call.golo");
	}
	
	 @Test
	  public void checkThatStructParsingIsOK() {
	    ParserTestHelper.testFile("/for-parsing-and-compilation/struct.golo");
	  }
	
	@Test
	public void checkThatFunctionsWithReturnsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/functions-with-returns.golo");
	}
	
	@Test
	public void checkThatLoopBreaksParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/loop-breaks.golo");
	}
	
	@Test
	public void checkThatLoopsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/loops.golo");
	}
	
	@Test
	public void checkThatMethodCallParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/method-call.golo");
	}
	
	@Test
	public void checkThatModuleCompactParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/module-compact.golo");
	}
	
	@Test
	public void checkThatModuleWithCommentsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/module-with-comments.golo");
	}
	
	@Test
	public void checkThatModuleWithSpacesParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/module-with-spaces.golo");
	}
	
	@Test
	public void checkThatOperatorsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/operators.golo");
	}
	
	@Test
	public void checkThatSimpleReturnsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/simple-returns.golo");
	}
	
	@Test
	public void checkThatVariablesParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/variables.golo");
	}
	
	@Test
	public void checkThatMultistringsParsingIsOK() {
		ParserTestHelper.testFile("/for-parsing-and-compilation/multistrings.golo");
	}
	
  @Test
  public void checkThatCollectionLiteralsParsingIsOK() {
    ParserTestHelper.testFile("/for-parsing-and-compilation/collection-literals.golo");
  }
  
  @Test
  public void checkThatDocIsOK() {
    ParserTestHelper.testFile("/for-parsing-and-compilation/doc.golo");
  }

}
