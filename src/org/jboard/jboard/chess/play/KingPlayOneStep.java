package org.jboard.jboard.chess.play;

import java.util.LinkedList;
import java.util.List;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

import static org.jboard.jboard.Constants.*;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 12, 2014
 *
 */
public class KingPlayOneStep extends PlayOneStep
{

	public KingPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}
	
	public void excludeCastling()
	{
		calculateCastling = false;
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		moves = new LinkedList<Move>();
		opponentMoves = null;
		
		WhiteBlack myColor = board.getPositions().get(origin).getColor();
		
		for (int addToRow : MINUS_ONE_ZERO_ONE)
		{
			for (int addToColumn : MINUS_ONE_ZERO_ONE)
			{
				if ( (addToRow==0) && (addToColumn==0) ) {}
				else
				{
					char column = (char)(origin.getColumn()+addToColumn);
					int row = origin.getRow()+addToRow;
					DestinationType destinationType = PlayUtilities.getDestination(board, myColor, column, row);
					if (destinationType!=DestinationType.INVALID)
					{
						moves.add(new Move(origin, new SquareCoordinates(column, row), null));
					}
				}
			}
		}
		
		if (calculateCastling)
		{
			addCastlings(myColor);
		}
		
		return moves;
	}
	
	private void addCastlings(WhiteBlack myColor)
	{
		boolean kingMoved = false;
		if (WhiteBlack.WHITE == myColor) {kingMoved = board.isWhiteKingAlreadyMoved();}
		else {kingMoved = board.isBlackKingAlreadyMoved();}
		
		if (!kingMoved)
		{
			// rook at a
			boolean rookAMoved = false;
			if (WhiteBlack.WHITE == myColor) {rookAMoved = board.isWhiteRookInColumnA_AlreadyMoved();}
			else {rookAMoved = board.isBlackRookInColumnA_AlreadyMoved();}
			if (!rookAMoved)
			{
				SquareCoordinates rookCoordinates = new SquareCoordinates('a', origin.getRow());
				List<SquareCoordinates> way = getSquaresInWay(origin,rookCoordinates);
				if (checkNotThreatened(myColor, way))
				{
					moves.add(new Move(origin, 
							new SquareCoordinates((char) (origin.getColumn()-2), origin.getRow()),
							null));
					
				}
			}
			
			// rook at h
			boolean rookHMoved = false;
			if (WhiteBlack.WHITE == myColor) {rookHMoved = board.isWhiteRookInColumnH_AlreadyMoved();}
			else {rookHMoved = board.isBlackRookInColumnH_AlreadyMoved();}
			if (!rookHMoved)
			{
				SquareCoordinates rookCoordinates = new SquareCoordinates((char)('a'+BOARD_SIZE-1), origin.getRow());
				List<SquareCoordinates> way = getSquaresInWay(origin,rookCoordinates);
				if (checkNotThreatened(myColor, way))
				{
					moves.add(new Move(origin, 
							new SquareCoordinates((char) (origin.getColumn()+2), origin.getRow()),
							null));
					
				}
			}
			
		}
		
	}
	
	private List<SquareCoordinates> getSquaresInWay(SquareCoordinates kingCoordinates, SquareCoordinates rookCoordinates)
	{
		int row = kingCoordinates.getRow();
		char firstColumn;
		char secondColumn;
		if (kingCoordinates.getColumn()>rookCoordinates.getColumn())
		{
			firstColumn = rookCoordinates.getColumn();
			secondColumn = kingCoordinates.getColumn();
		}
		else
		{
			firstColumn = kingCoordinates.getColumn();
			secondColumn = rookCoordinates.getColumn();
		}
		
		List<SquareCoordinates> ret = new LinkedList<SquareCoordinates>();
		for (char column = firstColumn; column<=secondColumn; ++column)
		{
			ret.add(new SquareCoordinates(column, row));
		}
		return ret;
	}
	
	private boolean checkNotThreatened(WhiteBlack myColor, List<SquareCoordinates> squares)
	{
		if (null==opponentMoves)
		{
			AllMovesCalculator allMovesCalculator = new AllMovesCalculator(board,myColor.getOther());
			allMovesCalculator.excludeCastling();
			opponentMoves = allMovesCalculator.calculateAllMoves();
		}
		
		for (SquareCoordinates oneSquare : squares)
		{
			for (Move oneMove : opponentMoves)
			{
				if (oneMove.getDestination().equals(oneSquare))
				{
					return false;
				}
			}
		}
		return true;
	}
	


	private boolean calculateCastling = true;
	private List<Move> moves;
	
	private List<Move> opponentMoves = null;
	
	
	private static final int[] MINUS_ONE_ZERO_ONE = new int[]{-1,0,1};
}
