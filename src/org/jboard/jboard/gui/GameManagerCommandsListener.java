package org.jboard.jboard.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			gameManager.startNewGameWithCurrentConfiguration();
		}
		else if (e.getActionCommand().equals("reset_and_start"))
		{
			gameManager.startNewGame();
		}
		else if (e.getActionCommand().equals("select_engine"))
		{
			selectEngine();
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
		else if (e.getActionCommand().equals("flip_display"))
		{
			gameManager.getBoardPanel().setAppearsDown(gameManager.getBoardPanel().getAppearsDown().getOther());
		}
		
		
		
		gameManager.getBoardPanel().requestFocusInWindow();
	}
	
	public void selectEngine()
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
	
	private final GameManager gameManager;
}
