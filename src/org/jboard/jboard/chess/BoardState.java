package org.jboard.jboard.chess;

import java.util.Map;

/**
 * Represents the organization of the pieces in a chess board.
 * This class also stores additional information, like whether the kings and rooks have
 * moved since the beginning of the game, etc. 
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public class BoardState
{
	public BoardState(Map<SquareCoordinates, ColoredPiece> positions,
			SquareCoordinates inPassing, boolean whiteKingAlreadyMoved,
			boolean whiteRookInRowA_AlreadyMoved,
			boolean whiteRookInRowH_AlreadyMoved,
			boolean blackKingAlreadyMoved,
			boolean blackRookInRowA_AlreadyMoved,
			boolean blackRookInRowH_AlreadyMoved)
	{
		super();
		this.positions = positions;
		this.inPassing = inPassing;
		this.whiteKingAlreadyMoved = whiteKingAlreadyMoved;
		this.whiteRookInRowA_AlreadyMoved = whiteRookInRowA_AlreadyMoved;
		this.whiteRookInRowH_AlreadyMoved = whiteRookInRowH_AlreadyMoved;
		this.blackKingAlreadyMoved = blackKingAlreadyMoved;
		this.blackRookInRowA_AlreadyMoved = blackRookInRowA_AlreadyMoved;
		this.blackRookInRowH_AlreadyMoved = blackRookInRowH_AlreadyMoved;
	}
	
	
	
	public Map<SquareCoordinates, ColoredPiece> getPositions()
	{
		return positions;
	}
	public SquareCoordinates getInPassing()
	{
		return inPassing;
	}
	public boolean isWhiteKingAlreadyMoved()
	{
		return whiteKingAlreadyMoved;
	}
	public boolean isWhiteRookInRowA_AlreadyMoved()
	{
		return whiteRookInRowA_AlreadyMoved;
	}
	public boolean isWhiteRookInRowH_AlreadyMoved()
	{
		return whiteRookInRowH_AlreadyMoved;
	}
	public boolean isBlackKingAlreadyMoved()
	{
		return blackKingAlreadyMoved;
	}
	public boolean isBlackRookInRowA_AlreadyMoved()
	{
		return blackRookInRowA_AlreadyMoved;
	}
	public boolean isBlackRookInRowH_AlreadyMoved()
	{
		return blackRookInRowH_AlreadyMoved;
	}



	private final Map<SquareCoordinates, ColoredPiece> positions;
	
	/**
	 * inPassing is a square, which is empty, but in case a pawn tries to capture a piece in this
	 * square the square is treated as if a pawn is standing in it.
	 * For example: for the move e2e4, e3 is an inPassing square.
	 */
	private final SquareCoordinates inPassing; // may be null
	private final boolean whiteKingAlreadyMoved;
	private final boolean whiteRookInRowA_AlreadyMoved;
	private final boolean whiteRookInRowH_AlreadyMoved;
	private final boolean blackKingAlreadyMoved;
	private final boolean blackRookInRowA_AlreadyMoved;
	private final boolean blackRookInRowH_AlreadyMoved;
	
}
