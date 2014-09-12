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
public class KinkPlayOneStep extends PlayOneStep
{

	public KinkPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		List<Move> ret = new LinkedList<Move>();
		WhiteBlack myColor = board.getPositions().get(origin).getColor();
		
		for (int addToRow : new int[]{-1,0,1})
		{
			for (int addToColumn : new int[]{-1,0,1})
			{
				if ( (addToRow==0) && (addToColumn==0) ) {}
				else
				{
					char column = (char)(origin.getColumn()+addToColumn);
					int row = origin.getRow()+addToRow;
					DestinationType destinationType = PlayUtilities.getDestination(board, myColor, column, row);
					if (destinationType!=DestinationType.INVALID)
					{
						ret.add(new Move(origin, new SquareCoordinates(column, row), null));
					}
				}
			}
		}
		
		return ret;
	}

}
