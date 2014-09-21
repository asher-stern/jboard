package org.jboard.jboard.chess.move_parse;

import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 21, 2014
 *
 */
public class MoveInformation
{
	public MoveInformation(WhiteBlack color, SquareCoordinates destination,
			char originColumn, int originRow, Piece piece, Piece promotion)
	{
		super();
		this.color = color;
		this.destination = destination;
		this.originColumn = originColumn;
		this.originRow = originRow;
		this.piece = piece;
		this.promotion = promotion;
	}
	
	
	public WhiteBlack getColor()
	{
		return color;
	}
	public SquareCoordinates getDestination()
	{
		return destination;
	}
	public Character getOriginColumn()
	{
		return originColumn;
	}
	public Integer getOriginRow()
	{
		return originRow;
	}
	public Piece getPiece()
	{
		return piece;
	}
	public Piece getPromotion()
	{
		return promotion;
	}



	private final WhiteBlack color;
	private final SquareCoordinates destination;
	private final Character originColumn; // may be null
	private final Integer originRow; // may be null
	private final Piece piece;
	private final Piece promotion;
}
