package org.jboard.jboard.gui;

import javax.swing.JButton;

import org.jboard.jboard.gui.board.BoardPanel;

/**
 * Includes some GUI controls that should be enabled and disabled by the {@link GameManager}.
 * 
 * @author Asher Stern
 * Date: Aug 25, 2014
 *
 */
public class ControlsManager
{
	public ControlsManager(BoardPanel boardPanel, JButton buttonStart,
			JButton buttonResetAndStart)
	{
		super();
		this.boardPanel = boardPanel;
		this.buttonStart = buttonStart;
		this.buttonResetAndStart = buttonResetAndStart;
	}
	
	public void enableAfterEngineSelected()
	{
		boardPanel.setMoveEnabled(true);
		buttonStart.setEnabled(true);
		buttonResetAndStart.setEnabled(true);
	}
	
	private final BoardPanel boardPanel;
	private final JButton buttonStart;
	private final JButton buttonResetAndStart;
	
}
