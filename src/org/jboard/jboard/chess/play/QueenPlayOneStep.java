package org.jboard.jboard.chess.play;

import java.util.LinkedList;
import java.util.List;

import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 12, 2014
 *
 */
public class QueenPlayOneStep extends PlayOneStep
{

	public QueenPlayOneStep(BoardState board, SquareCoordinates origin)
	{
		super(board, origin);
	}

	@Override
	public List<Move> calculateAllMoves()
	{
		List<Move> ret = new LinkedList<Move>();
		RookPlayOneStep rookPlayOneStep = new RookPlayOneStep(board, origin);
		addAll(ret,rookPlayOneStep.calculateAllMoves());
		BishopPlayOneStep bishopPlayOneStep = new BishopPlayOneStep(board, origin);
		addAll(ret,bishopPlayOneStep.calculateAllMoves());
		
		return ret;
	}
	
	
	/**
	 * Collection.addAll() was buggy in some versions of JRE.
	 */
	private static <T> void addAll(List<T> myList, List<T> otherList)
	{
		for (T t : otherList)
		{
			myList.add(t);
		}
	}

}
