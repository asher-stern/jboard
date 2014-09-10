package org.jboard.jboard.chess.play;

import java.util.List;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 10, 2014
 *
 */
public abstract class PlayOneStep
{
	public PlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super();
		this.board = board;
		this.origin = origin;
	}
	
	public abstract List<Move> calculateAllMoves();

	protected final BoardState board;
	protected final SquareCoordinates origin;
}
