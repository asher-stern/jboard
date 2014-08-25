package org.jboard.jboard.utilities;

import java.io.File;

/**
 * General utilities for JVM.
 *
 * @author Asher Stern
 * Date: Aug 24, 2014
 *
 */
public class JavaSystemUtilities
{
	public static File getJavaExecutable()
	{
		String javaHome = System.getProperty("java.home");
		File javaHomeDir = new File(javaHome);
		return new File(new File(javaHomeDir,"bin"),OS.programName("java"));
	}

}
