package org.jboard.jboard.utilities;

import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;

/**
 * 
 *
 * @author Asher Stern
 * Date: Aug 21, 2014
 *
 */
public class GuiUtilities
{
	public static void centerDialog(Window ownerWindow, JDialog dialog)
	{
		dialog.setLocationRelativeTo(ownerWindow);
		Dimension dimension = dialog.getSize();
		Dimension ownerDimension = ownerWindow.getSize();
		
		dialog.setLocation(
				(int)((ownerDimension.getWidth()/2)-(dimension.getWidth()/2)),
				(int)((ownerDimension.getHeight()/2)-(dimension.getHeight()/2))
				);
	}

}
