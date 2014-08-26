package org.jboard.jboard.gui.board;

import org.jboard.jboard.chess.Move;

/**
 * Interface of events sent to the {@link BoardPanel}.
 * The {@link BoardPanel} should respond to these events.
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public interface BoardResponder
{
	/**
	 * The engine (the computer) performed a move. This should then be reflected
	 * in the board presented to the user.
	 * @param move
	 */
	public void movePerformed(Move move);
	
	/**
	 * A directive to the board to cancel the last move that has been performed
	 * by the user. This is typically requested when that last move was an illegal move.
	 */
	public void cancelLastMove();
}
