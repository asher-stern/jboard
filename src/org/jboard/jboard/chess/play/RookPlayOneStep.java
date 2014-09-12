package org.jboard.jboard.chess.play;

import java.util.LinkedList;
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
public class RookPlayOneStep extends RookOrBishopPlayOneStep
{

	public RookPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		allMoves = new LinkedList<Move>();
		WhiteBlack myColor = board.getPositions().get(origin).getColor();
		
		PlayUtilities.ProceedColumn proceedColumn = c -> c;
		PlayUtilities.ProceedRow proceedRow = r -> r+1;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);
		
		proceedColumn = c -> c;
		proceedRow = r -> r-1;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);

		proceedColumn = c -> (char)(c+1);
		proceedRow = r -> r;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);

		proceedColumn = c -> (char)(c-1);
		proceedRow = r -> r;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);
		
		return allMoves;
	}
	
	

}
