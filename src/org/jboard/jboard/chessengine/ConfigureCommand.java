package org.jboard.jboard.chessengine;

/**
 * A {@link Command} sent from the user to configure the engine. 
 *
 * @author Asher Stern
 * Date: 2014
 *
 */
public class ConfigureCommand extends Command
{
	public ConfigureCommand(String commandString)
	{
		super();
		this.commandType = CommandType.CONFIGURE;
		this.commandString = commandString;
	}
	
	public ConfigureCommand getConfigureCommand()
	{
		return this;
	}


	public String getCommandString()
	{
		return commandString;
	}



	private final String commandString;
}
