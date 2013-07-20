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
package org.gololang.gldt.core.ui.contentassist;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CompoundElement;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.common.ui.contentassist.TerminalsProposalProvider;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;
import org.eclipse.xtext.util.Strings;
import org.gololang.gldt.core.ui.contentassist.AbstractGoloProposalProvider;

/**
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#contentAssist on how to customize content assistant
 */
public class GoloProposalProvider extends AbstractGoloProposalProvider {

	@Inject
	TerminalsProposalProvider delegate;

	private String getAssignedFeature(RuleCall call) {
		Assignment ass = GrammarUtil.containingAssignment(call);
		if (ass != null) {
			String result = ass.getFeature();
			if (result.equals(result.toLowerCase()))
				result = Strings.toFirstUpper(result);
			return result;
		}
		return null;
	}

	private void createNumericProposal(ContentAssistContext context, ICompletionProposalAcceptor acceptor,
			RuleCall ruleCall, String feature,	Object value) {
		String proposalText = getValueConverter().toString(value, ruleCall.getRule().getName());
		String displayText = proposalText + " - " + ruleCall.getRule().getName();
		if (feature != null)
			displayText = proposalText + " - " + feature;
		ICompletionProposal proposal = createCompletionProposal(proposalText, displayText, null, context);
		if (proposal instanceof ConfigurableCompletionProposal) {
			ConfigurableCompletionProposal configurable = (ConfigurableCompletionProposal) proposal;
			configurable.setSelectionStart(configurable.getReplacementOffset());
			configurable.setSelectionLength(proposalText.length());
			configurable.setAutoInsertable(false);
			configurable.setSimpleLinkedMode(context.getViewer(), '\t', ' ');
		}
		acceptor.accept(proposal);
	}
	
	public void complete_INT(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
     	String feature = getAssignedFeature(ruleCall);
		createNumericProposal(context, acceptor, ruleCall, feature, 1);
	}

	public void complete_LONG(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
     	String feature = getAssignedFeature(ruleCall);
		createNumericProposal(context, acceptor, ruleCall, feature, 1L);
	}

	@Override
	public void complete_FLOATING_NUMBER(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		String feature = getAssignedFeature(ruleCall);
		createNumericProposal(context, acceptor, ruleCall, feature, 1.0d);
	}

	@Override
	public void complete_FLOAT(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		String feature = getAssignedFeature(ruleCall);
		createNumericProposal(context, acceptor, ruleCall, feature, 1.0f);
	}

	@Override
	public void complete_IDENTIFIER(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		delegate.complete_ID(model, ruleCall, context, acceptor);
	}

	@Override
	public void complete_MODULE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_IMPORT(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_FUNCTION(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_LOCAL(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_IF(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_IN(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_MATCH(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_OTHERWISE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_LET(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_RETURN(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_ELSE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_WHILE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_FOR(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_FOREACH(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_THROW(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_TRY(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_CATCH(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_FINALLY(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_CASE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_WHEN(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_THEN(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_AUGMENT(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		for(AbstractElement e : ((CompoundElement)ruleCall.getRule().getAlternatives()).getElements()) {
			completeKeyword((Keyword) e, context, acceptor);
		}
	}
	
	@Override
	public void complete_BREAK(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_CONTINUE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_ASSOCIATIVE_OPERATOR(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		for(AbstractElement e : ((CompoundElement)ruleCall.getRule().getAlternatives()).getElements()) {
			completeKeyword((Keyword) e, context, acceptor);
		}
	}
	
	@Override
	public void complete_COMMUTATIVE_OPERATOR(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		for(AbstractElement e : ((CompoundElement)ruleCall.getRule().getAlternatives()).getElements()) {
			completeKeyword((Keyword) e, context, acceptor);
		}
	}
	
	@Override
	public void complete_UNARY_OPERATOR(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_VAR(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_NUMBER(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		complete_INT(model, ruleCall, context, acceptor);
	}
	
	@Override
	public void complete_LONG_NUMBER(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		complete_LONG(model, ruleCall, context, acceptor);
	}
	
	@Override
	public void complete_STRING(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		delegate.complete_STRING(model, ruleCall, context, acceptor);
	}
	
	@Override
	public void complete_CHAR(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		//TODO
	}

	@Override
	public void complete_NULL(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_TRUE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}
	
	@Override
	public void complete_FALSE(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeKeyword((Keyword) ruleCall.getRule().getAlternatives(), context, acceptor);
	}

	@Override
	public void complete_INVOCATION(EObject model, RuleCall ruleCall,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		for(AbstractElement e : ((CompoundElement)ruleCall.getRule().getAlternatives()).getElements()) {
			completeKeyword((Keyword) e, context, acceptor);
		}
	}
}
