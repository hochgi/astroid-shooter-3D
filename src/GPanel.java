import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Game game;
	private int xOld;
	private int yOld;
	private boolean outOfScreen;

	public GPanel(Game game) {
		this.game = game;
	}

	public void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setBackground(Color.white);
		Vector pos = game.getCamPos();
		xOld = (int)pos.getX() - 3 + (this.getSize().width / 2);
		yOld = (int)pos.getZ() - 3 + (this.getSize().height / 2);
		
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.drawOval(xOld, yOld, 6, 6);
		g.drawLine(xOld, yOld+3, xOld+5, yOld+3);
		g.drawLine(xOld+3, yOld, xOld+3, yOld+5);
		Vector pos = game.getCamPos();
		int x = (int)(pos.getX()/3) - 3 + (this.getSize().width / 2);
		int y = (int)(pos.getZ()/3) - 3 + (this.getSize().height / 2);
		if(x > -2 && x < getWidth() + 2 && y > -2 && y < getHeight() + 2) {
			if(outOfScreen) {
				g.drawString("out of map", getWidth() / 2 - 27, getHeight() / 2 + 4);
				outOfScreen = false;
			}
			g.setColor(Color.red);
			g.drawOval(x, y, 6, 6);
			g.drawLine(x, y+3, x+5, y+3);
			g.drawLine(x+3, y, x+3, y+5);
		}
		else if(!outOfScreen){
			g.setColor(Color.black);
			g.drawString("out of map", getWidth() / 2 - 27, getHeight() / 2 + 4);
			outOfScreen = true;
		}
		this.xOld = x;
		this.yOld = y;
	}
}
