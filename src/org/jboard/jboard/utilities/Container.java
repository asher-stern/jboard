package org.jboard.jboard.utilities;

/**
 * 
 * @author Asher Stern
 * Date: Aug 4, 2014
 *
 * @param <T>
 */
public class Container<T>
{
	public Container(T t)
	{
		this.t = t;
	}
	
	public T get()
	{
		return t;
	}
	
	public void set(T t)
	{
		this.t = t;
	}


	private T t;
}
