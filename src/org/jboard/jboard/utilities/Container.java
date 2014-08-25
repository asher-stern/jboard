package org.jboard.jboard.utilities;

/**
 * Holds an object, which can be changed by the {@link #set(Object)} method, and retrieved
 * by the {@link #get()} method.
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
