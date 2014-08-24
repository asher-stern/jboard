package org.jboard.jboard.gui.board;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.WhiteBlack;
import org.jboard.jboard.gui.Images;
import org.jboard.jboard.utilities.GuiUtilities;

/**
 * 
 * @author Asher Stern
 * Date: Jul 17, 2014
 *
 */
@SuppressWarnings("serial")
public class PromotionDialog extends JDialog implements ActionListener
{
	public static Piece showPromotionDialog(Frame owner, WhiteBlack color,
			Images<?> images, int imageActualWidth, int imageActualHeight)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			final PromotionDialog promotionDialog = new PromotionDialog(owner,"Promotion", color, images, imageActualWidth, imageActualHeight);
			promotionDialog.setVisible(true);
			return promotionDialog.getPiece();
		}
		else
		{
			throw new RuntimeException("showPromotionDialog must be called only from within the Event Dispatching Thread.");
		}
		
		
	}
	
	
	public PromotionDialog(Frame owner, String title,
			WhiteBlack color, Images<?> images,
			int imageActualWidth, int imageActualHeight)
	{
		super(owner, title, true);
		this.owner = owner;
		this.color = color;
		this.images = images;
		this.imageActualWidth = imageActualWidth;
		this.imageActualHeight = imageActualHeight;
		init();
	}
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("action command = "+e.getActionCommand());
		Piece selection = Piece.valueOf(e.getActionCommand());
		if (selection != null)
		{
			this.piece = selection;
		}
		setVisible(false);
		dispose();
	}
	
	
	
	public Piece getPiece()
	{
		return piece;
	}

	private void init()
	{
		setFocusTraversalPolicyProvider(true);
		JPanel buttonsPanel = new JPanel();
		
		for (Piece candidate : Piece.values())
		{
			if (candidate.isValidForPromotion())
			{
				Image image = images.getPieceImages().get(color).get(candidate);
				image = image.getScaledInstance(imageActualWidth, imageActualHeight, Image.SCALE_DEFAULT);
				Icon icon = new ImageIcon(image);
				
				JButton button = new JButton(icon);
				button.setActionCommand(candidate.name());
				button.addActionListener(this);
				buttonsPanel.add(button);
			}
		}
		
		this.add(buttonsPanel);
		pack();
		GuiUtilities.centerDialog(owner,this);
	}


	private final Frame owner;
	private final WhiteBlack color;
	private final Images<?> images;
	private final int imageActualWidth;
	private final int imageActualHeight;


	private Piece piece = Piece.QUEEN;
}
