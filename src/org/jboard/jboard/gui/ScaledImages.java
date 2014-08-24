package org.jboard.jboard.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.WhiteBlack;

/**
 * Stores a scaled version of all the images which are stored in the class {@link Image}.
 *
 * @author Asher Stern
 * Date: Aug 18, 2014
 *
 */
public class ScaledImages
{
	public static boolean compatibleWith(final ScaledImages scaledImages, final int width, final int height)
	{
		if (scaledImages==null)
		{
			return false;
		}
		else
		{
			return ( (scaledImages.getWidth()==width)&&(scaledImages.getHeight()==height) );
		}
	}
	
	public static ScaledImages createScaledImages(Images<BufferedImage> images, int width, int height,
			int imageActualWidth, int imageActualHeight)
	{
		Map<WhiteBlack, Image> squareImages = new LinkedHashMap<>();
		for (WhiteBlack color : WhiteBlack.values())
		{
			
			squareImages.put(color, 
					images.getSquareImages().get(color).getScaledInstance(imageActualWidth, imageActualHeight, Image.SCALE_SMOOTH)
					);
		}
		
		Map<WhiteBlack, Map<Piece, Image>> pieceImages = new LinkedHashMap<>();
		for (WhiteBlack color : WhiteBlack.values())
		{
			Map<Piece, Image> coloredPieceImages = new LinkedHashMap<>();
			pieceImages.put(color, coloredPieceImages);
			for (Piece piece : Piece.values())
			{
				coloredPieceImages.put(piece,
						images.getPieceImages().get(color).get(piece).getScaledInstance(imageActualWidth, imageActualHeight, Image.SCALE_SMOOTH)
						);
			}
		}
		
		Images<Image> newImages = new Images<Image>(pieceImages,squareImages,imageActualWidth,imageActualHeight);
		
		return new ScaledImages(newImages,width,height);
	}
	
	public ScaledImages(Images<Image> images, int width, int height)
	{
		super();
		this.images = images;
		this.width = width;
		this.height = height;
	}
	
	
	
	public Images<Image> getImages()
	{
		return images;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}



	private final Images<Image> images;
	private final int width;
	private final int height;
}
