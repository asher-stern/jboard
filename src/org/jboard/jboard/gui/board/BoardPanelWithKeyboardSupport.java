package org.jboard.jboard.gui.board;

import static org.jboard.jboard.Constants.BOARD_SIZE;
import static org.jboard.jboard.Constants.MARK_SQUARE_STROKE;
import static org.jboard.jboard.Constants.POTENTIAL_DESTINATION_STROKE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.jboard.jboard.chess.ColoredPiece;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;
import org.jboard.jboard.chess.play.PlayOneStep;
import org.jboard.jboard.chess.play.PlayUtilities;
import org.jboard.jboard.gui.Images;

/**
 * 
 * 
 * @author Asher Stern
 * Date: Sep 21, 2014
 *
 */
@SuppressWarnings("serial")
public class BoardPanelWithKeyboardSupport extends BoardPanel implements KeyListener
{

	public BoardPanelWithKeyboardSupport(Frame ownerFrame, Images<BufferedImage> images)
	{
		super(ownerFrame, images);

		addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent evt)
			{
				requestFocusInWindow();
			}
		});
		addKeyListener(this);
		setFocusable(true);

	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (moveEnabled)
		{
			int keyCode = e.getKeyCode();
			if ( (keyCode==KeyEvent.VK_KP_LEFT) || (keyCode==KeyEvent.VK_LEFT) )
			{
				moveMark(Direction.LEFT);
			}
			else if ( (keyCode==KeyEvent.VK_KP_RIGHT) || (keyCode==KeyEvent.VK_RIGHT) )
			{
				moveMark(Direction.RIGHT);
			}
			else if ( (keyCode==KeyEvent.VK_KP_UP) || (keyCode==KeyEvent.VK_UP) )
			{
				moveMark(Direction.UP);
			}
			else if ( (keyCode==KeyEvent.VK_KP_DOWN) || (keyCode==KeyEvent.VK_DOWN) )
			{
				moveMark(Direction.DOWN);
			}
			else if (keyCode==KeyEvent.VK_ENTER)
			{
				if (potentialDestinations!=null) {deactivatePotentialDestinations();}
				
				SquareCoordinates save_markChosen = markSource;
				if (mark.equals(markSource))
				{
					markSource=null;
				}
				else if (markSource==null)
				{
					if (getActiveBoardState().getPositions().containsKey(mark))
					{
						markSource=mark;	
					}
				}
				else
				{
					triggerMove(markSource,mark);
					markSource = null;
				}
				if (save_markChosen!=null){repaintSquare(save_markChosen);}
				if (markSource!=null){repaintSquare(markSource);}
				repaintSquare(mark);
			}
			else if (keyCode==KeyEvent.VK_F1)
			{
				if (potentialDestinations!=null) {deactivatePotentialDestinations();}
				else {activatePotentialDestinations();}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyReleased(KeyEvent e){}

	
	
	/**
	 * In response to the user pressing on the arrow keys, this method
	 * moves the colored rectangle to another square on the board.
	 * @param direction
	 */
	protected void moveMark(Direction direction)
	{
		repaintSquare(mark);
		
		if (appearsDown==WhiteBlack.BLACK)
		{
			direction = direction.flipDirection();
		}
		char column = mark.getColumn();
		int row = mark.getRow();
		if (Direction.LEFT==direction) {if (column>'a') {--column;} }
		if (Direction.RIGHT==direction) {if (column<'h') {++column;} }
		if (Direction.UP==direction) {if (row<BOARD_SIZE) {++row;}}
		if (Direction.DOWN==direction) {if (row>1) {--row;}}
		mark = new SquareCoordinates(column, row);
		repaintSquare(mark);
	}
	
	
	@Override
	protected void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		
		if (moveEnabled)
		{
			if (!(mark.equals(markSource))){paintMark(graphics,mark,Color.RED);}
			if (markSource!=null) {paintMark(graphics,markSource,Color.BLUE);}
		}
		if (potentialDestinations!=null) {paintPotentialDestinations(graphics);}
	}
	
	private void paintMark(Graphics graphics, SquareCoordinates square, Color color)
	{
		Graphics2D graphics2D = (Graphics2D)graphics;
		graphics2D.setColor(color);
		graphics2D.setStroke(new BasicStroke(MARK_SQUARE_STROKE));
		SquareArea area = areaOfSquare(square);
		graphics2D.drawRect(area.getxStart()+((int)(MARK_SQUARE_STROKE/2)),
				area.getyStart()+((int)(MARK_SQUARE_STROKE/2)),
				imageActualWidth-((int)MARK_SQUARE_STROKE), imageActualHeight-((int)MARK_SQUARE_STROKE));
	}

	private void paintPotentialDestinations(Graphics graphics)
	{
		Graphics2D graphics2D = (Graphics2D)graphics;
		graphics2D.setColor(Color.GREEN);
		graphics2D.setStroke(new BasicStroke(POTENTIAL_DESTINATION_STROKE));

		for (SquareCoordinates square : potentialDestinations)
		{
			SquareArea area = areaOfSquare(square);
			graphics2D.drawOval(area.getxStart()+((int)(POTENTIAL_DESTINATION_STROKE/2)),
					area.getyStart()+((int)(POTENTIAL_DESTINATION_STROKE/2)),
					imageActualWidth-((int)POTENTIAL_DESTINATION_STROKE), imageActualHeight-((int)POTENTIAL_DESTINATION_STROKE));

		}
	}
	
	private void activatePotentialDestinations()
	{
		potentialDestinations = null;

		if (currentBoardState.getPositions().containsKey(mark))
		{
			// Calculate all moves the can be performed from the marked square.
			// Make this calculation in a separate thread, such that the GUI will not be frozen during this calculation.
			new Thread(
					new Runnable()
					{
						@Override
						public void run()
						{
							Set<SquareCoordinates> internalPotentialDestinations = new LinkedHashSet<SquareCoordinates>();
							ColoredPiece piece = currentBoardState.getPositions().get(mark);
							PlayOneStep playOneStep = PlayUtilities.getPlayOneStepForPiece(currentBoardState, mark, piece.getPiece(), true);
							for (Move move : playOneStep.calculateAllMoves())
							{
								internalPotentialDestinations.add(move.getDestination());
							}
							if (internalPotentialDestinations.size()>0)
							{
								potentialDestinations = internalPotentialDestinations;
								SwingUtilities.invokeLater(new Runnable()
								{
									@Override
									public void run()
									{
										repaint();
									}
								});
							}
							else
							{
								potentialDestinations = null;
							}
						}
					}).start();
		}
	}

	private void deactivatePotentialDestinations()
	{
		Set<SquareCoordinates> oldPotentialDestinations = potentialDestinations;
		potentialDestinations = null;
		for (SquareCoordinates square : oldPotentialDestinations)
		{
			repaintSquare(square);
		}
	}

	
	
	protected SquareCoordinates mark = new SquareCoordinates('e', 2);
	protected SquareCoordinates markSource = null;
	
	protected Set<SquareCoordinates> potentialDestinations = null;

}
