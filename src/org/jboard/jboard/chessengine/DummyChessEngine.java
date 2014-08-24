package org.jboard.jboard.chessengine;

/**
 * A chess engine that does not process any command and does not send any directives.
 *
 * @author Asher Stern
 * Date: Aug 17, 2014
 *
 */
public class DummyChessEngine extends ChessEngine
{

	@Override
	public void sendCommand(Command command)
	{
	}

	@Override
	public void processUnderlyingEngineLine(String line)
	{
	}
	

}
