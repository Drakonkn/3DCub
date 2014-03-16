import java.awt.Color;


public class Point {
	private double x;
	private double y;
	private double z;
	Color color = new Color(0xFFFFFF);
	private double xN = 0;
	private double yN = 0;
	private double zN = 0;
	
	private int x2D = 0;
	private int y2D = 0;
	
	private double xV = 0;
	private double yV = 0;
	private double zV = 0;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	Point getNormal(){
		return new Point(xN,yN,zN);
	}
	
	public void setView(Point view){
		this.xV = view.getX();
		this.yV = view.getY();
		this.zV = view.getZ();
	}
	
	public int getX2D() {
		return x2D;
	}

	public void setX2D(int x2d) {
		x2D = x2d;
	}

	public int getY2D() {
		return y2D;
	}

	public void setY2D(int y2d) {
		y2D = y2d;
	}

	public double getxV() {
		return xV;
	}

	public void setxV(double xV) {
		this.xV = xV;
	}

	public double getyV() {
		return yV;
	}

	public void setyV(double yV) {
		this.yV = yV;
	}

	public double getzV() {
		return zV;
	}

	public void setzV(double zV) {
		this.zV = zV;
	}
	

	public void setNorm(double xN,double yN, double zN ){
		this.xN = xN;
		this.yN = yN;
		this.zN = zN;
	}
	
	public static Point sub(Point p1, Point p2){
		return new Point(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
	}
	
	public static Point cross(Point a, Point b){
		return new Point(a.y * b.z - a.z*b.y, a.z*b.x - a.x*b.z, a.x*b.y - a.y*b.x);
	}
	
	public static Point normalize(Point p1) {
		double length = Math.sqrt(p1.x*p1.x+p1.y*p1.y+p1.z*p1.z);
		int mul = 1;
		return new Point(mul * (p1.x/length), mul*(p1.y/length),mul*(p1.z/length));
	}
	
	public Point(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static double calcDist(Point p1, Point p2){
		return Math.sqrt(Math.pow((p1.x - p2.x),2) + Math.pow((p1.y - p2.y),2) + Math.pow((p1.z - p2.z),2));
		
	}
	
	public Point(double[][] matrix){
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
	
	public void addNormal(Point norm){
		if (xN == 0 && yN == 0 && zN == 0){
			xN = norm.x;yN = norm.y;zN = norm.z;
		}
		else{
			xN += norm.x;yN += norm.y;zN += norm.z;
			double length = Math.sqrt((xN * xN)+(yN * yN)+(zN * zN));
			xN /= length;
			yN /= length;
			zN /= length;
		}
	}

	public double getxN() {
		return xN;
	}

	public double getyN() {
		return yN;
	}

	public double getzN() {
		return zN;
	}
	
}
