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
}
