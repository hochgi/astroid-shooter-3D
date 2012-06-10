import javax.media.opengl.GLAutoDrawable;


public class Model3D extends Object3D {

	public Model3D(Vector axis, double angle, String objFile) {
		super(axis, angle);
		generateModelFromFile(objFile);
	}

	public Model3D(Vector position, Vector axis, double angle, String objFile) {
		super(position, axis, angle);
		generateModelFromFile(objFile);
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		// TODO: implement.

	}

	@Override
	protected void update() {
		// TODO: implement.

	}
	
	private void generateModelFromFile(String objFile) {
		// TODO: implement. make use of loader: http://darksleep.com/oobjloader/
		
	}

}
