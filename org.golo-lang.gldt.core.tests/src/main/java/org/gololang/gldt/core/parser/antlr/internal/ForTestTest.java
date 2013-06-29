package org.gololang.gldt.core.parser.antlr.internal;

import org.junit.Test;

public class ForTestTest {

	@Test
	public void checkThatBootstrappedStandardAugmentationsParsingIsOK() {
		ParserTestHelper.testFile("/for-test/bootstrapped-standard-augmentations.golo");
	}
	
	@Test
	public void checkThatIncompleteParsingIsKO() {
		ParserTestHelper.testFile("/for-test/incomplete.golo", true);
	}
}
