package org.jboard.jboard.chess;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A chess piece.
 * 
 * @author Asher Stern
 * Date: Jul 11, 2014
 *
 */
public enum Piece
{
	KING(false,'k'),
	QUEEN(true,'q'),
	ROOK(true,'r'),
	BISHOP(true,'b'),
	KNIGHT(true,'n'),
	PAWN(false,'p')
	;
	
	
	public boolean isValidForPromotion()
	{
		return validForPromotion;
	}
	
	public char getItsChar()
	{
		return itsChar;
	}
	
	public static Piece fromChar(char itsChar)
	{
		if (null==mapCharPiece)
		{
			synchronized (Piece.class)
			{
				if (null==mapCharPiece)
				{
					mapCharPiece = new LinkedHashMap<>();
					for (Piece piece : Piece.values())
					{
						mapCharPiece.put(piece.getItsChar(),piece);
					}
				}
			}
		}
		if (mapCharPiece.containsKey(itsChar))
		{
			return mapCharPiece.get(itsChar);
		}
		else
		{
			throw new RuntimeException("Bad character for Piece: "+itsChar);
		}
	}



	private Piece(boolean validForPromotion, char itsChar)
	{
		this.validForPromotion = validForPromotion;
		this.itsChar = itsChar;
	}
	
	private final boolean validForPromotion;
	private final char itsChar;
	
	private static Map<Character, Piece> mapCharPiece = null;
}
