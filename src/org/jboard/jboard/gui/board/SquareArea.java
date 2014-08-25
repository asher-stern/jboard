package org.jboard.jboard.gui.board;

/**
 * Coordinates of a square on the chess-board in the {@link BoardPanel}.
 * 
 * @author Asher Stern
 * Date: Jul 17, 2014
 *
 */
public class SquareArea
{
	public SquareArea(int xStart, int yStart, int xEnd, int yEnd)
	{
		super();
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}
	
	
	
	public int getxStart()
	{
		return xStart;
	}
	public int getyStart()
	{
		return yStart;
	}
	public int getxEnd()
	{
		return xEnd;
	}
	public int getyEnd()
	{
		return yEnd;
	}



	private final int xStart;
	private final int yStart;
	private final int xEnd;
	private final int yEnd;
}
