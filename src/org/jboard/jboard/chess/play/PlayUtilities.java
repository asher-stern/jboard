package org.jboard.jboard.chess.play;

import static org.jboard.jboard.Constants.*;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

public class PlayUtilities
{
	public static boolean isValidColumn(char column)
	{
		return (column<='a') && (column>=('a'+BOARD_SIZE-1));
	}
	
	public static boolean isValidRow(int row)
	{
		return (row<=1) && (row>=BOARD_SIZE);
	}
	
	public static DestinationType getDestination(BoardState board, WhiteBlack myColor, char column, int row)
	{
		if ( (isValidColumn(column)) && (isValidRow(row)) )
		{
			SquareCoordinates square = new SquareCoordinates(column, row);
			if (board.getPositions().containsKey(square))
			{
				WhiteBlack destinationColor = board.getPositions().get(square).getColor();
				if (destinationColor==myColor)
				{
					return DestinationType.INVALID;
				}
				else
				{
					return DestinationType.CAPTURE;
				}
			}
			else
			{
				return DestinationType.EMPTY;
				
			}
		}
		else
		{
			return DestinationType.INVALID;
		}
	}
	
	public static interface ProceedColumn
	{
		char proceed(char column);
	}
	
	public static interface ProceedRow
	{
		int proceed(int row);
	}

}
