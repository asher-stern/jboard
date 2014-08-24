package org.jboard.jboard.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jboard.jboard.chessengine.ChessEngineConfiguration;
import org.jboard.jboard.gui.board.BoardPanel;

/**
 * A listener for user's command to configure the chess engine, start a new game, etc.
 * This listener does not listen to user's moves. Moves are processed by the {@link BoardPanel}.
 *
 * @author Asher Stern
 * Date: Aug 17, 2014
 *
 */
public class GameManagerCommandsListener implements ActionListener
{
	public GameManagerCommandsListener(GameManager gameManager)
	{
		super();
		this.gameManager = gameManager;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("start"))
		{
			gameManager.startNewGame();
		}
		else if (e.getActionCommand().equals("select_engine"))
		{
			EngineSelectionDialog engineSelectionDialog = new EngineSelectionDialog(gameManager.getFrame(),gameManager);
			engineSelectionDialog.setVisible(true);
			if (engineSelectionDialog.isDonePressed())
			{
				gameManager.userSelectedEngine(engineSelectionDialog.getSelectedEngine());
			}
			else if (engineSelectionDialog.isJarSelected())
			{
				gameManager.userSelectedJarEngine(engineSelectionDialog.getSelectedJarFile());
			}
		}
		else if (e.getActionCommand().equals("start_identical"))
		{
			ChessEngineConfiguration configuration = gameManager.getCurrentEngineConfiguration();
			gameManager.startNewGame();
			gameManager.configureEngine(configuration);
		}
		else if (e.getActionCommand().equals("flip_display"))
		{
			gameManager.getBoardPanel().setAppearsDown(gameManager.getBoardPanel().getAppearsDown().getOther());
		}
		else if (e.getActionCommand().equals("configure"))
		{
			ConfigurationDialog dialog = new ConfigurationDialog(gameManager.getFrame(),
					gameManager.getChessEngine().getDepth(),gameManager.getChessEngine().getTime(),
					gameManager.getChessEngine().getComputerColor(),gameManager.getChessEngine().isRandomMode());
			dialog.setVisible(true);
			if (dialog.isDonePressed())
			{
				gameManager.configureEngine(dialog.createConfiguration());
			}
		}
		
		
		
		gameManager.getBoardPanel().requestFocusInWindow();
		


	}
	
	private final GameManager gameManager;
}
