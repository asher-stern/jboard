package org.jboard.jboard.sandbox;

public class Uncaught
{

	public static void main(String[] args)
	{
		Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
		{
			
			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				System.out.println("Hello world!");
				
			}
		});
		
		throw new RuntimeException("Hello kitty.");
		
		//System.out.println("Bye Bye");

	}

}
