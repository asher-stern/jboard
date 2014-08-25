package org.jboard.jboard.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author Asher Stern
 * Date: Aug 25, 2014
 *
 */
public abstract class IndependentStringBasedList
{
	public IndependentStringBasedList(String listName)
	{
		super();
		this.listName = listName;
	}
	
	public List<String> getList()
	{
		List<String> ret = new LinkedList<String>();
		Map<Integer, String> mapNumberToValue = new LinkedHashMap<Integer, String>();
		
		for (String key : getAllIndependentItems())
		{
			Integer itsNumber = keyBelongsToList(key);
			if (itsNumber!= null)
			{
				mapNumberToValue.put(itsNumber,getIndependentItem(key));
			}
		}
		List<Integer> keysAsList = new ArrayList<>(mapNumberToValue.size());
		for (Integer i : mapNumberToValue.keySet())
		{
			keysAsList.add(i);
		}
		Collections.sort(keysAsList);
		for (Integer sortedKey : keysAsList)
		{
			ret.add(mapNumberToValue.get(sortedKey));
		}
		
		return ret;
	}
	
	public void add(String item)
	{
		Set<String> allKeys = getAllIndependentItems();
		int maxNumber = -1;
		for (String key : allKeys)
		{
			Integer itsNumber = keyBelongsToList(key);
			if (itsNumber!=null)
			{
				if (itsNumber>maxNumber)
				{
					maxNumber=itsNumber;
				}
			}
		}
		String itemKey = listName+(1+maxNumber);
		putIndependentItem(itemKey,item);
		
	}
	
	/**
	 * Returns the number of the item, if it belongs to the list. Otherwise returns null.
	 * @param key
	 * @return
	 */
	private Integer keyBelongsToList(String key)
	{
		if (key.startsWith(listName))
		{
			String number = key.substring(listName.length(), key.length());
			try
			{
				return Integer.parseInt(number);
			}
			catch(NumberFormatException e)
			{
				// do nothing
			}
		}
		return null;
	}
	
	

	protected abstract Set<String> getAllIndependentItems();
	protected abstract String getIndependentItem(String key);
	protected abstract void putIndependentItem(String key, String value);
	
	protected final String listName;
}
