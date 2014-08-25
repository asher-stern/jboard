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
	public void movePerformed(Move move);
	
	public void cancelLastMove();
}
