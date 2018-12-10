
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;

public class Tile extends JButton{
	
	  
	private int x;
	private int y;
	private boolean selected; 
	private int c;
	
	
	public Tile(int candy, int x1, int y1) {
		super(new ImageIcon("files/" + candy + ".png"));
		this.setBackground(new Color(178, 178, 255));
		x = x1;
		y = y1;
		selected = false;
		c = candy;
	}
	
	public int[] getPoint(){
		int[] ret = new int[] {x,y};
		return ret;
	}
	
	public Tile select() {
		this.setOpaque(true);
		selected = true;
		return this;
	}
	
	public JButton deselect() {
		this.setOpaque(false);
		selected = false;
		return this;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public void setCandy(int candy) {
		c = candy;
		setIcon(new ImageIcon("files/" + candy + ".png"));
	}
	public int getCandy() {
		return c;
	}
	
}
