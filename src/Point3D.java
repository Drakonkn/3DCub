import java.awt.Color;


public class Point3D {
	private double x;
	private double y;
	private double z;
	Color color = new Color(0);
	
	
	public static Point3D sub(Point3D p1, Point3D p2){
		return new Point3D(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
	}
	
	public static Point3D cross(Point3D p1, Point3D p2){
		return new Point3D(p1.x * p2.x, p1.y * p2.y, p1.z * p2.z);
	}
	
	public static Point3D normalize(Point3D p1) {
		double length = Math.sqrt(p1.x*p1.x+p1.y*p1.y+p1.z*p1.z); ;
		return new Point3D(p1.x/length, p1.y/length,p1.z/length);
	}
	
	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
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
