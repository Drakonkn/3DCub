import java.util.Vector;

public class Model {		
	private Point E = new Point( 20, -20, -20);
	private Point F = new Point(-20, -20, -20);
	private Point B = new Point(-20, -20,  20);
	private Point A = new Point( 20, -20,  20);
	private Point D = new Point( 20,  20,  20);
	private Point H = new Point( 20,  20, -20);
	private Point C = new Point(-20,  20,  20);
	private Point G = new Point(-20,  20, -20);	
	
	private Vector<Triangle> vector;
	
	public Model(){
		vector = new Vector<Triangle>();
		vector.add(new Triangle(E,D,A));
//		vector.add(new Triangle(H,D,E));
//		
//		vector.add(new Triangle(H,C,D));
//		vector.add(new Triangle(G,C,H));
//		
//		vector.add(new Triangle(C,G,B));
//		vector.add(new Triangle(B,G,F));
//		
//		vector.add(new Triangle(B,F,A));
//		vector.add(new Triangle(A,F,E));
//		
//		vector.add(new Triangle(A,D,B));
//		vector.add(new Triangle(B,D,C));
//		
//		vector.add(new Triangle(F,G,E));
//		vector.add(new Triangle(E,G,H));
	}
	
	
	public Vector<Triangle> getVector() {
		return vector;
	}

}
