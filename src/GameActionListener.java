import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * instead off making a different listener for every button,
 * and because i didn't want to use anonymous classes,
 * i made this -somehow awkward- class. 
 * @author gilad
 *
 */
public class GameActionListener implements ActionListener {

	private ButtonEnum action;
	private Game game;

	/**
	 * a simple setter constructor
	 * @param game
	 * @param be
	 */
	public GameActionListener(Game game, ButtonEnum be) {
		this.action = be;
		this.game = game;
	}

	/**
	 * inherited method implementation
	 */
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
		case RCO:
			game.resetOrientation();
			//after orientation were reset, we want to update the map =)
			Exercise4.miniMap.repaint();
			break;
		default:
			break;
		}
		Exercise4.canvas.requestFocus();		
	}
}
