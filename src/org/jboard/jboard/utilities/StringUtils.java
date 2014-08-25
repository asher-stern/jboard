package org.jboard.jboard.utilities;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Generic utilities for strings.
 * 
 * @author Asher Stern
 * Date: Aug 4, 2014
 *
 */
public class StringUtils
{
	/**
	 * Tests whether the given string starts with any of the given prefixes.
	 * @param iterable a collection of prefixes.
	 * @param str a given string to be tested.
	 * @return true if the string starts with any of the given prefixes.
	 */
	public static boolean anyStartsWith(Iterable<String> iterable, String str)
	{
		return anyStartsWith(iterable,str,null);
	}

	/**
	 * Tests whether the given string starts with any of the given prefixes.
	 * @param iterable a collection of prefixes.
	 * @param str a given string to be tested.
	 * @param outputString The prefix from the given collection of prefixes, which is indeed a prefix
	 * of the given string. This parameter might hold null if the method returns false.
	 * @return true if the string starts with any of the given prefixes.
	 */
	public static boolean anyStartsWith(Iterable<String> iterable, String str, Container<String> outputString)
	{
		outputString.set(null);
		for (String inIterable : iterable)
		{
			if (startsWithIgnoreCase(str,inIterable))
			{
				if (outputString!=null) {outputString.set(inIterable);}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether the main string starts with the given prefix, ignoring letter-case in both.
	 * @param main
	 * @param prefix
	 * @return
	 */
	public static boolean startsWithIgnoreCase(String main, String prefix)
	{
		return main.toLowerCase().startsWith(prefix.toLowerCase());
	}
	
	/**
	 * Returns a localized string for the given key.
	 * @param str
	 * @return
	 */
	public static String localized(String str)
	{
		return ResourceBundle.getBundle("jboard", Locale.getDefault(), UTF8RsourceBundleControl.INSTANCE).getString(str);
	}

}
