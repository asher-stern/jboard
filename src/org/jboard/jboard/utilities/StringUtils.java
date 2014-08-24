package org.jboard.jboard.utilities;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Asher Stern
 * Date: Aug 4, 2014
 *
 */
public class StringUtils
{
	public static boolean anyStartsWith(Iterable<String> iterable, String str)
	{
		return anyStartsWith(iterable,str,null);
	}

	public static boolean anyStartsWith(Iterable<String> iterable, String str, Container<String> outputString)
	{
		for (String inIterable : iterable)
		{
			if (str.startsWith(inIterable))
			{
				if (outputString!=null) {outputString.set(inIterable);}
				return true;
			}
		}
		return false;
	}
	
	public static String localized(String str)
	{
		return ResourceBundle.getBundle("jboard", Locale.getDefault(), UTF8RsourceBundleControl.INSTANCE).getString(str);
	}

}
