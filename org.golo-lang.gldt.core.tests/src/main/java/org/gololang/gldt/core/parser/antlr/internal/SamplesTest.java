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
	
	@Test
	public void checkThatCollectionLiteralsParsingIsOK() {
	  ParserTestHelper.testFile("/samples/collection-literals.golo");
	}
	
	@Test
	public void checkThatStructsParsingIsOK() {
	  ParserTestHelper.testFile("/samples/structs.golo");
	}
	
	@Test
	public void checkThatAdaptersParsingIsOK() {
	  ParserTestHelper.testFile("/samples/adapters.golo");
	}
}
