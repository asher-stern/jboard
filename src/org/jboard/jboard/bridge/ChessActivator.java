package org.jboard.jboard.bridge;

import org.jboard.jboard.chess.Move;
import org.jboard.jboard.gui.board.BoardActivator;

/**
 * Sends commands from the human player to the chess engine. 
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public interface ChessActivator extends BoardActivator
{
	public void makeMove(Move move);
	
	public void offerDraw();
	
	public void acceptDraw();
	
	public void resign();
}
