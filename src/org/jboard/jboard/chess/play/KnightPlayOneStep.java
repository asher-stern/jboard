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
 * Date: Sep 21, 2014
 *
 */
public class KnightPlayOneStep extends PlayOneStep
{

	public KnightPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		final WhiteBlack myColor = board.getPositions().get(origin).getColor();
		moves = new LinkedList<Move>();
		
		for (int rowDirection : MINUS_ONE_ONE)
		{
			for (int columnDirection : MINUS_ONE_ONE)
			{
				int addToRow = rowDirection*2;
				int addToColumn = columnDirection;
				add(myColor,origin.getRow()+addToRow,(char)(origin.getColumn()+addToColumn));
				
				addToRow = rowDirection;
				addToColumn = columnDirection*2;
				add(myColor,origin.getRow()+addToRow,(char)(origin.getColumn()+addToColumn));
			}
		}
		
		return moves;
	}
	
	private void add(final WhiteBlack myColor, int row, char column)
	{
		if (PlayUtilities.getDestination(board, myColor, column, row)!=DestinationType.INVALID)
		{
			moves.add(new Move(origin,
					new SquareCoordinates(column, row),
					null));
		}
	}

	private List<Move> moves;
	
	private static final int[] MINUS_ONE_ONE = new int[]{-1,1};
}
