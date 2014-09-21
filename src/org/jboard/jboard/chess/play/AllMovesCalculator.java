package org.jboard.jboard.chess.play;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.ColoredPiece;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 21, 2014
 *
 */
public class AllMovesCalculator
{
	public AllMovesCalculator(BoardState board, WhiteBlack color)
	{
		super();
		this.board = board;
		this.color = color;
	}
	
	public void excludeCastling()
	{
		calculateCastling = false;
	}

	public List<Move> calculateAllMoves()
	{
		List<Move> moves = new LinkedList<Move>();
		
		Map<SquareCoordinates, ColoredPiece> positions = board.getPositions();
		for (SquareCoordinates square : positions.keySet())
		{
			ColoredPiece coloredPiece = positions.get(square);
			if (coloredPiece.getColor()==color)
			{
				PlayOneStep playOneStep = null;
				switch(coloredPiece.getPiece())
				{
				case BISHOP:
					playOneStep = new BishopPlayOneStep(board,square);
					break;
				case KING:
					KingPlayOneStep kingPlayOneStep = new KingPlayOneStep(board,square);
					if (!calculateCastling)
					{
						kingPlayOneStep.excludeCastling();	
					}
					playOneStep = kingPlayOneStep;
					break;
				case KNIGHT:
					playOneStep = new KnightPlayOneStep(board,square);
					break;
				case PAWN:
					playOneStep = new PawnPlayOneStep(board, square);
					break;
				case QUEEN:
					playOneStep = new QueenPlayOneStep(board, square);
					break;
				case ROOK:
					playOneStep = new RookPlayOneStep(board, square);
					break;
				}
				
				for (Move move : playOneStep.calculateAllMoves())
				{
					moves.add(move);
				}
			}
		}
		
		return moves;
	}
	

	private final BoardState board;
	private final WhiteBlack color;
	
	private boolean calculateCastling = true;
}
