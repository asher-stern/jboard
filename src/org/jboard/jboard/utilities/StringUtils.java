package org.jboard.jboard.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import org.jboard.jboard.Constants;

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
		if (outputString!=null) {outputString.set(null);}
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
	 * @param str a key, which will be searched for in the jboard.properties file and
	 * jboard_XX.properties file (XX stands for language key).
	 * @return The string to be displayed to the user.
	 */
	public static String localized(String str)
	{
		return ResourceBundle.getBundle("jboard", Locale.getDefault(), UTF8RsourceBundleControl.INSTANCE).getString(str);
	}
	
	public static String collectionToString(Collection<String> collection, String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		boolean firstIteration = true;
		for (String str : collection)
		{
			if (firstIteration){firstIteration=false;}
			else {sb.append(delimiter);}
			
			sb.append(str);
		}
		return sb.toString();
	}
	
	
	/**
	 * Returns jboard version.
	 * @return jboard version.
	 */
	public static String getVersion()
	{
		String version = "";
		InputStream stream = null;
		try
		{
			stream = StringUtils.class.getResourceAsStream(Constants.VERSION_RESOURCE_PATH);
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
			{
				version = reader.readLine();
				if (version==null)
				{
					version = "";
				}
				version = version.trim();
			}
		}
		catch(Exception e)
		{
			version = "";
		}
		finally
		{
			if (stream!=null)
			{
				try{stream.close();} catch (IOException e) {}
			}
		}
		return version;
	}
	
	public static String getStackTrace(Throwable t)
	{
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stackTraceElements = t.getStackTrace();
		
		boolean firstIteration = true;
		for (StackTraceElement element : stackTraceElements)
		{
			if (firstIteration) {firstIteration=false;}
			else {sb.append("\n");}
			sb.append(element.toString());
		}
		return sb.toString();
	}
}
