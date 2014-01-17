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

  public static final String GOLO_BUILDER_ID = GoloJdtPlugin.PLUGIN_ID + ".goloBuilder"; //$NON-NLS-1$
	
  public static final String GOLO_NATURE_ID = GoloJdtPlugin.PLUGIN_ID + ".goloNature"; //$NON-NLS-1$
  
  public static final String GOLO_COMPILATION_MARKER_ID = GoloJdtPlugin.PLUGIN_ID + ".goloCompilationMarker"; //$NON-NLS-1$

  /**
   * The file extension for Golo source files. The leading dot is not included.
   */
  public static final String GOLO_FILE_EXTENSION = "golo"; //$NON-NLS-1$

  public static final String GOLO_FILE_FILTER = "*." + GOLO_FILE_EXTENSION; //$NON-NLS-1$

  /**
   * Identifier of the run Golo script confguration type
   */
  public static final String GOLO_LAUNCH_SCRIPT_CONFIGURATION_TYPE = GoloJdtPlugin.PLUGIN_ID + ".launchGoloScript"; //$NON-NLS-1$
  
  /**
   * The name of the configuration parameter that stores the Golo script file name (relative
   * to the workspace).
   */
  public static final String GOLO_LAUNCH_SCRIPT_PARAMETER_NAME = "script"; //$NON-NLS-1$

  public static final String GOLO_LAUNCH_SCRIPT_PARAMETER_DEFAULT_VALUE = ""; //$NON-NLS-1$

  public static final String GOLO_CLI_CLASS_NAME = "fr.insalyon.citi.golo.cli.Main"; //$NON-NLS-1$
  
  public static final String GOLO_RUNTIME_OSGI_IDENTIFIER = "org.golo-lang.golo"; //$NON-NLS-1$
  
  public static final String ASM_OSGI_IDENTIFIER = "org.objectweb.asm"; //$NON-NLS-1$
  
  public static final String ASM_MINIMUM_VERSION = "4.0"; //$NON-NLS-1$
  
  public static final String JCOMMANDER_OSGI_IDENTIFIER = "com.beust.jcommander"; //$NON-NLS-1$

  /**
   * Identifier for the Golo library container. Mathes the id in plugin.xml.
   */
  public static final String GOLO_LIBRARY_CONTAINER_IDENTIFIER = "org.golo-lang.gldt.jdt.GOLO_LIBRARY"; //$NON-NLS-1$

  public static final String[] AETHER_BUNDLES_IDS = {
    "org.eclipse.aether.api", //$NON-NLS-1$
    "org.eclipse.aether.maven", //$NON-NLS-1$
    "org.eclipse.aether.impl", //$NON-NLS-1$
    "org.eclipse.aether.connector.basic", //$NON-NLS-1$
    "org.eclipse.aether.spi", //$NON-NLS-1$
    "org.eclipse.aether.transport.http" //$NON-NLS-1$
  };
  
  /**
   * Name of the Golo library that deals with latest bits of Golo (need Aether to resolve)
   */
  public static final String GOLO_LATEST_LIBRARY_ID = "LATEST"; //$NON-NLS-1$
}
