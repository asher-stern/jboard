package org.jboard.jboard.gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jboard.jboard.Constants;
import org.jboard.jboard.bridge.ChessSystem;
import org.jboard.jboard.chess.BoardState;
import org.jboard.jboard.chess.BoardStateUtils;
import org.jboard.jboard.chess.ChessRulesUtils;
import org.jboard.jboard.chess.ColoredPiece;
import org.jboard.jboard.chess.InitialStateFactory;
import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.SquareCoordinates;
import org.jboard.jboard.chess.WhiteBlack;
import org.jboard.jboard.gui.Images;
import org.jboard.jboard.gui.ScaledImages;




import static org.jboard.jboard.Constants.*;

/**
 * A JPanel in which the chess board is printed.
 * This panel is also responsible for keyboard events which move pieces on the board.
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel implements BoardResponder, KeyListener
{
	public BoardPanel(Frame ownerFrame, Images<BufferedImage> images)
	{
		super();
		this.ownerFrame = ownerFrame;
		addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent evt)
			{
				requestFocusInWindow();
			}
		});
		addKeyListener(this);
		setFocusable(true);
		this.images = images;
		
		currentBoardState = InitialStateFactory.getInitialBoardState();
	}
	
	/**
	 * Registers a {@link BoardActivator} into which commands from the user are sent.
	 * This {@link BoardActivator} receives commands from the user, like performing a move.
	 * These commands are then forwarded into the chess engine.
	 * {@link BoardActivator} is typically implemented as the {@link ChessSystem}.
	 * 
	 * @param boardActivator a {@link BoardActivator} into which commands from the user are sent.
	 */
	public void registerActivator(BoardActivator boardActivator)
	{
		this.boardActivator = boardActivator;
	}
	
	/**
	 * Sets whether the user can perform moves (moving a piece) on this board.
	 * The common practice is that when the game did not yet start, and there
	 * is no chess engine up and running, then the user would not be able
	 * to move pieces on the board.
	 * 
	 * @param moveEnabled true/false indicate whether the user would be able to perform moves on this board.
	 */
	public void setMoveEnabled(boolean moveEnabled)
	{
		this.moveEnabled = moveEnabled;
	}

//	/**
//	 * Resets the board to the initial state. The game can start now.
//	 */
//	public void resetBoard()
//	{
//		boardStateList = new LinkedList<>();
//		boardStateList.add(InitialStateFactory.getInitialBoardState());
//		setAppearsDown(WhiteBlack.WHITE);
//		repaint();
//	}
	

	/**
	 * Returns the color which appears at the bottom of the board. The default
	 * is that the white would be at the bottom (the bottom line is "1", the upper
	 * line is "8"). When the user plays with the black pieces, then it is more
	 * convenient that the black would appear at the bottom.
	 * 
	 * @return the color which appears at the bottom.
	 */
	public WhiteBlack getAppearsDown()
	{
		return appearsDown;
	}

	/**
	 * Sets which color would appear at the bottom of the board.
	 * White - means that the bottom line is "1". Black means that the bottom
	 * line is "8".
	 * 
	 * @param appearsDown the color that would appear at the bottom of the board.
	 */
	public void setAppearsDown(WhiteBlack appearsDown)
	{
		this.appearsDown = appearsDown;
		repaint();
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
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyReleased(KeyEvent e){}


	/*
	 * (non-Javadoc)
	 * @see org.jboard.jboard.gui.board.BoardResponder#movePerformed(org.jboard.jboard.chess.Move)
	 */
	@Override
	public void movePerformed(Move move)
	{
		nowAnimatedEngineMove = move;
		triggerMove(move.getSource(),move.getDestination());
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see org.jboard.jboard.gui.board.BoardResponder#setState(org.jboard.jboard.chess.BoardState)
	 */
	public void setState(BoardState boardState)
	{
		currentBoardState = boardState;
		repaint();
	}
	
	

	/**
	 * Called when either the user or the engine (the computer) performed a move,
	 * and displays an animation of the piece moving from its source position
	 * to its destination.
	 * <BR>
	 * If this method was called to display the move performed by the engine
	 * (the computer), then the member field {@link #nowAnimatedEngineMove}
	 * would hold the engine's move. Otherwise, it would be null.
	 * 
	 * @param source
	 * @param destination
	 */
	protected void triggerMove(SquareCoordinates source, SquareCoordinates destination)
	{
		if (null==animatedMovingPieceImage) // nothing is moving at this very moment.
		{
			if ( (source!=null) && (destination!=null) )
			{
				// Start a thread that would move a piece on the board.
				Thread animationThread = new Thread(new AnimationRunnable(source, destination));
				animationThread.start();
			}
			// This function will continue in the continueTriggerMove,
			// called by the animation thread.
		}
	}
	
	/**
	 * Called after the animation has finished, and adds a new {@link BoardState}
	 * to the list of board states, which reflects the board after the move
	 * has been performed.
	 * <BR>
	 * This method is called only for user's move, not for engine's move.
	 * 
	 * @param source The original position of the moving piece. 
	 * @param destination The destimation of the moving piece.
	 */
	protected void continueTriggerMove(SquareCoordinates source, SquareCoordinates destination)
	{
		ColoredPiece piece = getActiveBoardState().getPositions().get(source);
		Piece promotion = null;
		if (piece!=null)
		{
			if (ChessRulesUtils.promotionTriggered(source,destination,piece))
			{
				WhiteBlack color = getActiveBoardState().getPositions().get(source).getColor();
				promotion = PromotionDialog.showPromotionDialog(ownerFrame, color,
						images, imageActualWidth, imageActualHeight);
			}
			Move move = new Move(source, destination, promotion);
			performMove(move);
		}
	}
	
	/**
	 * Adds a new {@link BoardState} to the list of board states, which reflects
	 * the board after the move has been performed.
	 * 
	 * @param move an object represents a move.
	 */
	protected void performMove(Move move)
	{
		BoardState stateAfterMove = BoardStateUtils.performMove(currentBoardState, move);
		currentBoardState = stateAfterMove;
		repaint();
		boardActivator.makeMove(move);
	}
	
	/**
	 * Called after the animation which displayed the move performed by
	 * the engine has been finished, and updates the board to reflect
	 * the new board-state after that move.
	 */
	protected void continueEngineMoveAfterAnimation()
	{
		Move move = nowAnimatedEngineMove;
		nowAnimatedEngineMove = null;
		BoardState stateAfterMove = BoardStateUtils.performMove(currentBoardState, move);
		currentBoardState = stateAfterMove;
		repaint();
	}

	
	/**
	 * Repaints a rectangle of this {@link BoardPanel} which displays
	 * the given square.
	 * @param square
	 */
	protected void repaintSquare(SquareCoordinates square)
	{
		SquareArea area = areaOfSquare(square);
		repaint(area.getxStart(),area.getyStart(),imageActualWidth,imageActualHeight);
	}
	
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

	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);

		calculateBoardAndImageWidthAndHeight();

		paintSquares(graphics);
		paintStandingPieces(graphics);
		if (moveEnabled)
		{
			if (!(mark.equals(markSource))){paintMark(graphics,mark,Color.RED);}
			if (markSource!=null) {paintMark(graphics,markSource,Color.BLUE);}
		}

		
		if (animatedMovingPieceImage!=null)
		{
			graphics.drawImage(animatedMovingPieceImage, xAnimatedMovingPiece, yAnimatedMovingPiece, null);
		}
	}
	
	private void paintSquares(Graphics graphics)
	{
		for (char x=FIRST_COLUMN;x<FIRST_COLUMN+BOARD_SIZE;++x)
		{
			for (int y=1;y<1+BOARD_SIZE;++y)
			{
				WhiteBlack whiteBlack = ( ((x-FIRST_COLUMN)%2)==((y-1)%2) )?WhiteBlack.WHITE:WhiteBlack.BLACK;
				SquareArea area = areaOfSquare(new SquareCoordinates(x, y));
				
				graphics.drawImage(scaledImages.getImages().getSquareImages().get(whiteBlack),
						area.getxStart(), area.getyStart(), null);
				
//				graphics.drawImage(images.getSquareImages().get(whiteBlack),
//						area.getxStart(), area.getyStart(),
//						imageActualWidth, imageActualHeight, null);
			}
		}
	}
	
	private void paintStandingPieces(Graphics graphics)
	{
		BoardState boardState = getActiveBoardState();
		Map<SquareCoordinates, ColoredPiece> positions = boardState.getPositions();
		for (SquareCoordinates square : positions.keySet())
		{
			SquareArea area = areaOfSquare(square);
			ColoredPiece piece = positions.get(square);

			graphics.drawImage(scaledImages.getImages().getPieceImages().get(piece.getColor()).get(piece.getPiece()),
					area.getxStart(),
					area.getyStart(),null);

			
//			graphics.drawImage(images.getPieceImages().get(piece.getColor()).get(piece.getPiece()),
//					area.getxStart(),
//					area.getyStart(),
//					imageActualWidth, imageActualHeight, null);
		}
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
	
	protected void calculateBoardAndImageWidthAndHeight()
	{
		final int boardRawWidth = getWidth();
		final int boardRawHeight = getHeight();
		
		double widthRatio = ((double) boardRawWidth) /
				( (double) (BOARD_SIZE*images.getWidthOfSubImage()));
		double heightRatio = ((double) boardRawHeight) /
				( (double) (BOARD_SIZE*images.getHeightOfSubImage()));
		
		double ratio = Math.min(widthRatio, heightRatio);
		
		boardWidth = (int)(ratio * ((double) boardRawWidth));
		boardHeight = (int)(ratio * ((double) boardRawHeight));
		
		imageActualWidth = (int)(ratio * ((double) images.getWidthOfSubImage()));
		imageActualHeight = (int)(ratio * ((double) images.getHeightOfSubImage()));
		
		boardFirstColumn = (getWidth()-BOARD_SIZE*imageActualWidth)/2;
		boardFirstColumn = Math.max(0, boardFirstColumn);

		if (!(ScaledImages.compatibleWith(scaledImages, boardWidth, boardHeight)))
		{
			scaledImages = ScaledImages.createScaledImages(images,boardWidth,boardHeight,imageActualWidth,imageActualHeight);
		}
	}
	
	protected BoardState getActiveBoardState()
	{
		if (temporaryBoardState!=null)
		{
			return temporaryBoardState;
		}
		else
		{
			return currentBoardState;
		}
	}
	
	protected int getRowCoordinate(int zeroStartingRowCoordinate)
	{
		if (WhiteBlack.WHITE.equals(appearsDown))
		{
			return BOARD_SIZE-1-zeroStartingRowCoordinate;
		}
		else
		{
			return zeroStartingRowCoordinate;
		}
		
	}

	protected int getColumnCoordinate(int zeroStartingColumnCoordinate)
	{
		if (WhiteBlack.WHITE.equals(appearsDown))
		{
			return zeroStartingColumnCoordinate;
		}
		else
		{
			return BOARD_SIZE-1-zeroStartingColumnCoordinate;
		}
		
	}

	
	protected SquareArea areaOfSquare(SquareCoordinates square)
	{
		int x = boardFirstColumn+imageActualWidth*getColumnCoordinate(square.getColumnAsZeroStartingCoordinate());
		int y = imageActualHeight*getRowCoordinate(square.getRowAsZeroStartingCoordinate());
		return new SquareArea(x,y,x+imageActualWidth,y+imageActualHeight);
	}
	

	
	protected final Frame ownerFrame;
	protected final Images<BufferedImage> images;
	
	protected BoardActivator boardActivator;
	protected boolean moveEnabled = true;

	protected BoardState currentBoardState;

	protected int boardWidth = 1;
	protected int boardHeight = 1;
	protected int imageActualWidth = 1;
	protected int imageActualHeight = 1;
	protected int boardFirstColumn = 0;
	protected ScaledImages scaledImages = null;
	
	protected WhiteBlack appearsDown = WhiteBlack.WHITE;
	
	protected SquareCoordinates mark = new SquareCoordinates('e', 2);
	protected SquareCoordinates markSource = null;
	
	protected Move nowAnimatedEngineMove = null;
	protected BoardState temporaryBoardState = null;
	protected Image animatedMovingPieceImage = null;
	protected int xAnimatedMovingPiece = 0;
	protected int yAnimatedMovingPiece = 0;
	

	
	
	
	
	
	//////////////////// NESTED CLASS AnimationRunnable ////////////////////
	
	/**
	 * Makes an animated move of a piece from one square to another.
	 * This class is used as the Runnable of a separated thread which changes the location of the piece on the
	 * board every few milliseconds.
	 * <BR>
	 * Note that animation is used both for user moves and computer (engine) moves.
	 *
	 *
	 */
	protected class AnimationRunnable implements Runnable
	{
		public AnimationRunnable(SquareCoordinates source,
				SquareCoordinates destination)
		{
			super();
			this.source = source;
			this.destination = destination;
		}

		@Override
		public void run()
		{
			ColoredPiece movingPiece = getActiveBoardState().getPositions().get(source);
			
//			BufferedImage originalImage = images.getPieceImages().get(movingPiece.getColor()).get(movingPiece.getPiece());
//			animatedMovingPieceImage = originalImage.getScaledInstance(imageActualWidth, imageActualHeight, Image.SCALE_DEFAULT);
			
			animatedMovingPieceImage = scaledImages.getImages().getPieceImages().get(movingPiece.getColor()).get(movingPiece.getPiece());
			
			if (animatedMovingPieceImage==null) throw new RuntimeException();
			try
			{
				temporaryBoardState = BoardStateUtils.temporarilyRemovePiece(getActiveBoardState(),source);
				try
				{
					sourceArea = areaOfSquare(source);
					destinationArea = areaOfSquare(destination);
					upDirection = destinationArea.getyStart()<sourceArea.getyStart();
					leftDirection = destinationArea.getxStart()<sourceArea.getxStart();
					int x = sourceArea.getxStart();
					int y = sourceArea.getyStart();

					final double lengthX = Math.abs(destinationArea.getxStart()-sourceArea.getxStart());
					final double lengthY = Math.abs(destinationArea.getyStart()-sourceArea.getyStart());

					int addX = leftDirection?(-Constants.ANIMATION_PIXEL_STEP):Constants.ANIMATION_PIXEL_STEP;
					int addY = upDirection?(-Constants.ANIMATION_PIXEL_STEP):Constants.ANIMATION_PIXEL_STEP;

					int aggregateX = 0;
					int aggregateY = 0;

					boolean stopWhile = done(x,y);
					while (!stopWhile)
					{
						if (lengthX>lengthY)
						{
							x+=addX;
							aggregateX+=Math.abs(addX);
							aggregateY = (int) ( (lengthY/lengthX)*((double)aggregateX) );
							if (upDirection) {y=sourceArea.getyStart()-aggregateY;}
							else {y=sourceArea.getyStart()+aggregateY;}
						}
						else
						{
							y+=addY;
							aggregateY+=Math.abs(addY);
							aggregateX = (int) ( (lengthX/lengthY)* ( (double)aggregateY ) );
							if (leftDirection){x=sourceArea.getxStart()-aggregateX;}
							else {x=sourceArea.getxStart()+aggregateX;}
						}
						
						stopWhile = done(x,y);
						if (!stopWhile)
						{
							xAnimatedMovingPiece=x;
							yAnimatedMovingPiece=y;
							repaint(
									Math.min(sourceArea.getxStart(),destinationArea.getxStart()),
									Math.min(sourceArea.getyStart(),destinationArea.getyStart()),
									(int)lengthX+imageActualWidth,
									(int)lengthY+imageActualHeight);
						}
						try
						{
							Thread.sleep(ANIMATION_SLEEP_MS);
						}
						catch (InterruptedException e)
						{
							throw new RuntimeException(e);
						}
					}
				}
				finally
				{
					temporaryBoardState=null;
				}
			}
			finally
			{
				animatedMovingPieceImage = null;
			}
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					if (nowAnimatedEngineMove!=null)
					{
						continueEngineMoveAfterAnimation();
					}
					else
					{
						continueTriggerMove(source, destination);
					}
				}
			});
		}
		
		private boolean done(int x, int y)
		{
			boolean xDone = false;
			if (leftDirection)
			{
				xDone = (x<=destinationArea.getxStart());
			}
			else
			{
				xDone = (x>=destinationArea.getxStart());
			}
			
			boolean yDone = false;
			if (upDirection)
			{
				yDone = (y<=destinationArea.getyStart());
			}
			else
			{
				yDone = (y>=destinationArea.getyStart());
			}
			
			return xDone&&yDone;
		}
		
		private final SquareCoordinates source;
		private final SquareCoordinates destination;
		
		private SquareArea sourceArea;
		private SquareArea destinationArea;
		private boolean upDirection = false;
		private boolean leftDirection = false;

	}
	
}
