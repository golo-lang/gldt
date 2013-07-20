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
package org.gololang.gldt.core.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.gololang.gldt.core.ui.editor.syntaxcoloring.GoloTokenToAttributeIdMapper;

/**
 * Use this class to register components to be used within the IDE.
 */
public class GoloUiModule extends org.gololang.gldt.core.ui.AbstractGoloUiModule {
	public GoloUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	/**
	 * Suppress the Add Xtext nature dialog
	 */
	public Class<? extends IXtextEditorCallback> bindIXtextEditorCallback() {
		return IXtextEditorCallback.NullImpl.class;
	}

	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindTokenToAttributeMapper() {
		return GoloTokenToAttributeIdMapper.class;
	}
}
