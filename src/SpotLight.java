import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SpotLight implements ActionListener {
	int r;
	int g;
	int b;
	Point2D coordinates2D = new Point2D();
	private Point3D viewCoordinates = new Point3D(0,0,0);
	private Point3D coordinates = new Point3D(-3,-3,-3);

	double power = 1;
	double a = Math.atan(coordinates.getY()/coordinates.getX());
	double f = Math.atan(Math.sqrt(coordinates.getX()*coordinates.getX()+coordinates.getY()*coordinates.getY())/coordinates.getZ());
	double rr = Math.sqrt(Math.pow(coordinates.getX(),2)+Math.pow(coordinates.getY(),2)+Math.pow(coordinates.getZ(),2));
	
	
	public Point3D getViewCoordinates() {
		return viewCoordinates;
	}

	public void setViewCoordinates(Point3D viewCoordinates) {
		this.viewCoordinates = viewCoordinates;
	}

	public Point2D getCoordinates2D() {
		coordinates2D.color = new Color(r,g,b);
		return coordinates2D;
	}

	public void setCoordinates2D(Point2D coordinates2d) {
		coordinates2D = coordinates2d;
	}
	
	public SpotLight(Color color, Point3D coordinates) {
		a = Math.atan(coordinates.getY()/coordinates.getX());
		f = Math.atan(Math.sqrt(coordinates.getX()*coordinates.getX()+coordinates.getY()*coordinates.getY())/coordinates.getZ());
		rr = Math.sqrt(Math.pow(coordinates.getX(),2)+Math.pow(coordinates.getY(),2)+Math.pow(coordinates.getZ(),2));
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
		this.coordinates = coordinates;
		javax.swing.Timer timer = new javax.swing.Timer(100, this);
		timer.start();
	}
	
	public Point3D getCoordinates() {
		return coordinates;
	}
	public void  rotateRight(){
		a+=Math.toRadians(1);
		reCalcCoordinates();
	}
	
	public void  rotateLeft(){
		a-=Math.toRadians(1);
		reCalcCoordinates();
	}
	
	public void  rotateTop(){
		f-=Math.toRadians(1);
		reCalcCoordinates();
	}
	
	public void  rotateBot(){
		f+=Math.toRadians(1);
		reCalcCoordinates();
	}

	private void reCalcCoordinates(){
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
		double X = getCoordinates().getX() -x;
		double Y = getCoordinates().getY() -y;
		double Z = getCoordinates().getZ() -z;
		double mul = (x2*X) + (y2*Y) + (z2*Z);
		double length1 = Math.sqrt(X*X+Y*Y+Z*Z);
		double length2 = Math.sqrt(x2*x2+y2*y2+z2*z2);
		double cos = mul/(length1*length2);
		return cos<0?0:cos;
	}

	public void actionPerformed(ActionEvent e) {
//		rotateX();
//		rotateY();
	}
	
}
