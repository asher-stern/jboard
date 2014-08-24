package org.jboard.jboard.sandbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jboard.jboard.chess.Piece;
import org.jboard.jboard.chess.WhiteBlack;
import org.jboard.jboard.gui.ImagesLoader;

@SuppressWarnings("serial")
public class TryGui extends JPanel implements MouseListener
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			createAndShowGui();
		}
		catch(Throwable t)
		{
			t.printStackTrace(System.out);
		}

	}
	
	
	


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawOval(10, 10, 200, 200);
		
		if (image!=null)
		{
			g.drawImage(image,20,20,
					Color.GREEN,
					imageObserver);
		}
		
		if (imageForMouse!=null)
		{
			g.drawImage(imageForMouse, xMouse, yMouse, imageObserver);
		}
	}




	private static boolean grayInImage(BufferedImage loadedImage, int x, int y)
	{
		ColorModel colorModel = ColorModel.getRGBdefault();
		int blue = colorModel.getBlue(loadedImage.getRGB(x, y));
		int red = colorModel.getRed(loadedImage.getRGB(x, y));
		int green = colorModel.getGreen(loadedImage.getRGB(x, y));
		if ( ( (blue>5) && (blue<250) ) &&
				( (red>5) && (red<250) ) &&
				( (green>5) && (green<250) )
				)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void createAndShowGui()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			
			@Override
			public void run()
			{
				try
				{
					JFrame frame = new JFrame("My Frame");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//frame.setSize(250,250);
					
					TryGui mainPanel = new TryGui();
					mainPanel.setPreferredSize(new Dimension(700,400));

					//System.out.println(this.getClass().getResource("a").toString());
					InputStream imageInputStream = this.getClass().getResourceAsStream("/classic.png");
					if (imageInputStream==null)
					{
						throw new RuntimeException("imageInputStream is null.");
					}
					BufferedImage loadedImage =	ImageIO.read(imageInputStream);
					System.out.println(loadedImage.getWidth());
					System.out.println(loadedImage.getHeight());
					//BufferedImage loadedImage =	ImageIO.read(new File("/media/asher/data2/data/asher/temp/eboard/eboard/classic.save.jpg"));
					//BufferedImage loadedImage =	ImageIO.read(new File("/media/asher/data2/data/asher/temp/eboard/eboard/classic.png"));
					BufferedImage convertedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
					for (int x=0;x<loadedImage.getWidth();++x)
					{
						for (int y=0;y<loadedImage.getHeight();++y)
						{
							if (!grayInImage(loadedImage,x,y))
							{
								convertedImage.setRGB(x, y, loadedImage.getRGB(x, y));
							}
						}
					}
					
					
//					BufferedImage loadedImage =	ImageIO.read(new File("/media/asher/data2/data/asher/temp/eboard/eboard/classic.save.jpg"));
//					Graphics imageGraphics = loadedImage.createGraphics();
//					//final int rgb = Color.PINK.getRGB();
//					//final int rgb = (0<<24)+(0<<16)+(0<<8)+0;
//					final int rgb = new Color(0.0f, 0.0f, 0.0f, 0.0f).getRGB();
//					//final int rgb = 0<<24+255<<16+0<<8+255;
//					
//					imageGraphics.setColor(new Color(0.0f, 0.0f, 0.0f, 1.0f));
//					imageGraphics.fillRect(10, 10, 50, 50);

//					for (int x=20;x<50;++x)
//					{
//						for (int y=20;y<50;++y)
//						{
//							loadedImage.setRGB(x, y, rgb);
//						}
//					}
					
					mainPanel.setImage(convertedImage);
					mainPanel.imagesLoader = new ImagesLoader();
					mainPanel.imagesLoader.load();
					mainPanel.addMouseListener(mainPanel);
					


					frame.add(mainPanel);
					frame.pack();
					frame.setVisible(true);

				}
				catch (Exception e)
				{
					e.printStackTrace(System.out);
				}
				finally{}
			}
		});
	}
	
	
	
	
	
	public BufferedImage getImage()
	{
		return image;
	}





	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		repaint(xMouse,yMouse,imagesLoader.getWidthOfSubImage(),imagesLoader.getHeightOfSubImage());
		
		WhiteBlack whiteBlack = WhiteBlack.values()[random.nextInt(WhiteBlack.values().length)];
		xMouse = e.getX();
		yMouse = e.getY();
		if (random.nextBoolean())
		{
			Piece piece = Piece.values()[random.nextInt(Piece.values().length)];
			imageForMouse = imagesLoader.getPieceImages().get(whiteBlack).get(piece);
		}
		else
		{
			imageForMouse = imagesLoader.getSquareImages().get(whiteBlack);
		}
		
		repaint(xMouse,yMouse,imagesLoader.getWidthOfSubImage(),imagesLoader.getHeightOfSubImage());
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}




	private static final ImageObserver imageObserver =
			new ImageObserver(){@Override public boolean imageUpdate(Image img, int infoflags, int x, int y,int width, int height){return false;}};

	private BufferedImage image = null;

	private Random random = new Random();
	private ImagesLoader imagesLoader = null;
	private BufferedImage imageForMouse = null;
	private int xMouse = 0;
	private int yMouse = 0;
	

	


	
}
