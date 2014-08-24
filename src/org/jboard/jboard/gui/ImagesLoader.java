package org.jboard.jboard.gui;

import static org.jboard.jboard.Constants.*;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.WhiteBlack;



/**
 * Loads the images (which will be stored in {@link Images}) from a file.
 * This file is an image file with all the images in two rows - the upper row with all the white
 * images, and the lower row with all the black images.
 * 
 * @author Asher Stern
 * Date: Jul 15, 2014
 *
 */
public class ImagesLoader
{
	public void load()
	{
		try
		{
			final Piece[] piecesInImage = new Piece[]{Piece.ROOK,Piece.KNIGHT,Piece.BISHOP,Piece.QUEEN,Piece.KING,Piece.PAWN};
			final int subimagesInRow = piecesInImage.length+1;
			pieceImages = new LinkedHashMap<>();
			Map<Piece, BufferedImage> whiteMap = new LinkedHashMap<>();
			pieceImages.put(WhiteBlack.WHITE, whiteMap);
			Map<Piece, BufferedImage> blackMap = new LinkedHashMap<>();
			pieceImages.put(WhiteBlack.BLACK, blackMap);

			squareImages = new LinkedHashMap<>();

			InputStream imageInputStream = this.getClass().getResourceAsStream(IMAGE_RESOURCE_PATH);
			if (null==imageInputStream) {throw new RuntimeException("Cannot load pieces and squares image");}
			try
			{
				BufferedImage loadedImage = ImageIO.read(imageInputStream);
				widthOfSubImage = loadedImage.getWidth()/subimagesInRow;
				heightOfSubImage = loadedImage.getHeight()/WhiteBlack.values().length;
				int heightStart = 0;
				int heightEnd = heightStart+heightOfSubImage-1;
				for (WhiteBlack whiteBlack : WhiteBlack.values())
				{
					Map<Piece, BufferedImage> pieceMap = pieceImages.get(whiteBlack);
					int widthStart = 0;
					int widthEnd = widthStart+widthOfSubImage-1;

					pieceMap.put(Piece.ROOK, createSubImage(loadedImage,widthStart,heightStart,widthEnd,heightEnd));
					widthStart += widthOfSubImage;
					widthEnd = widthStart+widthOfSubImage-1;

					pieceMap.put(Piece.KNIGHT, createSubImage(loadedImage,widthStart,heightStart,widthEnd,heightEnd));
					widthStart += widthOfSubImage;
					widthEnd = widthStart+widthOfSubImage-1;

					pieceMap.put(Piece.BISHOP, createSubImage(loadedImage,widthStart,heightStart,widthEnd,heightEnd));
					widthStart += widthOfSubImage;
					widthEnd = widthStart+widthOfSubImage-1;

					pieceMap.put(Piece.QUEEN, createSubImage(loadedImage,widthStart,heightStart,widthEnd,heightEnd));
					widthStart += widthOfSubImage;
					widthEnd = widthStart+widthOfSubImage-1;

					pieceMap.put(Piece.KING, createSubImage(loadedImage,widthStart,heightStart,widthEnd,heightEnd));
					widthStart += widthOfSubImage;
					widthEnd = widthStart+widthOfSubImage-1;

					pieceMap.put(Piece.PAWN, createSubImage(loadedImage,widthStart,heightStart,widthEnd,heightEnd));
					widthStart += widthOfSubImage;
					widthEnd = widthStart+widthOfSubImage-1;

					squareImages.put(whiteBlack, loadedImage.getSubimage(widthStart, heightStart, widthOfSubImage, heightOfSubImage));

					heightStart += heightOfSubImage;
					heightEnd = heightStart+heightOfSubImage-1;
				}
			}
			finally
			{
				if (imageInputStream!=null)
				{
					try{imageInputStream.close();}catch(IOException ioex){}
				}
			}

		}
		catch(RuntimeException e){throw e;}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}




	public Map<WhiteBlack, Map<Piece, BufferedImage>> getPieceImages()
	{
		return pieceImages;
	}
	
	public Map<WhiteBlack, BufferedImage> getSquareImages()
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




	private BufferedImage createSubImage(BufferedImage loadedImage, int widthStart, int heightStart, int widthEnd, int heightEnd)
	{
		BufferedImage convertedImage = new BufferedImage(widthEnd-widthStart+1, heightEnd-heightStart+1, BufferedImage.TYPE_INT_ARGB);
		for (int x=widthStart;x<=widthEnd;++x)
		{
			for (int y=heightStart;y<=heightEnd;++y)
			{
				if (!(grayInImage(loadedImage, x, y)))
				{
					int rgb = loadedImage.getRGB(x, y);
					convertedImage.setRGB(x-widthStart, y-heightStart, rgb);
				}
			}
		}
		return convertedImage;
	}

	

	private static boolean grayInImage(BufferedImage loadedImage, int x, int y)
	{
		ColorModel colorModel = ColorModel.getRGBdefault();
		int blue = colorModel.getBlue(loadedImage.getRGB(x, y));
		int red = colorModel.getRed(loadedImage.getRGB(x, y));
		int green = colorModel.getGreen(loadedImage.getRGB(x, y));
		if ( ( (blue>20) && (blue<235) ) ||
				( (red>20) && (red<235) ) ||
				( (green>20) && (green<235) )
				)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private Map<WhiteBlack, Map<Piece, BufferedImage>> pieceImages;
	private Map<WhiteBlack, BufferedImage> squareImages;
	
	private int widthOfSubImage;
	private int heightOfSubImage;

}
