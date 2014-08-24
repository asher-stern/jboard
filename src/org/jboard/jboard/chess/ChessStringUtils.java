package org.jboard.jboard.chess;

/**
 * String utilities related to the chess game.
 * 
 * @author Asher Stern
 * Date: 2014
 *
 */
public class ChessStringUtils
{
	/**
	 * Converts a string into a {@link Move}.
	 * @param moveString
	 * @return
	 */
	public static Move stringToMove(String moveString)
	{
		char[] array = moveString.toCharArray();
		int index=0;
		char columnSource = array[index];
		++index;
		char rowSourceChar = array[index];
		int rowSource = Integer.parseInt(String.valueOf(rowSourceChar));
		++index;
		char columnDestination = array[index];
		++index;
		char rowDestinationChar = array[index];
		int rowDestination = Integer.parseInt(String.valueOf(rowDestinationChar));
		++index;
		Piece promotion = null;
		if (array.length>index)
		{
			char promotionChar = array[index];
			promotion = Piece.fromChar(promotionChar);
		}
		return new Move(new SquareCoordinates(columnSource, rowSource),
				new SquareCoordinates(columnDestination, rowDestination),
				promotion);
	}


	/**
	 * Converts a {@link Move} into a string.
	 * @param move
	 * @return
	 */
	public static String moveToString(Move move)
	{
		String ret = coordinatesToString(move.getSource())+
				coordinatesToString(move.getDestination());
		if (move.getPromotion()!=null)
		{
			ret = ret+move.getPromotion().getItsChar();
		}
		return ret;
	}
	
	
	/**
	 * Converts a {@link SquareCoordinates} into a string.
	 * @param coordinates
	 * @return
	 */
	public static String coordinatesToString(SquareCoordinates coordinates)
	{
		return ""+coordinates.getColumn()+coordinates.getRow();
	}
	

}
