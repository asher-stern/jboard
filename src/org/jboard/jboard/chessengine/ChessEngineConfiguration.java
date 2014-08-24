package org.jboard.jboard.chessengine;

import org.jboard.jboard.chess.WhiteBlack;

/**
 * Holds chess-engine's configuration parameters.
 *
 * @author Asher Stern
 * Date: Aug 18, 2014
 *
 */
public class ChessEngineConfiguration
{
	public ChessEngineConfiguration(int depth, int time,
			WhiteBlack computerColor, boolean random)
	{
		super();
		this.depth = depth;
		this.time = time;
		this.computerColor = computerColor;
		this.random = random;
	}
	
	
	
	public int getDepth()
	{
		return depth;
	}
	public int getTime()
	{
		return time;
	}
	public WhiteBlack getComputerColor()
	{
		return computerColor;
	}
	public boolean isRandom()
	{
		return random;
	}




	private final int depth;
	private final int time;
	private final WhiteBlack computerColor;
	private final boolean random;
}
