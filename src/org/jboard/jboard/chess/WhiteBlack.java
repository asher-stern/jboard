package org.jboard.jboard.chess;

/**
 * Enum of white and black.
 * 
 * @author Asher Stern
 * Date: Jul 11, 2014
 *
 */
public enum WhiteBlack
{
	WHITE, BLACK ;
	
	public WhiteBlack getOther()
	{
		if (this==WHITE) return BLACK;
		else if (this==BLACK) return WHITE;
		else throw new RuntimeException();
	}
}
