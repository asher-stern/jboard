package org.jboard.jboard.chess;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.jboard.jboard.chess.ChessRulesUtils.*;

/**
 * Utilities for handling and generating a {@link BoardState}
 * 
 * @author Asher Stern
 * Date: July 2014
 *
 */
public class BoardStateUtils
{
	/**
	 * Performs a move in the given {@link BoardState}, resulting in a new {@link BoardState} which represents the board
	 * after performing that move.
	 * 
	 * @param state
	 * @param move
	 * @return
	 */
	public static BoardState performMove(BoardState state, Move move)
	{
		Map<SquareCoordinates, ColoredPiece> positions = new LinkedHashMap<>();
		positions.putAll(state.getPositions());
		positions.remove(move.getSource());
		ColoredPiece destinationPiece = state.getPositions().get(move.getSource());
		if (move.getPromotion()!=null)
		{
			ColoredPiece destinationPiece2 = new ColoredPiece(move.getPromotion(), destinationPiece.getColor());
			destinationPiece = destinationPiece2;
		}
		positions.put(move.getDestination(),destinationPiece);
		
		Move rookCastlingMove = ChessRulesUtils.getCastlingRookMove(state, move);
		if (rookCastlingMove!=null)
		{
			positions.remove(rookCastlingMove.getSource());
			ColoredPiece rookCastlingDestinationPiece = state.getPositions().get(rookCastlingMove.getSource());
			positions.put(rookCastlingMove.getDestination(),rookCastlingDestinationPiece);
		}
		
		SquareCoordinates captureInPassing = ChessRulesUtils.inPassingCapture(state,move);
		if (captureInPassing!=null)
		{
			positions.remove(captureInPassing);
		}
		
		ColoredPiece movingPiece = state.getPositions().get(move.getSource());
		SquareCoordinates inPassing = null;
		boolean inPassingDetected = ChessRulesUtils.inPassingMove(state, move);
		if (inPassingDetected)
		{
			int addToRow = 1;
			if (movingPiece.getColor()==WhiteBlack.BLACK)
			{
				addToRow=(-1);
			}
			inPassing = new SquareCoordinates(move.getSource().getColumn(), move.getSource().getRow()+addToRow);
		}
		
		boolean whiteKingAlreadyMoved = state.isWhiteKingAlreadyMoved() || WHITE_KING_SQUARE.equals(move.getSource()); 
		boolean whiteRookInRowA_AlreadyMoved = state.isWhiteRookInRowA_AlreadyMoved() || WHITE_ROOK_A_SQUARE.equals(move.getSource()) || ((rookCastlingMove!=null)&&WHITE_ROOK_A_SQUARE.equals(rookCastlingMove.getSource()));
		boolean whiteRookInRowH_AlreadyMoved = state.isWhiteRookInRowH_AlreadyMoved() || WHITE_ROOK_H_SQUARE.equals(move.getSource()) || ((rookCastlingMove!=null)&&WHITE_ROOK_H_SQUARE.equals(rookCastlingMove.getSource()));
		boolean blackKingAlreadyMoved = state.isBlackKingAlreadyMoved() || BLACK_KING_SQUARE.equals(move.getSource());
		boolean blackRookInRowA_AlreadyMoved = state.isBlackRookInRowA_AlreadyMoved() || BLACK_ROOK_A_SQUARE.equals(move.getSource()) || ((rookCastlingMove!=null)&&BLACK_ROOK_A_SQUARE.equals(rookCastlingMove.getSource()));
		boolean blackRookInRowH_AlreadyMoved = state.isBlackRookInRowH_AlreadyMoved() || BLACK_ROOK_H_SQUARE.equals(move.getSource()) || ((rookCastlingMove!=null)&&BLACK_ROOK_H_SQUARE.equals(rookCastlingMove.getSource()));
		
		
		return new BoardState(positions,inPassing,
				whiteKingAlreadyMoved,whiteRookInRowA_AlreadyMoved,whiteRookInRowH_AlreadyMoved,
				blackKingAlreadyMoved,blackRookInRowA_AlreadyMoved,blackRookInRowH_AlreadyMoved);
	}
	
	/**
	 * Creates a new {@link BoardState} which is identical to the given one, but with the specified piece removed
	 * from the board.
	 * 
	 * @param state
	 * @param squareToRemove
	 * @return
	 */
	public static BoardState temporarilyRemovePiece(BoardState state, SquareCoordinates squareToRemove)
	{
		Map<SquareCoordinates, ColoredPiece> positions = new LinkedHashMap<>();
		positions.putAll(state.getPositions());
		if (positions.containsKey(squareToRemove))
		{
			positions.remove(squareToRemove);
		}
		return new BoardState(positions,
				state.getInPassing(), state.isWhiteKingAlreadyMoved(),
				state.isWhiteRookInRowA_AlreadyMoved(),
				state.isWhiteRookInRowH_AlreadyMoved(),
				state.isBlackKingAlreadyMoved(),
				state.isBlackRookInRowA_AlreadyMoved(),
				state.isBlackRookInRowH_AlreadyMoved());
	}
	

}
