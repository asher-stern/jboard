package org.jboard.jboard.sandbox;

import java.util.prefs.Preferences;

/**
 * 
 * @author Asher Stern
 * Date: Aug 25, 2014
 *
 */
public class TryPreferences
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new TryPreferences().go();
		}
		catch(Throwable t)
		{
			t.printStackTrace(System.out);
		}

	}
	
	public void go()
	{
		Preferences p = Preferences.userNodeForPackage(TryPreferences.class);
		p.put("a", "1");
		System.out.println(p.get("a", "def"));
	}

}
