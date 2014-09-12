package org.jboard.jboard.chess.play;

import java.util.List;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 12, 2014
 *
 */
public abstract class RookOrBishopPlayOneStep extends PlayOneStep
{

	public RookOrBishopPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}
	
	
	protected void calculateSpecifiedDirection(WhiteBlack myColor, PlayUtilities.ProceedColumn proceedColumn, PlayUtilities.ProceedRow proceedRow)
	{
		boolean stop = false;
		char column = origin.getColumn();
		int row = origin.getRow();
		while (!stop)
		{
			column = proceedColumn.proceed(column);
			row = proceedRow.proceed(row);
			switch(PlayUtilities.getDestination(board, myColor, column, row))
			{
			case INVALID:
				stop=true;
				break;
			case EMPTY:
				allMoves.add(new Move(origin, new SquareCoordinates(column, row), null));
				break;
			case CAPTURE:
				allMoves.add(new Move(origin, new SquareCoordinates(column, row), null));
				stop=true;
			}
		}
	}
	

	protected List<Move> allMoves = null;

}
