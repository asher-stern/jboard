package org.jboard.jboard.chessengine;

import java.util.List;

import org.jboard.jboard.utilities.ProcessDoublePipeRunner;

/**
 * The chess-engine process (this is a stand alone process, with two pipes for its
 * standard input and standard output, used to communicate with the GUI (e.g.,
 * xboard, Winboard and jboard).
 * <P>
 * 
 * The process is constructed with the chess engine, such that it can send it commands and receives commands from it.
 * 
 * @author Asher Stern
 * Date: Jul 25, 2014
 *
 */
public class ChessEngineProcess extends ProcessDoublePipeRunner
{
	public ChessEngineProcess(List<String> command, String directory,
			PipelinedChessEngine chessEngine)
	{
		super(command, directory);
		this.chessEngine = chessEngine;
	}
	

	@Override
	public void processOutputLine(String line)
	{
		chessEngine.processUnderlyingEngineLine(line);
	}
	
	
	
	protected final PipelinedChessEngine chessEngine;
}
