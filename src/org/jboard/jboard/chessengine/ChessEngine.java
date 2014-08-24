package org.jboard.jboard.chessengine;

import org.jboard.jboard.chess.WhiteBlack;

/**
 * Wrapper of a chess engine. A chess engine is a stand alone process, with two pipes for its standard input and standard
 * output, by which it communicates with the GUI (xboard, Winboard and jboard).
 * <P>
 * The chess engine protocol is described in http://www.gnu.org/software/xboard/engine-intf.html
 * 
 * @author Asher Stern
 * Date: Jul 11, 2014
 *
 */
public abstract class ChessEngine
{
	public ChessEngine()
	{
		
	}
	
	public void init()
	{
		sendCommand(new ConfigureCommand("xboard"));
		thinkAlsoOnOpponentTime=false;
		setThinkAlsoOnOpponentTime(thinkAlsoOnOpponentTime);
		setTime(1);
		//setDepth(3);
	}
	
	
	/**
	 * Send a command into the standard input of the underlying engine process.
	 * @param command
	 */
	public abstract void sendCommand(Command command);

	/**
	 * Process a command that was printed by the chess engine into its standard output.
	 * @param line
	 */
	public abstract void processUnderlyingEngineLine(String line);
	
	
	
	public int getProtocolVersion()
	{
		return protocolVersion;
	}


	/**
	 * Indicates whether the chess engine is running in "random" mode.
	 * <I>About "random mode" in http://www.gnu.org/software/xboard/engine-intf.html:
	 * This command is specific to GNU Chess 4. You can either ignore it completely (that is, treat it as a no-op)
	 * or implement it as GNU Chess does. The command toggles "random" mode (that is, it sets random = !random).
	 * In random mode, the engine adds a small random value to its evaluation function to vary its play. The "new"
	 * command sets random mode off. 
	 * @return true if the engine is running in "random" mode.
	 */
	public boolean isRandomMode()
	{
		return randomMode;
	}

	/**
	 * Indicates whether the engine is in "go" mode. The default mode is "go" mode, in which the engine
	 * plays the game. The other mode is "force" mode, in which the engine does not play, but only checks the validity
	 * of the moves.
	 * @return true if in "go" mode. false if in "force" mode.
	 */
	public boolean isGoMode()
	{
		return goMode;
	}


	/**
	 * Indicates whether the engine is running (in this project it is always running).
	 * @return true if running
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * Returns the depth of the engine's thinking.
	 * @return the depth of the engine's thinking.
	 */
	public int getDepth()
	{
		return depth;
	}
	
	/**
	 * Indicates whether the engine is allowed to think also during the opponent's turn (when it waits for the user to play).
	 * @return true if the engine thinks also in opponent's time.
	 */
	public boolean isThinkAlsoOnOpponentTime()
	{
		return thinkAlsoOnOpponentTime;
	}
	
	

	/**
	 * Returns the color of the engine (e.g. {@link WhiteBlack#BLACK} if the engine plays in the black pieces).
	 * @return the color of the engine 
	 */
	public WhiteBlack getComputerColor()
	{
		return computerColor;
	}

	/**
	 * Set the color of the engine (the user will play in the other color).
	 * @param computerColor the color in which the engine will play.
	 */
	public void setComputerColor(WhiteBlack computerColor)
	{
		if (this.computerColor!=computerColor)
		{
			sendCommand(new ConfigureCommand("go"));
			this.computerColor = computerColor;
		}
	}

	/**
	 * Returns the maximum time in seconds the engine is allowed to think per move.
	 * @return engine's time of thinking per move in seconds.
	 */
	public int getTime()
	{
		return time;
	}

	/**
	 * Sets the maximum time in seconds the engine is allowed to think per move.
	 * @param time
	 */
	public void setTime(int time)
	{
		sendCommand(new ConfigureCommand("st "+time));
		this.time = time;
	}

	/**
	 * Sets whether the engine will be allowed to think during opponent's time (i.e., when it is the user's turn).
	 * @param thinkAlsoOnOpponentTime
	 */
	public void setThinkAlsoOnOpponentTime(boolean thinkAlsoOnOpponentTime)
	{
		sendCommand(new ConfigureCommand(thinkAlsoOnOpponentTime?"hard":"easy"));
		this.thinkAlsoOnOpponentTime = thinkAlsoOnOpponentTime;
	}

	/**
	 * Sets the maximum depth of the engine's thinking.
	 * @param depth
	 */
	public void setDepth(int depth)
	{
		sendCommand(new ConfigureCommand("sd "+depth));
		this.depth = depth;
	}


	/**
	 * Sets the engine to "go" or "force" mode. The parameter "goMode" should be set to turn for "go" mode, and false for "force" mode.
	 * The default mode is "go" mode, in which the engine
	 * plays the game. The other mode is "force" mode, in which the engine does not play, but only checks the validity
	 * of the moves.
	 * 
	 * 
	 * @param goMode true for "go" mode.
	 */
	public void setGoMode(boolean goMode)
	{
		if (goMode != this.goMode)
		{
			if (goMode==true)
			{
				sendCommand(new ConfigureCommand("go"));
			}
			else
			{
				sendCommand(new ConfigureCommand("force"));
			}
			this.goMode = goMode;
		}
	}

	/**
	 * Sets the engine's protocol version. 
	 * @param protocolVersion
	 */
	public void setProtocolVersion(int protocolVersion)
	{
		sendCommand(new ConfigureCommand("protover "+protocolVersion));
		this.protocolVersion = protocolVersion;
	}

	/**
	 * 
	 * Sets the chess engine to run in "random" mode.
	 * <I>About "random mode" in http://www.gnu.org/software/xboard/engine-intf.html:
	 * This command is specific to GNU Chess 4. You can either ignore it completely (that is, treat it as a no-op)
	 * or implement it as GNU Chess does. The command toggles "random" mode (that is, it sets random = !random).
	 * In random mode, the engine adds a small random value to its evaluation function to vary its play. The "new"
	 * command sets random mode off. 
	 * 
	 * @param randomMode
	 */
	public void setRandomMode()
	{
		sendCommand(new ConfigureCommand("random"));
		this.randomMode = true;
	}
	


	public void sendDrawOffer()
	{
		sendCommand(new ConfigureCommand("draw"));
	}
	
	public void moveNow()
	{
		throw new RuntimeException("Not implemented");
		//sendCommand(new ConfigureCommand("?"));
	}

	public void quitEngine()
	{
		sendCommand(new ConfigureCommand("quit"));
		running=false;
	}

	public void resetEngine()
	{
		sendCommand(new ConfigureCommand("new"));
		randomMode = false;
		goMode = true;
		depth = 0;
		time = 0;
		thinkAlsoOnOpponentTime = true;
	}
	

	
	
	
	private boolean running = true;
	private int protocolVersion = 1;
	private boolean randomMode = false;
	private boolean goMode = true;
	private WhiteBlack computerColor = WhiteBlack.BLACK;
	private int depth = 0;
	private int time = 0;
	private boolean thinkAlsoOnOpponentTime = true; // pondering
}
