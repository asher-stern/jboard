package org.jboard.jboard.chess.play;

import java.util.LinkedList;
import java.util.List;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

import static org.jboard.jboard.chess.play.PlayUtilities.*;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 10, 2014
 *
 */
public class KingPlayOneStep extends PlayOneStep
{

	public KingPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		List<Move> ret = new LinkedList<Move>();
		
		WhiteBlack color = board.getPositions().get(origin).getColor();
		
		for (int columnStep : new int[]{-1,0,1})
		{
			for (int rowStep : new int[]{-1,0,1})
			{
				if ( (columnStep==0) && (rowStep==0) ) {}
				else
				{
					char column = (char) (origin.getColumn()+columnStep);
					int row = origin.getRow()+rowStep;
					if ( (isValidColumn(column)) && (isValidRow(row)) )
					{
						SquareCoordinates destination = new SquareCoordinates(column, row);
						boolean occupiedBySameColorPiece = false;
						if (board.getPositions().containsKey(destination))
						{
							if (board.getPositions().get(destination).getColor()==color)
							{
								occupiedBySameColorPiece=true;
							}
						}
						if (!occupiedBySameColorPiece)
						{
							ret.add(new Move(origin, destination, null));
						}
					}
					
				}
					
			}
			
		}

		return ret;
	}

}
