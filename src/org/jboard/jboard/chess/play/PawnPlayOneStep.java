package org.jboard.jboard.chess.play;

import java.util.LinkedList;
import java.util.List;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.ColoredPiece;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

import static org.jboard.jboard.Constants.*;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 10, 2014
 *
 */
public class PawnPlayOneStep extends PlayOneStep
{

	public PawnPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		List<Move> ret = new LinkedList<Move>();
		
		ColoredPiece piece = board.getPositions().get(origin);
		if (piece.getPiece()!=Piece.PAWN) {throw new RuntimeException("Bug");}
		WhiteBlack color = piece.getColor();
		if (color==null) {throw new RuntimeException("Bug");}
		int addToRow = color==WhiteBlack.WHITE?1:-1;
		int lastRowZeroStarting = color==WhiteBlack.WHITE?(BOARD_SIZE-1):0;
		if (origin.getRowAsZeroStartingCoordinate()==lastRowZeroStarting) {throw new RuntimeException("Bug: a pawn cannot be placed in the last row.");}
		
		List<SquareCoordinates> destinationCandidates = new LinkedList<SquareCoordinates>();
		
		SquareCoordinates ahead = new SquareCoordinates(origin.getColumn(), origin.getRow()+addToRow);
		if (board.getPositions().containsKey(ahead)) {}
		else
		{
			destinationCandidates.add(ahead);
		}
		
		List<SquareCoordinates> captureCandidates = new LinkedList<SquareCoordinates>();
		if (origin.getColumn()>'a')
		{
			char column = origin.getColumn();
			--column;
			captureCandidates.add(new SquareCoordinates(column, origin.getRow()+addToRow));
		}
		if (origin.getColumn()<'a'+(BOARD_SIZE-1))
		{
			char column = origin.getColumn();
			++column;
			captureCandidates.add(new SquareCoordinates(column, origin.getRow()+addToRow));
		}
		
		for (SquareCoordinates captureCandidate : captureCandidates)
		{
			if (board.getPositions().containsKey(captureCandidate))
			{
				WhiteBlack colorInCapture = board.getPositions().get(captureCandidate).getColor();
				if (colorInCapture.getOther()==color)
				{
					destinationCandidates.add(captureCandidate);
				}
			}
		}
		
		for (SquareCoordinates destinationCandidate : destinationCandidates)
		{
			if (destinationCandidate.getRowAsZeroStartingCoordinate()==lastRowZeroStarting) // promotion
			{
				ret.add(new Move(origin, destinationCandidate, null));
			}
			else
			{
				for (Piece promotionCandidate : Piece.values())
				{
					if (promotionCandidate.isValidForPromotion())
					{
						ret.add(new Move(origin, destinationCandidate, promotionCandidate));
					}
				}
			}

		}

		
		
		return ret;
	}

}
