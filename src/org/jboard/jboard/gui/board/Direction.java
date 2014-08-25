package org.jboard.jboard.gui.board;

/**
 * A direction of the mark-rectangle on the chess board.
 * 
 * @author Asher Stern
 * Date: Jul 16, 2014
 *
 */
public enum Direction
{
	LEFT, RIGHT, UP, DOWN;
	
	public Direction flipDirection()
	{
		if (this==LEFT) return RIGHT;
		else if (this==RIGHT) return LEFT;
		else if (this==UP) return DOWN;
		else if (this==DOWN) return UP;
		else throw new RuntimeException();
	}
}
