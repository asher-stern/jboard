package org.jboard.jboard.sandbox;

public class TryLambda
{

	public static void main(String[] args)
	{
		try
		{
			new TryLambda().go();
		}
		catch(Throwable t)
		{
			t.printStackTrace(System.out);
		}

	}
	
	public void go()
	{
		I1 i1 = p -> p+1;
		useI1(i1);
	}

	
	public static interface I1
	{
		int f(int i);
	}
	
	public void useI1(I1 i1)
	{
		System.out.println(i1.f(3));
	}
	
	
	

}
