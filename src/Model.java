import java.util.Vector;

public class Model {
	
	private Point3D A;
	private Point3D B;
	private Point3D C;
	private Point3D D;
	private Point3D E;
	private Point3D F;
	private Point3D G;
	private Point3D H;
	
	private Point3D I;
	private Point3D J;
	private Point3D K;
	private Point3D L;
	private Point3D M;
	private Point3D N;
	
	
	private Vector<triangle> vector;
	
	public Model(){

		vector = new Vector<triangle>();
		
		E = new Point3D( 20, -20, -20);
		F = new Point3D(-20, -20, -20);
		B = new Point3D(-20, -20,  20);
		A = new Point3D( 20, -20,  20);
		D = new Point3D( 20,  20,  20);
		H = new Point3D( 20,  20, -20);
		C = new Point3D(-20,  20,  20);
		G = new Point3D(-20,  20, -20);
		
		
		I = new Point3D( 0,  0,  2);
		J = new Point3D( 0,  0, -2);
		K = new Point3D( 0, -2,  0);
		L = new Point3D( 0,  2,  0);
		M = new Point3D( 2,  0,  0);
		N = new Point3D(-2,  0,  0);
		
		
		vector.add(new triangle(A,D,E));
		vector.add(new triangle(H,D,E));
		
		vector.add(new triangle(D,C,H));
		vector.add(new triangle(G,C,H));
		
		vector.add(new triangle(C,G,B));
		vector.add(new triangle(F,G,B));
		
		vector.add(new triangle(B,F,A));
		vector.add(new triangle(E,F,A));
		
		vector.add(new triangle(A,D,B));
		vector.add(new triangle(C,D,B));
		
		vector.add(new triangle(F,G,E));
		vector.add(new triangle(H,G,E));
		
		
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
	
	
	public Vector<triangle> getVector() {
		return vector;
	}


	public void setVector(Vector<triangle> vector) {
		this.vector = vector;
	}


	public Point3D getB() {
		return B;
	}

	public void setB(Point3D b) {
		B = b;
	}

	public Point3D getC() {
		return C;
	}

	public void setC(Point3D c) {
		C = c;
	}

	public void setA(Point3D a) {
		A = a;
	}




	public Point3D getA() {
		return A;
	}

	public Point3D getD() {
		return D;
	}

	public void setD(Point3D d) {
		D = d;
	}

	public Point3D getE() {
		return E;
	}

	public void setE(Point3D e) {
		E = e;
	}

	public Point3D getF() {
		return F;
	}

	public void setF(Point3D f) {
		F = f;
	}

	public Point3D getG() {
		return G;
	}

	public void setG(Point3D g) {
		G = g;
	}

	public Point3D getH() {
		return H;
	}

	public void setH(Point3D h) {
		H = h;
	}
	

}
