package org.jboard.jboard.gui;

import static org.jboard.jboard.utilities.StringUtils.localized;

import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileFilter;

import org.jboard.jboard.utilities.FileUtilities;
import org.jboard.jboard.utilities.GuiUtilities;

/**
 * 
 * @author Asher Stern
 * Date: Aug 24, 2014
 *
 */
@SuppressWarnings("serial")
public class EngineSelectionDialog extends JDialog implements ActionListener, ItemListener
{
	public static final String JAR_FILE_EXTENSION = "jar";
	
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
	public File getSelectedJarFile()
	{
		return selectedJarFile;
	}



	public boolean isDonePressed()
	{
		return donePressed;
	}
	public boolean isJarSelected()
	{
		return jarSelected;
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
		else if (e.getActionCommand().equals("jar_file_chooser"))
		{
			JFileChooser fileChooser = new JFileChooser(lastSelectedDirectory);
			fileChooser.setApproveButtonText(localized("select"));
			if (lastSelectedDirectory!=null) {fileChooser.setCurrentDirectory(lastSelectedDirectory);}
			
			fileChooser.addChoosableFileFilter(new FileFilter()
			{
				
				@Override
				public String getDescription()
				{
					return "Jar files";
				}
				
				@Override
				public boolean accept(File f)
				{
					return f.isFile() && FileUtilities.getExtension(f).equals(JAR_FILE_EXTENSION);
				}
			});
			
			fileChooser.setFileFilter(new FileFilter()
			{
				@Override
				public boolean accept(File f)
				{
					return f.isDirectory() || FileUtilities.getExtension(f).equals(JAR_FILE_EXTENSION);
				}

				@Override
				public String getDescription()
				{
					return "Jar files";
				}
			});
			
			int fileChooserReturnValue = fileChooser.showOpenDialog(ownerWindow);
			if (JFileChooser.APPROVE_OPTION==fileChooserReturnValue)
			{
				selectedJarFile = fileChooser.getSelectedFile();
				lastSelectedDirectory = selectedJarFile.getParentFile();
				jarSelected=true;
				dispose();
				setVisible(false);
			}
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		enableDisable();
	}
	
	private void enableDisable()
	{
		chessEngineComboBox.setEnabled(regularRadioButton.isSelected());
		doneButton.setEnabled(regularRadioButton.isSelected());
		jarFileChooserButton.setEnabled(javaRadioButton.isSelected());
		if (!(regularRadioButton.isSelected()))
		{
			selectedEngine=null;
		}
		if (!(javaRadioButton.isSelected()))
		{
			selectedJarFile=null;
		}
	}


	
	private void init()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		ButtonGroup radioSelectionGroup = new ButtonGroup();

		JPanel regularSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		regularSelectPanel.setBorder(BorderFactory.createTitledBorder(localized("non_java_engine")));
		
		regularRadioButton = new JRadioButton(localized("choose_regular_engine"));
		regularRadioButton.setSelected(true);
		radioSelectionGroup.add(regularRadioButton);
		regularRadioButton.addItemListener(this);
		regularSelectPanel.add(regularRadioButton);
		
		Vector<String> chessEngineVector = new Vector<>(gameManager.getChessEngineList());
		chessEngineComboBox = new JComboBox<>(chessEngineVector);
		chessEngineComboBox.setSelectedIndex(0);
		chessEngineComboBox.setEditable(true);
		regularSelectPanel.add(chessEngineComboBox);
		
		doneButton = new JButton(localized("done"));
		doneButton.setActionCommand("done");
		doneButton.addActionListener(this);
		regularSelectPanel.add(doneButton);
		
		mainPanel.add(regularSelectPanel);
		
		
		JPanel javaSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		javaSelectPanel.setBorder(BorderFactory.createTitledBorder(localized("java_engine")));
		
		javaRadioButton = new JRadioButton(localized("choose_java_engine"));
		radioSelectionGroup.add(javaRadioButton);
		javaRadioButton.addItemListener(this);
		javaSelectPanel.add(javaRadioButton);
		
		jarFileChooserButton = new JButton(localized("select"));
		jarFileChooserButton.setActionCommand("jar_file_chooser");
		jarFileChooserButton.addActionListener(this);
		javaSelectPanel.add(jarFileChooserButton);
		
		
		mainPanel.add(javaSelectPanel);

		

		setContentPane(mainPanel);
		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		enableDisable();
		pack();
		GuiUtilities.centerDialog(ownerWindow, this);
	}
	
	private static File lastSelectedDirectory = new File(".");
	
	private final java.awt.Window ownerWindow;
	private final GameManager gameManager;

	private JButton doneButton; 
	private JComboBox<String> chessEngineComboBox;
	private JButton jarFileChooserButton;
	
	private JRadioButton regularRadioButton;
	private JRadioButton javaRadioButton;
	
	private String selectedEngine = null;
	private File selectedJarFile = null;
	private boolean donePressed = false;
	private boolean jarSelected = false;
}
