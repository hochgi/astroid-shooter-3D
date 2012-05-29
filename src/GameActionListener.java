import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameActionListener implements ActionListener {

	private ButtonEnum action;
	private Game game;

	public GameActionListener(Game game, ButtonEnum be) {
		this.action = be;
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (action) {
		case RCR:
			game.reverseCubesRotation();
			break;
		case ICC:
			game.inverseCubesColors();
			break;
		case CTS:
			game.changeTetrahedronSpeed();
			break;
		case RCP:
			game.resetOrientation();
			break;
		default:
			break;
		}
		Exercise3.canvas.requestFocus();
		Exercise3.miniMap.repaint();
	}
}
