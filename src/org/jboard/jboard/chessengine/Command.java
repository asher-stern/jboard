package org.jboard.jboard.chessengine;

/**
 * A command which is sent to the chess engine.
 * A command may be either a configuration command or a move command.
 * 
 * @author Asher Stern
 * Date: Jul 11, 2014
 *
 */
public class Command
{
	public CommandType getCommandType()
	{
		return commandType;
	}
	
	public MoveCommand getMoveCommand()
	{
		return null;
	}
	
	public ConfigureCommand getConfigureCommand()
	{
		return null;
	}

	protected CommandType commandType;
}
