package org.jboard.jboard.gui;

import static org.jboard.jboard.Constants.CHESS_ENGINE_MAXIMUM_DEPTH;
import static org.jboard.jboard.Constants.CHESS_ENGINE_MAXIMUM_TIME;

import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jboard.jboard.chess.WhiteBlack;
import org.jboard.jboard.chessengine.ChessEngineConfiguration;
import org.jboard.jboard.utilities.GuiUtilities;

import static org.jboard.jboard.utilities.StringUtils.localized;

/**
 * A dialog box to configure the chess engine.
 *
 * @author Asher Stern
 * Date: Aug 18, 2014
 *
 */
@SuppressWarnings("serial")
public class ConfigurationDialog extends JDialog implements ActionListener
{
	public ConfigurationDialog(Frame ownerFrame, int depth, int time, WhiteBlack computerColor, boolean random)
	{
		super(ownerFrame,localized("configuration"),true);
		this.ownerFrame = ownerFrame;
		this.depth = depth;
		this.time = time;
		this.computerColor = computerColor;
		this.random = random;
		init();
	}
	
	/**
	 * Constructs a {@link ChessEngineConfiguration} based on the member variables of this class.
	 * This method should be called after the user make the configurations in the dialog box, such that
	 * the constructed {@link ChessEngineConfiguration} would contain the user's preferences.
	 * @return
	 */
	public ChessEngineConfiguration createConfiguration()
	{
		return new ChessEngineConfiguration(getDepth(), getTime(), getComputerColor(),isRandom());
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("done"))
		{
			depth = (int)depthSpinner.getValue();
			time = (int)timeSpinner.getValue();
			findSelectedColor();
			donePressed = true;

			dispose();
			setVisible(false);
		}
	}
	
	
	public boolean isDonePressed()
	{
		return donePressed;
	}

	public int getDepth()
	{
		return depth;
	}

	public int getTime()
	{
		return time;
	}
	public WhiteBlack getComputerColor()
	{
		return computerColor;
	}
	public boolean isRandom()
	{
		return random;
	}
	

	protected void init()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel depthTimePanel = new JPanel();
		
		depthTimePanel.add(new JLabel(localized("depth")));
		SpinnerNumberModel depthModel = new SpinnerNumberModel(depth, 0, CHESS_ENGINE_MAXIMUM_DEPTH, 1);
		depthSpinner = new JSpinner(depthModel);
		depthTimePanel.add(depthSpinner);

		
		depthTimePanel.add(new JLabel(localized("time")));
		SpinnerNumberModel timeModel = new SpinnerNumberModel(time, 0, CHESS_ENGINE_MAXIMUM_TIME, 1);
		timeSpinner = new JSpinner(timeModel);
		depthTimePanel.add(timeSpinner);
		
		randomModeCheckBox = new JCheckBox(localized("random_mode"));
		randomModeCheckBox.setSelected(random);
		if (random) {randomModeCheckBox.setEnabled(false);}
		randomModeCheckBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				random=true;
			}
		});
		depthTimePanel.add(randomModeCheckBox);
		
		doneButton = new JButton(localized("done"));
		doneButton.setActionCommand("done");
		doneButton.addActionListener(this);
		depthTimePanel.add(doneButton);
		
		mainPanel.add(depthTimePanel);
		
		JPanel colorPanel = new JPanel();
		colorPanel.add(new JLabel(localized("computer_color")+": "));
		
		ButtonGroup colorGroup = new ButtonGroup();
		whiteButton = new JRadioButton(localized(WhiteBlack.WHITE.name().toLowerCase(new Locale("en","US"))));
		blackButton = new JRadioButton(localized(WhiteBlack.BLACK.name().toLowerCase(new Locale("en","US"))));
		if (computerColor==WhiteBlack.WHITE)
		{
			whiteButton.setSelected(true);
		}
		else if (computerColor==WhiteBlack.BLACK)
		{
			blackButton.setSelected(true);
		}
		else {throw new RuntimeException();}
		
		colorGroup.add(whiteButton);
		colorGroup.add(blackButton);
		colorPanel.add(whiteButton);
		colorPanel.add(blackButton);
		
		mainPanel.add(colorPanel);
		
		setContentPane(mainPanel);
		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		pack();
		GuiUtilities.centerDialog(ownerFrame,this);
		
	}
	
	private void findSelectedColor()
	{
		if (whiteButton.isSelected()) {computerColor = WhiteBlack.WHITE;}
		else if (blackButton.isSelected()) {computerColor = WhiteBlack.BLACK;}
		else throw new RuntimeException();
	}
	
	private final Frame ownerFrame;
	
	private JSpinner depthSpinner;
	private JSpinner timeSpinner;
	private JButton doneButton;
	private JCheckBox randomModeCheckBox;
	
	private JRadioButton whiteButton;
	private JRadioButton blackButton;
	private boolean donePressed = false;
	
	private int depth = 3;
	private int time = 1;
	private WhiteBlack computerColor;
	private boolean random;
}
