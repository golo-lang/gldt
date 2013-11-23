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

public class ForExecutionTest {

	@Test
	public void checkThatArraysParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/arrays.golo");
	}
	
	@Test
	public void checkThatAugmentationsExternalSourceParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/augmentations-external-source.golo");
	}
	
	@Test
	public void checkThatAugmentationsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/augmentations.golo");
	}
	
	@Test
	public void checkThatCallJavaObjectsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/call-java-objects.golo");
	}
	
	@Test
	public void checkThatClosuresParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/closures.golo");
	}
	
	@Test
	public void checkThatConditionalsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/conditionals.golo");
	}
	
	@Test
	public void checkThatContinueAndBreakParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/continue-and-break.golo");
	}
	
	@Test
	public void checkThatDynamicObjectsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/dynamic-objects.golo");
	}
	
	@Test
	public void checkThatExceptionsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/exceptions.golo");
	}
	
	@Test
	public void checkThatAFailureAssignConstantParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/failure-assign-constant.golo");
	}
	
	@Test
	public void checkThatFailureAssignToUndeclaredReferenceParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/failure-assign-to-undeclared-reference.golo");
	}
	
	@Test
	public void checkThatFailureInvalidBreakParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/failure-invalid-break.golo");
	}
	
	@Test
	public void checkThatFailureUndeclaredParameterParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/failure-undeclared-parameter.golo");
	}
	
	@Test
	public void checkThatFailureWrongScopeParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/failure-wrong-scope.golo");
	}
	
	@Test
	public void checkThatFibonacciRecursiveParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/fibonacci-recursive.golo");
	}
	
	@Test
	public void checkThatImportsMetadataParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/imports-metadata.golo");
	}
	
	@Test
	public void checkThatLoopingsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/loopings.golo");
	}
	
	@Test
	public void checkThatMethodInvocationsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/method-invocations.golo");
	}
	
	@Test
	public void checkThatOperatorsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/operators.golo");
	}
	
	@Test
	public void checkThatOverloadingParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/overloading.golo");
	}
	
	@Test
	public void checkThatParameterlessFunctionCallsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/parameterless-function-calls.golo");
	}
	
	@Test
	public void checkThatReturnsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/returns.golo");
	}
	
	@Test
	public void checkThatVarargsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/varargs.golo");
	}
	
	@Test
	public void checkThatVariableAssignmentsParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/variable-assignments.golo");
	}
	
	@Test
	public void checkThatBooleansParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/booleans.golo");
	}
	
	@Test
	public void checkThatDynamicEvaluationParsingIsOK() {
		ParserTestHelper.testFile("/for-execution/dynamic-evaluation.golo");
	}
	
	 @Test
	  public void checkThatCollectionLiteralsParsingIsOK() {
	    ParserTestHelper.testFile("/for-execution/collection-literals.golo");
	  }
	 
   @Test
   public void checkThatNumericLiteralsParsingIsOK() {
     ParserTestHelper.testFile("/for-execution/numeric-literals.golo");
   }
   
   @Test
   public void checkThatFailureNumericDoubleUnderscoreParsingIsKO() {
     ParserTestHelper.testFile("/for-execution/failure-numeric-double-underscore.golo", true);
   }
   
   @Test
   public void checkThatFailureNumericTrailingUnderscoreParsingIsKO() {
     ParserTestHelper.testFile("/for-execution/failure-numeric-trailing-underscore.golo", true);
   }
   
   @Test
   public void checkThatFailureMissingRefInClosureParsingIsOK() {
     ParserTestHelper.testFile("/for-execution/failure-missing-ref-in-closure.golo");
   }

   @Test
   public void checkThatStructsParsingIsOK() {
     ParserTestHelper.testFile("/for-execution/structs.golo");
   }

   @Test
   public void checkThatAdaptersParsingIsOK() {
     ParserTestHelper.testFile("/for-execution/adapters.golo");
   }
   
   @Test
   public void checkThatSAMParsingIsOK() {
     ParserTestHelper.testFile("/for-execution/sam.golo");
   }
}
