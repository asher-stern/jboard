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
		
		JLabel label = new JLabel("+ "+StringUtils.localized("details"));
		label.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (!detailsShown)
				{
					label.setText("- "+StringUtils.localized("details"));
					mainPanel.add(detailsArea);
					mainPanel.setSize(getWidth(), getHeight()+detailsArea.getHeight());
				}
				else
				{
					label.setText("- "+StringUtils.localized("details"));
					mainPanel.remove(detailsArea);
					mainPanel.setSize(getWidth(), getHeight()-detailsArea.getHeight());
				}
			}
		});
		mainPanel.add(label);
		
		setContentPane(mainPanel);
		
		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
		pack();
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
}
