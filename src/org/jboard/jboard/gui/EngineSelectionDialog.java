package org.jboard.jboard.gui;

import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jboard.jboard.utilities.GuiUtilities;
import static org.jboard.jboard.utilities.StringUtils.*;

/**
 * 
 * @author Asher Stern
 * Date: Aug 24, 2014
 *
 */
@SuppressWarnings("serial")
public class EngineSelectionDialog extends JDialog implements ActionListener
{
	public EngineSelectionDialog(java.awt.Window ownerWindow,GameManager gameManager)
	{
		super(ownerWindow, Dialog.DEFAULT_MODALITY_TYPE);
		this.ownerWindow = ownerWindow;
		this.gameManager = gameManager;
		init();
	}
	
	

	public String getSelectedEngine()
	{
		return selectedEngine;
	}
	public boolean isDonePressed()
	{
		return donePressed;
	}




	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("done"))
		{
			selectedEngine = (String)chessEngineComboBox.getSelectedItem();
			System.out.println("Engine selected by the user = "+selectedEngine);
			donePressed = true;
			dispose();
			setVisible(false);
		}
	}

	
	private void init()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.add(new JLabel(localized("choose_engine")));
		
		Vector<String> chessEngineVector = new Vector<>(gameManager.getChessEngineList());
		chessEngineComboBox = new JComboBox<>(chessEngineVector);
		chessEngineComboBox.setSelectedIndex(0);
		chessEngineComboBox.setEditable(true);
		mainPanel.add(chessEngineComboBox);
		
		JButton doneButton = new JButton(localized("done"));
		doneButton.setActionCommand("done");
		doneButton.addActionListener(this);
		mainPanel.add(doneButton);
		
		setContentPane(mainPanel);
		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		pack();
		GuiUtilities.centerDialog(ownerWindow, this);
	}
	
	private final java.awt.Window ownerWindow;
	private final GameManager gameManager;
	
	private JComboBox<String> chessEngineComboBox;
	
	private String selectedEngine = null;
	private boolean donePressed = false;
}
