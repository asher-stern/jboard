package org.jboard.jboard.chessengine;


import static org.jboard.jboard.chess.ChessStringUtils.moveToString;
import static org.jboard.jboard.chess.ChessStringUtils.stringToMove;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jboard.jboard.bridge.ChessResponder;
import org.jboard.jboard.bridge.ChessSystem;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.WhiteBlack;
import org.jboard.jboard.utilities.Container;
import org.jboard.jboard.utilities.StringUtils;

/**
 * A chess engine which communicates with an actual engine which runs as a stand-alone process.
 * It sends the user's commands to that process, and receives commands from the user, processes them, and delivers them to
 * the user.
 * <P>
 * The components {@link ChessEngineProcess}, {@link PipelinedChessEngine},
 * {@link ChessSystem} and {@link BoardPanel} are constructed with the lower level component,
 * and register the upper level component.
 * <BR>
 * Consequently, {@link PipelinedChessEngine} is constructed with a {@link ChessResponder} (implemented by
 * {@link ChessSystem}), and registers a {@link ChessEngineProcess} into which the chess engine delivers commands.
 * <BR>
 * Note that {@link ChessEngineProcess} is constructed with {@link PipelinedChessEngine} as a constructor parameter,
 * so the {@link ChessEngineProcess} has the {@link PipelinedChessEngine} and sends commands to it (which are then processed and
 * sent to the user (the GUI)).
 * 
 * @author Asher Stern
 * Date: Jul 25, 2014
 *
 */
/**
 *
 * @author Asher Stern
 * Date: Aug 22, 2014
 *
 */
public class PipelinedChessEngine extends ChessEngine
{
	public static final Map<String, WhiteBlack> WIN_DIRECTIVES = new LinkedHashMap<String, WhiteBlack>();
	public static final Set<String> MOVE_DIRECTIVE = new LinkedHashSet<>();
	public static final Set<String> INVALID_MOVE_MESSAGES = new LinkedHashSet<>();
	static
	{
		INVALID_MOVE_MESSAGES.addAll(Arrays.asList(new String[]{"Illegal move","Invalid move"}));
		MOVE_DIRECTIVE.addAll(Arrays.asList(new String[]{"My move is :","move"}));
		
		WIN_DIRECTIVES.put("0-1", WhiteBlack.BLACK);
		WIN_DIRECTIVES.put("1-0", WhiteBlack.WHITE);
		WIN_DIRECTIVES.put("1/2-1/2", null);
	}


	/**
	 * A constructed which takes a {@link ChessResponder}, implemented by {@link ChessSystem}, into which it sends commands
	 * from the engine to the user.
	 * @param responder
	 */
	public PipelinedChessEngine(ChessResponder responder)
	{
		super();
		this.responder = responder;
	}
	
	/**
	 * Registers the engine's stand-alone process, into which commands from the user will be sent.
	 * @param processContainer
	 */
	public void registerProcess(Container<ChessEngineProcess> processContainer)
	{
		this.processContainer = processContainer;
	}
	
	

	
	/* (non-Javadoc)
	 * @see org.jboard.jboard.chessengine.ChessEngine#processUnderlyingEngineLine(java.lang.String)
	 */
	@Override
	public void processUnderlyingEngineLine(String line)
	{
		if (StringUtils.anyStartsWith(MOVE_DIRECTIVE,line,directiveString))
		{
			String moveString = line.substring(directiveString.get().length(), line.length());
			moveString = moveString.trim();
			Move move = stringToMove(moveString);
			responder.movePerformed(move);
		}
		else if (StringUtils.anyStartsWith(INVALID_MOVE_MESSAGES,line))
		{
			responder.lastMoveRegected(line);
		}
		else if (StringUtils.anyStartsWith(WIN_DIRECTIVES.keySet(),line, directiveString))
		{
			WhiteBlack winner = null;
			if (WIN_DIRECTIVES.containsKey(directiveString.get()))
			{
				winner = WIN_DIRECTIVES.get(directiveString.get());
				responder.endGameDeclared(winner, line);
			}
			else
			{
				throw new RuntimeException("Unrecognized game over message.");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jboard.jboard.chessengine.ChessEngine#sendCommand(org.jboard.jboard.chessengine.Command)
	 */
	@Override
	public void sendCommand(Command command)
	{
		switch(command.getCommandType())
		{
		case CONFIGURE:
			processContainer.get().sendInputLine(command.getConfigureCommand().getCommandString());
			break;
		case MOVE:
			Move move = command.getMoveCommand().getMove();
			processContainer.get().sendInputLine(moveToString(move));
			break;
		default:
			throw new RuntimeException("Unrecognized command");
		}
	}


	
	protected final ChessResponder responder;
	
	protected Container<ChessEngineProcess> processContainer = null;
	
	private Container<String> directiveString = new Container<String>(null);
	
	
}
