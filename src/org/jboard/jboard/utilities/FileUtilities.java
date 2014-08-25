package org.jboard.jboard.utilities;

import java.io.File;

/**
 * Utilities for files.
 *
 * @author Asher Stern
 * Date: Aug 24, 2014
 *
 */
public class FileUtilities
{
	public static final char EXTENSION_MARKER = '.';
	
	public static String getExtension(File file)
	{
		String name = file.getAbsolutePath();
		int index = name.lastIndexOf(EXTENSION_MARKER);
		if (index<=0)
		{
			return "";
		}
		else
		{
			if ((index+1)<name.length())
			{
				return name.substring(index+1);
			}
			else
			{
				return "";
			}
		}
	}
}
