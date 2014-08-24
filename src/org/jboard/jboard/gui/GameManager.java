package org.jboard.jboard.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.jboard.jboard.bridge.ChessSystem;
import org.jboard.jboard.chessengine.ChessEngine;
import org.jboard.jboard.chessengine.ChessEngineConfiguration;
import org.jboard.jboard.chessengine.ChessEngineProcess;
import org.jboard.jboard.chessengine.PipelinedChessEngine;
import org.jboard.jboard.gui.board.BoardPanel;
import org.jboard.jboard.utilities.Container;

import static org.jboard.jboard.Constants.*;

/**
 * A focal point for all the components from which the program is composed.
 * <BR>
 * This game manager creates new chess engine process, creates other components: {@link ChessEngine},
 * {@link ChessSystem}, and registers the {@link ChessSystem} within a {@link BoardPanel}.
 * <BR>
 * The game manager also manages user's configuration for the engine.
 *
 * @author Asher Stern
 * Date: Aug 5, 2014
 *
 */
public class GameManager
{
	public GameManager(JFrame frame, BoardPanel boardPanel, Container<ChessEngineProcess> processContainer, Images<?> images)
	{
		this.frame = frame;
		this.boardPanel = boardPanel;
		this.processContainer = processContainer;
		this.images = images;
		loadChessEngineList();
	}
	
	
	
	public List<String> getChessEngineList()
	{
		return chessEngineList;
	}



	public void startNewGame()
	{
		tryToShutDownProcess();
		boardPanel.resetBoard();
		
		ChessSystem chessSystem = new ChessSystem(boardPanel,frame, images);
		boardPanel.registerActivator(chessSystem);
		
		chessEngine = createChessEngine(chessSystem);
		chessSystem.registerEngine(chessEngine);
	}
	
	public void configureEngine(ChessEngineConfiguration configuration)
	{
		if (boardPanel.getAppearsDown()!=configuration.getComputerColor().getOther())
		{
			boardPanel.setAppearsDown(configuration.getComputerColor().getOther());
		}
		chessEngine.setDepth(configuration.getDepth());
		chessEngine.setTime(configuration.getTime());
		chessEngine.setComputerColor(configuration.getComputerColor());
		if (configuration.isRandom()) {chessEngine.setRandomMode();}
	}
	
	public void userSelectedEngine(String engine)
	{
		selectedChessEngine = engine;
		if (!(chessEngineList.contains(engine)))
		{
			chessEngineList.add(engine);
		}
	}
	
	
	public ChessEngineConfiguration getCurrentEngineConfiguration()
	{
		return new ChessEngineConfiguration(chessEngine.getDepth(),chessEngine.getTime(),chessEngine.getComputerColor(),chessEngine.isRandomMode());
	}
	
	public void tryToShutDownProcess()
	{
		if (getProcessContainer()!=null)
		{
			if (getProcessContainer().get()!=null)
			{
				getProcessContainer().get().stopProcess();
			}
		}

	}
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public BoardPanel getBoardPanel()
	{
		return boardPanel;
	}

	public ChessEngine getChessEngine()
	{
		return chessEngine;
	}

	public Container<ChessEngineProcess> getProcessContainer()
	{
		return processContainer;
	}


	private ChessEngine createChessEngine(ChessSystem chessSystem)
	{
		PipelinedChessEngine engine = new PipelinedChessEngine(chessSystem);
		
		List<String> command = Arrays.asList(new String[]{selectedChessEngine});
		ChessEngineProcess process = new ChessEngineProcess(command,".",engine);
		processContainer.set(process);
		engine.registerProcess(processContainer);

		processContainer.get().startProcess();
		engine.init();

		return engine;
	}

	private void loadChessEngineList()
	{
		chessEngineList = new LinkedList<>();
		InputStream stream = this.getClass().getResourceAsStream(CHESS_ENGINE_LIST_FILE);
		try
		{
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
			{
				String line = reader.readLine();
				while (line!=null)
				{
					line = line.trim();
					if (line.length()>0)
					{
						chessEngineList.add(line);
					}
					line = reader.readLine();
				}
				if (chessEngineList.size()==0)
				{
					throw new RuntimeException("Chess engine list file is empty.");
				}
				else
				{
					selectedChessEngine = chessEngineList.iterator().next();
					chessEngineList = Collections.unmodifiableList(chessEngineList);
				}
			} catch (IOException e)
			{
				throw new RuntimeException("Could not read chess engine list file.",e);
			}
		}
		finally
		{
			if (stream!=null)
			{
				try {stream.close();}catch(IOException ioex){}
			}
		}

	}


	protected final JFrame frame;
	protected final BoardPanel boardPanel;
	protected final Container<ChessEngineProcess> processContainer;
	protected final Images<?> images;

	private String selectedChessEngine = null;
	private List<String> chessEngineList = null;
	
	protected ChessEngine chessEngine = null;
}
