package org.jboard.jboard.bridge;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jboard.jboard.chess.Move;
import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.WhiteBlack;
import org.jboard.jboard.chessengine.ChessEngine;
import org.jboard.jboard.chessengine.ChessEngineProcess;
import org.jboard.jboard.chessengine.MoveCommand;
import org.jboard.jboard.chessengine.PipelinedChessEngine;
import org.jboard.jboard.gui.Images;
import org.jboard.jboard.gui.board.BoardPanel;
import org.jboard.jboard.gui.board.BoardResponder;
import static org.jboard.jboard.utilities.StringUtils.localized;

/**
 * Receives/sends commands from/to the human player to/from the chess engine.
 * Most of the commands are reflected in the board (the {@link BoardPanel}).
 * 
 * The components {@link ChessEngineProcess}, {@link PipelinedChessEngine},
 * {@link ChessSystem} and {@link BoardPanel} are constructed with the lower level component,
 * and register the upper level component.
 * <BR>
 * Consequently, {@link ChessSystem} is constructed with the {@link BoardPanel} as a constructor
 * parameter. It registers the {@link ChessEngine} in order to send it commands
 * (commands from {@link ChessSystem} to {@link ChessEngine}).
 * 
 * @author Asher Stern
 * Date: Jul 31, 2014
 *
 */
public class ChessSystem implements ChessActivator, ChessResponder
{
	public ChessSystem(BoardResponder boardResponder, java.awt.Window mainFrame, Images<?> images)
	{
		this.boardResponder = boardResponder;
		this.mainFrame = mainFrame;
		this.images = images;
	}
	
	public void registerEngine(ChessEngine engine)
	{
		this.engine = engine;
	}
	

	@Override
	public void makeMove(Move move)
	{
		engine.sendCommand(new MoveCommand(move));
	}

	@Override
	public void offerDraw()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptDraw()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resign()
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
	// ChessResponder
	
	@Override
	public void movePerformed(Move move)
	{
		boardResponder.movePerformed(move);
	}

	@Override
	public void lastMoveRegected(String comment)
	{
		JOptionPane.showMessageDialog(mainFrame, comment, localized("illegal_move"), JOptionPane.ERROR_MESSAGE);
		boardResponder.cancelLastMove();
	}

	@Override
	public void endGameDeclared(WhiteBlack winner, String comment)
	{
		if (winner!=null)
		{
			String winnerString = (winner==WhiteBlack.WHITE)?localized("winner_white"):localized("winner_black");
			Icon icon = new ImageIcon(images.getPieceImages().get(winner).get(Piece.KING));
			JOptionPane.showMessageDialog(mainFrame, winnerString+"\n"+comment, localized("game_over"), JOptionPane.INFORMATION_MESSAGE,icon);
		}
		else
		{
			JOptionPane.showMessageDialog(mainFrame, localized("draw")+"\n"+comment, localized("game_over"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void drawOffered()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawAccepted()
	{
		// TODO Auto-generated method stub
		
	}





	protected final BoardResponder boardResponder;
	protected final java.awt.Window mainFrame;
	protected final Images<?> images;
	protected ChessEngine engine;
	

}
