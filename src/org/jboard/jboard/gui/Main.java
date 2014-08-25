package org.jboard.jboard.gui;

import static org.jboard.jboard.utilities.StringUtils.localized;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jboard.jboard.chessengine.ChessEngineProcess;
import org.jboard.jboard.gui.board.BoardPanel;
import org.jboard.jboard.utilities.Container;

/**
 * Entry point of the program. Constructs all the game components, including the main frame
 * and the {@link BoardPanel}.
 * 
 * @author Asher Stern
 * Date: Jul 16, 2014
 *
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ImagesLoader imagesLoader = new ImagesLoader();
			imagesLoader.load();
			final Images<BufferedImage> images = new Images<BufferedImage>(imagesLoader.getPieceImages(), imagesLoader.getSquareImages(), imagesLoader.getWidthOfSubImage(), imagesLoader.getHeightOfSubImage());

			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						Main main = new Main();
						main.createAndShowGui(images);
					}
					catch(Throwable t)
					{
						t.printStackTrace(System.out);
					}
				}
			});
			
		}
		catch(Throwable t)
		{
			t.printStackTrace(System.out);
		}
	}
	
	private void createAndShowGui(Images<BufferedImage> images)
	{
		final JFrame mainFrame = new JFrame("Jboard");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createBoardPanelAndProcess(mainFrame,images);
		GameManagerCommandsListener gameManagerCommandsListener = new GameManagerCommandsListener(gameManager); 
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(boardPanel,BorderLayout.CENTER);
		addToolBar(mainPanel,gameManagerCommandsListener);
		gameManager.setControlsManager(new ControlsManager(boardPanel, buttonStart, buttonResetAndStart));
		mainFrame.setContentPane(mainPanel);

		mainFrame.setFocusTraversalKeysEnabled(true);
		
		mainFrame.addWindowFocusListener(new WindowAdapter()
		{
			@Override public void windowGainedFocus(WindowEvent e)
			{
				boardPanel.requestFocusInWindow();
			}
		});
		
		mainPanel.addFocusListener(new FocusAdapter()
		{
			@Override public void focusGained(FocusEvent e)
			{
				boardPanel.requestFocusInWindow();
			}
		});

		
		
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension mainFrameDimension = new Dimension((int)(screenDimension.getWidth()*0.75), (int)(screenDimension.getHeight()*0.75));
		mainFrame.setSize(mainFrameDimension);
		
		mainFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.out.println("Main frame closing.");
				if (gameManager!=null)
				{
					gameManager.tryToShutDownProcess();
				}
			}
		});
		
		mainFrame.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		mainFrame.setVisible(true);
		boardPanel.requestFocusInWindow();

	}
	
	private void createBoardPanelAndProcess(JFrame frame, Images<BufferedImage> images)
	{
		boardPanel = new BoardPanel(frame, images);
		boardPanel.setMoveEnabled(false);
		Container<ChessEngineProcess> processContainer = new Container<ChessEngineProcess>(null);
		
		gameManager = new GameManager(frame, boardPanel, processContainer, images);
		gameManager.startNewGame();
	}
	
	
	private void addToolBar(java.awt.Container mainPanel, GameManagerCommandsListener gameManagerCommandsListener)
	{
		JToolBar toolBar = new JToolBar();
		
		buttonStart = new JButton(localized("start"));
		buttonStart.setEnabled(false);
		buttonStart.setActionCommand("start");
		buttonStart.addActionListener(gameManagerCommandsListener);
		toolBar.add(buttonStart);

		buttonResetAndStart = new JButton(localized("reset_and_start"));
		buttonResetAndStart.setEnabled(false);
		buttonResetAndStart.setActionCommand("reset_and_start");
		buttonResetAndStart.addActionListener(gameManagerCommandsListener);
		toolBar.add(buttonResetAndStart);

		JButton buttonSelectEngine = new JButton(localized("choose_engine"));
		buttonSelectEngine.setActionCommand("select_engine");
		buttonSelectEngine.addActionListener(gameManagerCommandsListener);
		toolBar.add(buttonSelectEngine);

		JButton buttonConfigure = new JButton(localized("configure_engine"));
		buttonConfigure.setActionCommand("configure");
		buttonConfigure.addActionListener(gameManagerCommandsListener);
		toolBar.add(buttonConfigure);
		
		JButton buttonFlipDisplay = new JButton(localized("flip_display"));
		buttonFlipDisplay.setActionCommand("flip_display");
		buttonFlipDisplay.addActionListener(gameManagerCommandsListener);
		toolBar.add(buttonFlipDisplay);


		mainPanel.add(toolBar, BorderLayout.PAGE_START);
	}
	
	

	private BoardPanel boardPanel = null;
	private GameManager gameManager = null;
	
	private JButton buttonStart;
	private JButton buttonResetAndStart; 
}
