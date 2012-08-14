package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;

/**
 * some explanations is given here, for this is a different kind of sprite.
 * basically, it deals with sprites that is being determined by 2 dots (line).
 * like a smoke trail...
 * and since the sprite can't be perpendicular to camera on all 3 axis,
 * we now compute the "most perpendicular it can be" 
 * @author gilad
 *
 */
public abstract class LineSprite extends Sprite {

	private Vector pos2;
	private Vector[] bb;
	private final float rat;

	public LineSprite(Vector position, Vector position2, Orientation camera, float ratio) {
		super(position, camera);
		pos2 = position2;
		bb = new Vector[4];
		bb[0] = new Vector();
		bb[1] = new Vector();
		bb[2] = new Vector();
		bb[3] = new Vector();
		// ratio between length and width.
		// for example: an image that is 64px X 256px has 4:1 ratio...
		if(ratio < 1f) {
			rat = ratio;
		}
		else {
			rat = 1f / ratio;
		}
	}
	
	public void moveTail(Vector tail) {
		pos2.addMutate(tail, 1);
	}
	
	/**
	 * this is where we compute the "most perpendicular to camera" plane.
	 * we return a quad billboard that is semi-perpendicular to the camera...
	 */
	@Override
	protected Vector[] getQuadBillboard() {
		Vector pos1 = getPosition();
		Vector line = pos1.sub(pos2, 1);
		Vector pltp = getCam().getPosition().sub(pos1.add(pos2, 0.5f), 1);
		Vector perp = Vector.cross(pltp, line).normalize();
		perp.mulMutate(rat * line.len());

		bb[0] = (pos1.add(perp, 0.5f));
		bb[1] = (pos2.add(perp, 0.5f));
		bb[2] = (pos2.sub(perp, 0.5f));
		bb[3] = (pos1.sub(perp, 0.5f));
		
		return bb;
	}
}
