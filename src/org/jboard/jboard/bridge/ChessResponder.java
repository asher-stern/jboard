package org.jboard.jboard.bridge;

import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.WhiteBlack;

/**
 * Receives commands from the chess engine, and makes appropriate actions to
 * present the commands to the human player.
 * 
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public interface ChessResponder
{
	public void movePerformed(Move move);
	
	public void lastMoveRegected(String comment);
	
	public void endGameDeclared(WhiteBlack winner, // null if draw
			String comment);
	
	public void drawOffered();
	
	public void drawAccepted();
	
}
