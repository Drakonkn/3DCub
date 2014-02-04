import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SpotLight implements ActionListener {
	int r = 255;
	int g = 8;
	int b = 8;
	Point3D coordinates = new Point3D(-10,-10,-10);
	double power = 1;
	double a = Math.atan(coordinates.getY()/coordinates.getX());
	double f = Math.atan(Math.sqrt(coordinates.getX()*coordinates.getX()+coordinates.getY()*coordinates.getY())/coordinates.getZ());
	double rr = Math.sqrt(Math.pow(coordinates.getX(),2)+Math.pow(coordinates.getY(),2)+Math.pow(coordinates.getZ(),2));
	
	public SpotLight() {
		javax.swing.Timer timer = new javax.swing.Timer(100, this);
		timer.start();
	}
	
	private void  rotate(){
		f+=Math.toRadians(1);
		coordinates.setX(rr*Math.sin(f)*Math.cos(a));
		coordinates.setY(rr*Math.sin(f)*Math.sin(a));
		coordinates.setZ(rr*Math.cos(f));
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public double getCos(double x2, double y2, double z2,double x,double y, double z) {
		double X = coordinates.getX() -x;
		double Y = coordinates.getY() -y;
		double Z = coordinates.getZ() -z;
		double mul = (x2*X) + (y2*Y) + (z2*Z);
		double length1 = Math.sqrt(X*X+Y*Y+Z*Z);
		double length2 = Math.sqrt(x2*x2+y2*y2+z2*z2);
		return Math.abs(mul/(length1*length2));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		rotate();
	}
	
}
