gldt
===

Golo Language Development Tools (Eclipse)

<b>Manual installation</b>

Once you've cloned the repository, run mvn package.

This should produce an update site under org.gololang.gldt.site/target/repository.

Under Eclipse, add this folder as a local software site and give it a name (GLDT for example).

Then, click on Help -> Install new software, and select the software site you just created.

After a short time, you should see two items, GLDT and Xtext.

If Xtext is not installed on your Eclipse, select the two items and proceed to the installation.

If Xtext is already installed on your Eclipse, select only the GLDT item and proceed to the installation.

Restart Eclipse.

GLDT should be installed and the GDLT editor should be run on each .golo file.

<b>Automatic installation</b>

Under Eclipse, click on Help -> Install new software, and enter the http://gldt-update-site.golo-lang.org/nightly URL

and hit the Enter key. After a short time, you should see GLDT displayed in the tree, select it and click on the Next

button. When the feature Golo Development Toolkit Core is displayed, click on the Next button, accept the license agreement

and click on the Finish button. Restart Eclipse.

GLDT should be installed and the GDLT editor should be run on each .golo file.

<b><a name"JDT">JDT Integration</a></b>

GLDT now integrates with Eclipse JDT. If you create a Java project and set the Golo nature, then each .golo file

that is located into one of the Java source folders with gets compiled.

However, GLDT does not embed the Golo compiler as Golo is changing today at a high pace. So you must download yourself 

the Golo jar file and set it in your project classpath. If the Golo jar file is not in your project classpath, then

compilation of the .golo files with failed and you will get a dialog error.

To set the Golo nature to your Java project, right click on your project, and select Configure -> Add Golo nature

<b>IRC<b>

Log into the #gldt IRC channel on irc.freenode.net or with <a href="http://webchat.freenode.net">web irc client</a> 

![Build Status](https://travis-ci.org/golo-lang/gldt.png?branch=master) https://travis-ci.org/golo-lang/gldt
