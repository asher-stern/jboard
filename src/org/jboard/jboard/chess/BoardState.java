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
			boolean whiteRookInColumnA_AlreadyMoved,
			boolean whiteRookInColumnH_AlreadyMoved,
			boolean blackKingAlreadyMoved,
			boolean blackRookInColumnA_AlreadyMoved,
			boolean blackRookInColumnH_AlreadyMoved)
	{
		super();
		this.positions = positions;
		this.inPassing = inPassing;
		this.whiteKingAlreadyMoved = whiteKingAlreadyMoved;
		this.whiteRookInColumnA_AlreadyMoved = whiteRookInColumnA_AlreadyMoved;
		this.whiteRookInColumnH_AlreadyMoved = whiteRookInColumnH_AlreadyMoved;
		this.blackKingAlreadyMoved = blackKingAlreadyMoved;
		this.blackRookInColumnA_AlreadyMoved = blackRookInColumnA_AlreadyMoved;
		this.blackRookInColumnH_AlreadyMoved = blackRookInColumnH_AlreadyMoved;
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
	public boolean isWhiteRookInColumnA_AlreadyMoved()
	{
		return whiteRookInColumnA_AlreadyMoved;
	}
	public boolean isWhiteRookInColumnH_AlreadyMoved()
	{
		return whiteRookInColumnH_AlreadyMoved;
	}
	public boolean isBlackKingAlreadyMoved()
	{
		return blackKingAlreadyMoved;
	}
	public boolean isBlackRookInColumnA_AlreadyMoved()
	{
		return blackRookInColumnA_AlreadyMoved;
	}
	public boolean isBlackRookInColumnH_AlreadyMoved()
	{
		return blackRookInColumnH_AlreadyMoved;
	}



	private final Map<SquareCoordinates, ColoredPiece> positions;
	
	/**
	 * inPassing is a square, which is empty, but in case a pawn tries to capture a piece in this
	 * square the square is treated as if a pawn is standing in it.
	 * For example: for the move e2e4, e3 is an inPassing square.
	 */
	private final SquareCoordinates inPassing; // may be null
	private final boolean whiteKingAlreadyMoved;
	private final boolean whiteRookInColumnA_AlreadyMoved;
	private final boolean whiteRookInColumnH_AlreadyMoved;
	private final boolean blackKingAlreadyMoved;
	private final boolean blackRookInColumnA_AlreadyMoved;
	private final boolean blackRookInColumnH_AlreadyMoved;
	
}
