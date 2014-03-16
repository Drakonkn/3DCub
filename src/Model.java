import java.util.Vector;

public class Model {
	
	private Point A;
	private Point B;
	private Point C;
	private Point D;
	private Point E;
	private Point F;
	private Point G;
	private Point H;
	
//	private Point3D I;
//	private Point3D J;
//	private Point3D K;
//	private Point3D L;
//	private Point3D M;
//	private Point3D N;
	
	
	private Vector<Triangle> vector;
	
	public Model(){

		vector = new Vector<Triangle>();
		
		E = new Point( 20, -20, -20);
		F = new Point(-20, -20, -20);
		B = new Point(-20, -20,  20);
		A = new Point( 20, -20,  20);
		D = new Point( 20,  20,  20);
		H = new Point( 20,  20, -20);
		C = new Point(-20,  20,  20);
		G = new Point(-20,  20, -20);
		
		
//		I = new Point3D( 0,  0,  2);
//		J = new Point3D( 0,  0, -2);
//		K = new Point3D( 0, -2,  0);
//		L = new Point3D( 0,  2,  0);
//		M = new Point3D( 2,  0,  0);
//		N = new Point3D(-2,  0,  0);
		
		
		vector.add(new Triangle(E,D,A));
		vector.add(new Triangle(H,D,E));
		
		vector.add(new Triangle(H,C,D));
		vector.add(new Triangle(G,C,H));
		
		vector.add(new Triangle(C,G,B));
		vector.add(new Triangle(B,G,F));
		
		vector.add(new Triangle(B,F,A));
		vector.add(new Triangle(A,F,E));
		
		vector.add(new Triangle(A,D,B));
		vector.add(new Triangle(B,D,C));
		
		vector.add(new Triangle(F,G,E));
		vector.add(new Triangle(E,G,H));
		
		
//		vector.add(new triangle(A,B,I));
//		vector.add(new triangle(C,B,I));
//		vector.add(new triangle(C,D,I));
//		vector.add(new triangle(A,D,I));
//		
//		
//		vector.add(new triangle(A,D,M));
//		vector.add(new triangle(D,H,M));
//		vector.add(new triangle(H,E,M));
//		vector.add(new triangle(E,A,M));
//		
//		
//		vector.add(new triangle(A,B,K));
//		vector.add(new triangle(B,F,K));
//		vector.add(new triangle(F,E,K));
//		vector.add(new triangle(E,A,K));
//		
//		vector.add(new triangle(B,C,N));
//		vector.add(new triangle(C,G,N));
//		vector.add(new triangle(G,F,N));
//		vector.add(new triangle(F,B,N));
//		
//
//		vector.add(new triangle(E,F,J));
//		vector.add(new triangle(F,G,J));
//		vector.add(new triangle(G,H,J));
//		vector.add(new triangle(H,E,J));
//		
//
//		vector.add(new triangle(D,C,L));
//		vector.add(new triangle(C,G,L));
//		vector.add(new triangle(G,H,L));
//		vector.add(new triangle(H,D,L));
	}
	
	
	public Vector<Triangle> getVector() {
		return vector;
	}


	public void setVector(Vector<Triangle> vector) {
		this.vector = vector;
	}


	public Point getB() {
		return B;
	}

	public void setB(Point b) {
		B = b;
	}

	public Point getC() {
		return C;
	}

	public void setC(Point c) {
		C = c;
	}

	public void setA(Point a) {
		A = a;
	}




	public Point getA() {
		return A;
	}

	public Point getD() {
		return D;
	}

	public void setD(Point d) {
		D = d;
	}

	public Point getE() {
		return E;
	}

	public void setE(Point e) {
		E = e;
	}

	public Point getF() {
		return F;
	}

	public void setF(Point f) {
		F = f;
	}

	public Point getG() {
		return G;
	}

	public void setG(Point g) {
		G = g;
	}

	public Point getH() {
		return H;
	}

	public void setH(Point h) {
		H = h;
	}
	

}
