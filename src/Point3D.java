import java.awt.Color;


public class Point3D {
	private double x;
	private double y;
	private double z;
	Color color = new Color(0);
	
	
	public static Point3D sub(Point3D p1, Point3D p2){
		return new Point3D(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
	}
	
	public static Point3D cross(Point3D a, Point3D b){
		return new Point3D(a.y * b.z - a.z*b.y, a.z*b.x - a.x*b.z, a.x*b.y - a.y*b.x);
	}
	
	public static Point3D normalize(Point3D p1) {
		double length = Math.sqrt(p1.x*p1.x+p1.y*p1.y+p1.z*p1.z);
		int mul = 1;
		return new Point3D(mul * (p1.x/length), mul*(p1.y/length),mul*(p1.z/length));
	}
	
	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static double calcDist(Point3D p1, Point3D p2){
		return Math.sqrt(Math.pow((p1.x - p2.x),2) + Math.pow((p1.y - p2.y),2) + Math.pow((p1.z - p2.z),2));
		
	}
	
	public Point3D(double[][] matrix){
		this.x = matrix[0][0];
		this.y = matrix[0][1];
		this.z = matrix[0][2];;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public double[][] getMatrix(){
		double res[][] = new double[1][4];
		res[0][0] = x;
		res[0][1] = y;
		res[0][2] = z;
		res[0][3] = 1;
		return res;
		
	}
}
