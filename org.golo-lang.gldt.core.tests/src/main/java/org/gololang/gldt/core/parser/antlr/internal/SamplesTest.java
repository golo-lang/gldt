package org.gololang.gldt.core.parser.antlr.internal;

import org.junit.Test;

public class SamplesTest {

	@Test
	public void checkThatAugmentationsParsingIsOK() {
		ParserTestHelper.testFile("/samples/augmentations.golo");
	}
	
	@Test
	public void checkThatClosuresParsingIsOK() {
		ParserTestHelper.testFile("/samples/closures.golo");
	}
	
	@Test
	public void checkThatCoinChangeParsingIsOK() {
		ParserTestHelper.testFile("/samples/coin-change.golo");
	}
	
	@Test
	public void checkThatDynamicObjectPersonParsingIsOK() {
		ParserTestHelper.testFile("/samples/dynamic-object-person.golo");
	}
	
	@Test
	public void checkThatEchoArgsParsingIsOK() {
		ParserTestHelper.testFile("/samples/echo-args.golo");
	}
	
	@Test
	public void checkThatEnumsThreadStateParsingIsOK() {
		ParserTestHelper.testFile("/samples/enums-thread-state.golo");
	}
	
	@Test
	public void checkThatFibonacciParsingIsOK() {
		ParserTestHelper.testFile("/samples/fibonacci.golo");
	}
	
	@Test
	public void checkThatHelloworldParsingIsOK() {
		ParserTestHelper.testFile("/samples/helloworld.golo");
	}
	
	@Test
	public void checkThatHttpServerParsingIsOK() {
		ParserTestHelper.testFile("/samples/http-server.golo");
	}
	
	@Test
	public void checkThatMatchingOperatorParsingIsOK() {
		ParserTestHelper.testFile("/samples/matching-operator.golo");
	}
	
	@Test
	public void checkThatMaxIntParsingIsOK() {
		ParserTestHelper.testFile("/samples/max-int.golo");
	}
	
	@Test
	public void checkThatNullSafetyParsingIsOK() {
		ParserTestHelper.testFile("/samples/null-safety.golo");
	}
	
	@Test
	public void checkThatSwingActionlistenerParsingIsOK() {
		ParserTestHelper.testFile("/samples/swing-actionlistener.golo");
	}
	
	@Test
	public void checkThatWorkersParsingIsOK() {
		ParserTestHelper.testFile("/samples/workers.golo");
	}
	
	@Test
	public void checkThatDynamicEvaluationParsingIsOK() {
		ParserTestHelper.testFile("/samples/dynamic-evaluation.golo");
	}
	
	@Test
	public void checkThatTemplatesChatWebappParsingIsOK() {
		ParserTestHelper.testFile("/samples/templates-chat-webapp.golo");
	}
}
