package org.jboard.jboard.gui.board;

import org.jboard.jboard.chess.Move;

/**
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
