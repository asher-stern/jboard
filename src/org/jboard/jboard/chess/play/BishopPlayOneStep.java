package org.jboard.jboard.chess.play;

import java.util.LinkedList;
import java.util.List;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

public class BishopPlayOneStep extends RookOrBishopPlayOneStep
{

	public BishopPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		allMoves = new LinkedList<Move>();
		WhiteBlack myColor = board.getPositions().get(origin).getColor();
		
		PlayUtilities.ProceedColumn proceedColumn = c -> (char)(c+1);
		PlayUtilities.ProceedRow proceedRow = r -> r+1;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);
		
		proceedColumn = c -> (char)(c+1);
		proceedRow = r -> r-1;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);

		proceedColumn = c -> (char)(c-1);
		proceedRow = r -> r+1;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);

		proceedColumn = c -> (char)(c-1);
		proceedRow = r -> r-1;
		calculateSpecifiedDirection(myColor,proceedColumn,proceedRow);
		
		return allMoves;
		
	}

}
