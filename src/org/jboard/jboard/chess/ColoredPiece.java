package org.jboard.jboard.chess;

/**
 * Represents a {@link Piece} and its color (represented by the {@link WhiteBlack} enum).
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public class ColoredPiece
{
	public ColoredPiece(Piece piece, WhiteBlack color)
	{
		super();
		this.piece = piece;
		this.color = color;
	}
	
	
	
	public Piece getPiece()
	{
		return piece;
	}
	public WhiteBlack getColor()
	{
		return color;
	}
	
	



	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
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
		ColoredPiece other = (ColoredPiece) obj;
		if (color != other.color)
			return false;
		if (piece != other.piece)
			return false;
		return true;
	}





	private final Piece piece;
	private final WhiteBlack color;
}
