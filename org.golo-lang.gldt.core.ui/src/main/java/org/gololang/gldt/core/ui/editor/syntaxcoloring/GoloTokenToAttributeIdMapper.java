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
package org.gololang.gldt.core.ui.editor.syntaxcoloring;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;

/**
 * 
 */
public class GoloTokenToAttributeIdMapper extends
		DefaultAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		System.out.println(tokenName);
		if ("RULE_STRING".equals(tokenName) || "RULE_CHAR".equals(tokenName)) {
			return DefaultHighlightingConfiguration.STRING_ID;
		} else if ("RULE_COMMENT".equals(tokenName)) {
			return DefaultHighlightingConfiguration.COMMENT_ID;
		} else if ("RULE_NUMBER".equals(tokenName) || "RULE_LONG_NUMBER".equals(tokenName) || "RULE_FLOAT".equals(tokenName) || "RULE_FLOATING_NUMBER".equals(tokenName)) {
			return DefaultHighlightingConfiguration.NUMBER_ID;
		} else if ("RULE_NEWLINE".equals(tokenName)) {
			return DefaultHighlightingConfiguration.DEFAULT_ID;
		} else if ("RULE_IDENTIFIER".equals(tokenName)) {
			return DefaultHighlightingConfiguration.DEFAULT_ID;
		} else if (tokenName.startsWith("RULE_") && !(tokenName.endsWith("OPERATOR") || "RULE_INVOCATION".equals(tokenName))) {
			return DefaultHighlightingConfiguration.KEYWORD_ID;
		}
		return super.calculateId(tokenName, tokenType);
	}

}
