package org.jboard.jboard.chess.move_parse;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.ColoredPiece;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.play.PlayUtilities;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 21, 2014
 *
 */
public class MoveDetector
{
	
	public MoveDetector(BoardState board, MoveInformation moveInformation)
	{
		super();
		this.board = board;
		this.moveInformation = moveInformation;
	}

	public Move detect()
	{
		Set<Move> detectedMoves = new LinkedHashSet<Move>();
		
		Set<SquareCoordinates> potentialOrigins = findPotentialOriginsByPieceAndPosition();
		for (SquareCoordinates potentialOrigin : potentialOrigins)
		{
			List<Move> potentialMoves = PlayUtilities.getPlayOneStepForPiece(board, potentialOrigin, board.getPositions().get(potentialOrigin).getPiece(), true).calculateAllMoves();
			for (Move move : potentialMoves)
			{
				if (move.getDestination().equals(moveInformation.getDestination()))
				{
					if (equalsOrBothNull(move.getPromotion(),moveInformation.getPromotion()))
					{
						detectedMoves.add(move);
					}
				}
			}
		}
		
		if (detectedMoves.size()!=1)
		{
			throw new RuntimeException("Could not find move. Size of detected moves = "+detectedMoves.size());
		}
		else
		{
			return detectedMoves.iterator().next();
		}
		
	}
	
	
	private Set<SquareCoordinates> findPotentialOriginsByPieceAndPosition()
	{
		Set<SquareCoordinates> potentialOrigins = new LinkedHashSet<SquareCoordinates>();
		
		for (SquareCoordinates square : board.getPositions().keySet())
		{
			ColoredPiece coloredPiece =	board.getPositions().get(square);
			if (coloredPiece.getColor()==moveInformation.getColor())
			{
				if (coloredPiece.getPiece()==moveInformation.getPiece())
				{
					boolean rowOk = true;
					if (moveInformation.getOriginRow()!=null)
					{
						if (moveInformation.getOriginRow().intValue()!=square.getRow())
						{
							rowOk = false;
						}
					}
					
					boolean columnOk = true;
					if (moveInformation.getOriginColumn()!=null)
					{
						if (moveInformation.getOriginColumn().charValue()!=square.getColumn())
						{
							columnOk = false;
						}
					}
					
					if (rowOk && columnOk)
					{
						potentialOrigins.add(square);
					}
				}
			}
		}
		
		return potentialOrigins;
	}
	
	private static <T> boolean equalsOrBothNull(T t1, T t2)
	{
		if (t1==t2) return true;
		if (t1==null) return false;
		if (t2==null) return false;
		return t1.equals(t2);
	}


	private final BoardState board;
	private final MoveInformation moveInformation;
	
	
}
