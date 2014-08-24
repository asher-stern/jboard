package org.jboard.jboard.chess;

import static org.jboard.jboard.Constants.*;

/**
 * Represents a square in the chess board, like a1, e2, h8, etc.
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public class SquareCoordinates
{
	public SquareCoordinates(char column, int row)
	{
		super();
		this.column = column;
		this.row = row;
		if ( (column>='a') && (column<=('a'+BOARD_SIZE-1)) ) {} else {throw new RuntimeException("Illegal column: "+column);}
		if ( (row>=1) && (row<=BOARD_SIZE) ) {} else {throw new RuntimeException("Illegal row: "+row);}
	}
	
	
	
	public char getColumn()
	{
		return column;
	}
	public int getRow()
	{
		return row;
	}
	
	public int getColumnAsZeroStartingCoordinate()
	{
		return column-'a';
	}
	public int getRowAsZeroStartingCoordinate()
	{
		return row-1;
	}
	
	



	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}



	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SquareCoordinates other = (SquareCoordinates) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}





	private final char column;
	private final int row;
}
