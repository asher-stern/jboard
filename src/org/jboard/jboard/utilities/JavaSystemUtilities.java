package org.jboard.jboard.utilities;

import java.io.File;

public class JavaSystemUtilities
{
	public static File getJavaExecutable()
	{
		String javaHome = System.getProperty("java.home");
		File javaHomeDir = new File(javaHome);
		return new File(new File(javaHomeDir,"bin"),OS.programName("java"));
	}

}
