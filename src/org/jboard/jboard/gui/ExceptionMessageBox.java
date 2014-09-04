package org.jboard.jboard.gui;

import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jboard.jboard.utilities.GuiUtilities;
import org.jboard.jboard.utilities.StringUtils;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 4, 2014
 *
 */
@SuppressWarnings("serial")
public class ExceptionMessageBox extends JDialog
{
	public static final int DEFAULT_WIDTH = 500;
	public static final int WIDTH_SLACK = 30;
	public static final int DEFAULT_HEIGHT = 200;
	public static final int DEFAULT_DETAILS_HEIGHT = 200;
	
	public ExceptionMessageBox(Window window, Exception exception)
	{
		super(window,StringUtils.localized("error"),Dialog.DEFAULT_MODALITY_TYPE);
		this.window = window;
		this.exception = exception;
		
		init();
	}
	
	private void init()
	{
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
		mainPanel.setLayout(layout);
		
		JTextArea textArea = new JTextArea(exception.getMessage());
		mainPanel.add(textArea);
		
		detailsArea.setText(StringUtils.getStackTrace(exception));
		
		
		JLabel label = new JLabel("+ "+StringUtils.localized("details"));
		JPanel labelPanel = new JPanel();
		labelPanel.add(label);
		labelPanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (!detailsShown)
				{
					label.setText("- "+StringUtils.localized("details"));
					mainPanel.add(detailsArea);
					setSize(getWidth(), getHeight()+DEFAULT_DETAILS_HEIGHT);
				}
				else
				{
					label.setText("+ "+StringUtils.localized("details"));
					mainPanel.remove(detailsArea);
					setSize(getWidth(), getHeight()-DEFAULT_DETAILS_HEIGHT);
				}
				detailsShown=!detailsShown;
			}
		});
		
		
		mainPanel.add(labelPanel);
		
		setContentPane(mainPanel);
		
		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		int width = DEFAULT_WIDTH;
		if (window!=null)
		{
			width = Math.max( width, (window.getWidth()-WIDTH_SLACK) );
		}
		setSize(width, DEFAULT_HEIGHT);
		if (window!=null)
		{
			GuiUtilities.centerDialog(window, this);
		}
		setVisible(true);
	}

	private final java.awt.Window window;
	private final Exception exception;
	
	private JTextArea detailsArea = new JTextArea();
	private boolean detailsShown = false;
	
	
//	public static void main(String[] args)
//	{
//		SwingUtilities.invokeLater(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				JFrame frame = new JFrame();
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				JPanel panel = new JPanel();
//				panel.add(new JLabel("Hello world"));
//				JButton button = new JButton("Dispaly");
//				button.addActionListener(new ActionListener()
//				{
//					
//					@Override
//					public void actionPerformed(ActionEvent e)
//					{
//						ExceptionMessageBox emb = new ExceptionMessageBox(frame, new RuntimeException("hello"));
//						
//					}
//				});
//				panel.add(button);
//				frame.setContentPane(panel);
//				frame.pack();
//				frame.setVisible(true);
//				
//			}
//		});
//	}
}
