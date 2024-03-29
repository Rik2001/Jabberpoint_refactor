import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;


/** <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem {
  private BufferedImage bufferedImage;
  private final String IMAGENAME;
  protected static final String FILE = "File ";
  protected static final String NOTFOUND = " not found";

	/**
	 * a bitmapItem is an image which can be displayed in a presentation.
	 * @param level indicates the item-level
	 * @param name indicates the name of the file with the image
	 */
	public BitmapItem(int level, String name) {
		super(level);
		IMAGENAME = name;
		try {
			bufferedImage = ImageIO.read(new File(IMAGENAME));
		}
		catch (IOException e) {
			System.err.println(FILE + IMAGENAME + NOTFOUND) ;
		}
	}

	/**
	 * gets the name of the image
	 * @return the filename of the image
	 */
	public String getName() {
		return IMAGENAME;
	}

	//Returns the bounding box of the image
	public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle((int) (myStyle.indent * scale), 0,
				(int) (bufferedImage.getWidth(observer) * scale),
				((int) (myStyle.leading * scale)) + 
				(int) (bufferedImage.getHeight(observer) * scale));
	}

	public void draw(int x, int y, float scale, Graphics graphics, Style myStyle, ImageObserver observer) {
		int width = x + (int) (myStyle.indent * scale);
		int height = y + (int) (myStyle.leading * scale);
		graphics.drawImage(bufferedImage, width, height,(int) (bufferedImage.getWidth(observer)*scale),
                (int) (bufferedImage.getHeight(observer)*scale), observer);
	}

	public String toString() {
		return "BitmapItem[" + getLevel() + "," + IMAGENAME + "]";
	}
}
