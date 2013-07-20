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
package org.gololang.gldt.jdt;

import org.gololang.gldt.jdt.internal.GoloJdtPlugin;

public class GoloJdtConstants {

  public static final String GOLO_BUILDER_ID = GoloJdtPlugin.PLUGIN_ID + ".goloBuilder";
	
  public static final String GOLO_NATURE_ID = GoloJdtPlugin.PLUGIN_ID + ".goloNature";
  
  public static final String GOLO_COMPILATION_MARKER_ID = GoloJdtPlugin.PLUGIN_ID + ".goloCompilationMarker";

  /**
   * The file extension for Golo source files. The leading dot is not included.
   */
  public static final String GOLO_FILE_EXTENSION = "golo";

  public static final String GOLO_FILE_FILTER = "*." + GOLO_FILE_EXTENSION;
}
