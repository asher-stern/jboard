package org.jboard.jboard.chess;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generates the initial board state (all pieces in their initial position).
 * 
 * @author Asher Stern
 * Date: Jul 16, 2014
 *
 */
public class InitialStateFactory
{
	public static BoardState getInitialBoardState()
	{
		return initialBoardState;
	}

	private static final BoardState initialBoardState;
	
	static
	{
		Map<SquareCoordinates, ColoredPiece> positions = new LinkedHashMap<>();
		positions.put(new SquareCoordinates('a', 1), new ColoredPiece(Piece.ROOK, WhiteBlack.WHITE));
		positions.put(new SquareCoordinates('b', 1), new ColoredPiece(Piece.KNIGHT, WhiteBlack.WHITE));
		positions.put(new SquareCoordinates('c', 1), new ColoredPiece(Piece.BISHOP, WhiteBlack.WHITE));
		positions.put(new SquareCoordinates('d', 1), new ColoredPiece(Piece.QUEEN, WhiteBlack.WHITE));
		positions.put(new SquareCoordinates('e', 1), new ColoredPiece(Piece.KING, WhiteBlack.WHITE));
		positions.put(new SquareCoordinates('f', 1), new ColoredPiece(Piece.BISHOP, WhiteBlack.WHITE));
		positions.put(new SquareCoordinates('g', 1), new ColoredPiece(Piece.KNIGHT, WhiteBlack.WHITE));
		positions.put(new SquareCoordinates('h', 1), new ColoredPiece(Piece.ROOK, WhiteBlack.WHITE));
		
		for (char column='a';column<='h';++column)
		{
			positions.put(new SquareCoordinates(column, 2), new ColoredPiece(Piece.PAWN, WhiteBlack.WHITE));
		}

		for (char column='a';column<='h';++column)
		{
			positions.put(new SquareCoordinates(column, 7), new ColoredPiece(Piece.PAWN, WhiteBlack.BLACK));
		}

		positions.put(new SquareCoordinates('a', 8), new ColoredPiece(Piece.ROOK, WhiteBlack.BLACK));
		positions.put(new SquareCoordinates('b', 8), new ColoredPiece(Piece.KNIGHT, WhiteBlack.BLACK));
		positions.put(new SquareCoordinates('c', 8), new ColoredPiece(Piece.BISHOP, WhiteBlack.BLACK));
		positions.put(new SquareCoordinates('d', 8), new ColoredPiece(Piece.QUEEN, WhiteBlack.BLACK));
		positions.put(new SquareCoordinates('e', 8), new ColoredPiece(Piece.KING, WhiteBlack.BLACK));
		positions.put(new SquareCoordinates('f', 8), new ColoredPiece(Piece.BISHOP, WhiteBlack.BLACK));
		positions.put(new SquareCoordinates('g', 8), new ColoredPiece(Piece.KNIGHT, WhiteBlack.BLACK));
		positions.put(new SquareCoordinates('h', 8), new ColoredPiece(Piece.ROOK, WhiteBlack.BLACK));
		

		
		initialBoardState = new BoardState(positions, null, false, false, false, false, false, false);
	}
	
}
