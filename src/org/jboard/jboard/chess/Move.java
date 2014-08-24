package org.jboard.jboard.chess;


/**
 * Represents a move of a piece in the chess board.
 * The move holds the square where the piece has been, the square where the piece is moving to, and a promotion piece,
 * in case this move is a pawn that arrived at the last row, and is promoted. 
 * 
 * @author Asher Stern
 * Date: Jul 11, 2014
 *
 */
public class Move
{
	public Move(SquareCoordinates source, SquareCoordinates destination, Piece promotion)
	{
		super();
		this.source = source;
		this.destination = destination;
		this.promotion = promotion;
	}
	
	
	
	public SquareCoordinates getSource()
	{
		return source;
	}
	public SquareCoordinates getDestination()
	{
		return destination;
	}
	
	/**
	 * In case of promotion, returns the piece into which the moved piece is promoted.
	 * Otherwise returns null.
	 * @return
	 */
	public Piece getPromotion()
	{
		return promotion;
	}
	
	
	




	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result
				+ ((promotion == null) ? 0 : promotion.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		Move other = (Move) obj;
		if (destination == null)
		{
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (promotion != other.promotion)
			return false;
		if (source == null)
		{
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}







	private final SquareCoordinates source;
	private final SquareCoordinates destination;
	private final Piece promotion;
}
