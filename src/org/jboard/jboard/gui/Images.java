package org.jboard.jboard.gui;

import java.awt.Image;
import java.util.Map;

import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.WhiteBlack;

/**
 * Contains images of squares (the square background, on which pieces are positioned)
 * and images of the pieces.
 * <BR>
 * Note that all the images have the same width and the same height.
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public class Images<I extends Image>
{
	public Images(Map<WhiteBlack, Map<Piece, I>> pieceImages,
			Map<WhiteBlack, I> squareImages, int widthOfSubImage,
			int heightOfSubImage)
	{
		super();
		this.pieceImages = pieceImages;
		this.squareImages = squareImages;
		this.widthOfSubImage = widthOfSubImage;
		this.heightOfSubImage = heightOfSubImage;
	}
	
	
	
	
	public Map<WhiteBlack, Map<Piece, I>> getPieceImages()
	{
		return pieceImages;
	}
	public Map<WhiteBlack, I> getSquareImages()
	{
		return squareImages;
	}
	public int getWidthOfSubImage()
	{
		return widthOfSubImage;
	}
	public int getHeightOfSubImage()
	{
		return heightOfSubImage;
	}




	private final Map<WhiteBlack, Map<Piece, I>> pieceImages;
	private final Map<WhiteBlack, I> squareImages;
	
	private final int widthOfSubImage;
	private final int heightOfSubImage;
}
