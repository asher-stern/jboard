package org.jboard.jboard.chessengine;

import org.jboard.jboard.chess.Move;

/**
 * A command sent from the user to make a move (represents the user's turn).
 * 
 * @author Asher Stern
 * Date: Jul 11, 2014
 *
 */
public class MoveCommand extends Command
{
	public MoveCommand(Move move)
	{
		this.commandType = CommandType.MOVE;
		this.move = move;
	}
	
	public MoveCommand getMoveCommand()
	{
		return this;
	}
	

	public Move getMove()
	{
		return move;
	}

	private final Move move;
}
