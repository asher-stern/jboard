package org.jboard.jboard.chess;

import static org.jboard.jboard.Constants.*;

/**
 * Utilities related to the chess game rules, like a method that checks whether a given move
 * triggers promotion (of a pawn to another piece), etc.
 *
 * @author Asher Stern
 * Date: 2014
 *
 */
public class ChessRulesUtils
{
	public static SquareCoordinates WHITE_KING_SQUARE = new SquareCoordinates('e', 1);
	public static SquareCoordinates BLACK_KING_SQUARE = new SquareCoordinates('e', BOARD_SIZE);
	public static SquareCoordinates WHITE_ROOK_A_SQUARE = new SquareCoordinates('a', 1);
	public static SquareCoordinates BLACK_ROOK_A_SQUARE = new SquareCoordinates('a', BOARD_SIZE);
	public static SquareCoordinates WHITE_ROOK_H_SQUARE = new SquareCoordinates('h', 1);
	public static SquareCoordinates BLACK_ROOK_H_SQUARE = new SquareCoordinates('h', BOARD_SIZE);
	
	/**
	 * Checks whether the move represented by the parameters triggers promotion.
	 * @return true if promotion is triggered by this move.
	 */
	public static boolean promotionTriggered(SquareCoordinates source, SquareCoordinates destination, ColoredPiece piece)
	{
		if (piece.getPiece()==Piece.PAWN)
		{
			int row = destination.getRow();
			if (
					( (piece.getColor()==WhiteBlack.WHITE) && (row==BOARD_SIZE) )
					||
					( (piece.getColor()==WhiteBlack.BLACK) && (row==1) )
				)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether this move is a move of a pawn that moved two squares ahead.
	 * @param boardState
	 * @param move
	 * @return
	 */
	public static boolean inPassingMove(BoardState boardState, Move move)
	{
		ColoredPiece movingPiece = boardState.getPositions().get(move.getSource());
		if (movingPiece.getPiece()==Piece.PAWN)
		{
			if ( Math.abs((move.getSource().getRow()-move.getDestination().getRow()))>1 )
			{
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Checks whether the given move is a castling. If yes - returns the rook's move. If no - returns null.
	 * @param boardState
	 * @param move
	 * @return If this is a castling move, returns the rook's move from its square to the square near the king. If this
	 * is not a castling move, returns null.
	 */
	public static Move getCastlingRookMove(BoardState boardState, Move move)
	{
		return getCastlingRookMove(boardState, move.getSource(), move.getDestination());
	}
	

	/**
	 * Checks whether the given move is a castling. If yes - returns the rook's move. If no - returns null.
	 * @param boardState
	 * @param moveSource
	 * @param moveDestination
	 * @return If this is a castling move, returns the rook's move from its square to the square near the king. If this
	 * is not a castling move, returns null.
	 */
	public static Move getCastlingRookMove(BoardState boardState, SquareCoordinates moveSource, SquareCoordinates moveDestination)
	{
		Move ret = null;
		ColoredPiece movingPiece = boardState.getPositions().get(moveSource);
		if (movingPiece.getPiece()==Piece.KING)
		{
			int row = (movingPiece.getColor()==WhiteBlack.WHITE)?1:BOARD_SIZE;
			if (
					(moveSource.getColumn()==KING_COLUMN)
					&&
					(moveSource.getRow()==row)
				)
			{
				if (Math.abs((moveDestination.getColumn()-KING_COLUMN))==2)
				{
					SquareCoordinates rookSquare = null;
					if (moveDestination.getColumn()=='g')
					{
						rookSquare = new SquareCoordinates('h', row);
					}
					else if (moveDestination.getColumn()=='c')
					{
						rookSquare = new SquareCoordinates('a', row);
					}
					
					if (rookSquare!=null)
					{
						ColoredPiece maybeRook = boardState.getPositions().get(rookSquare);
						if (maybeRook!=null)
						{
							if (maybeRook.getColor()==movingPiece.getColor())
							{
								if (maybeRook.getPiece()==Piece.ROOK)
								{
									SquareCoordinates destinationRook = null;
									if (moveDestination.getColumn()=='g')
									{
										destinationRook = new SquareCoordinates('f', row);
									}
									else if (moveDestination.getColumn()=='c')
									{
										destinationRook = new SquareCoordinates('d', row);
									}
									else
									{
										throw new RuntimeException("Unexpected bug.");
									}
									ret = new Move(rookSquare,destinationRook,null);
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Checks whether the given move is "capture in passing" - a pawn which captures an opponent's pawn that moved two squares
	 * in the last opponent's move, and the capture is as if that pawn moved only one square.
	 * @param state
	 * @param move
	 * @return If this is a "capture in passing" move, returns the square of the captured opponent's pawn. If not,
	 * returns null.
	 */
	public static SquareCoordinates inPassingCapture(BoardState state, Move move)
	{
		SquareCoordinates ret = null;
		ColoredPiece movingPiece = state.getPositions().get(move.getSource());
		if (movingPiece.getPiece()==Piece.PAWN)
		{
			if (Math.abs( move.getDestination().getColumn()-move.getSource().getColumn() )==1 )
			{
				if (!(state.getPositions().containsKey(move.getDestination())))
				{
					int addToRow = (movingPiece.getColor()==WhiteBlack.WHITE)?(-1):1;
					SquareCoordinates captured = new SquareCoordinates(move.getDestination().getColumn(), move.getDestination().getRow()+addToRow);
					if (state.getPositions().containsKey(captured))
					{
						ColoredPiece capturedPiece = state.getPositions().get(captured);
						if ( (capturedPiece.getPiece()==Piece.PAWN) && (capturedPiece.getColor()!=movingPiece.getColor()) )
						{
							ret = captured;
						}
					}
				}
			}
		}
		return ret;
	}
	
	
	
	
}
