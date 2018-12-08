import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	
	private BufferedImage itemImage;
	
	public Tile(int candy) {
		try {
            itemImage = ImageIO.read(new File("img/" + (candy+1) + ".png"));
        } catch (IOException e ) {
            e.printStackTrace();
        }
	}
	
	public void draw(Graphics g, int x, int y) {
        g.drawImage(itemImage, x, y, TILE_SIZE, TILE_SIZE, null);
    }
	
}
