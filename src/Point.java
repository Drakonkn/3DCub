import java.awt.Color;


public class Point implements Normal {
	private double x;
	private double y;
	private double z;
	Color color = new Color(0xFFFFFF);
	
	Normal norm = null;
	
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
	
	Normal getNormal(){
		return norm;
	}
	
	public void setView(Point view){
		this.xV = view.getX();
		this.yV = view.getY();
		this.zV = view.getZ();
		this.norm = view.norm;
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

	public double getXV() {
		return xV;
	}

	public void setXV(double xV) {
		this.xV = xV;
	}

	public double getYV() {
		return yV;
	}

	public void setYV(double yV) {
		this.yV = yV;
	}

	public double getZV() {
		return zV;
	}

	public void setZV(double zV) {
		this.zV = zV;
	}
	

	public void setNorm(double xN,double yN, double zN ){
		if (norm == null){
			norm = new Point(xN,yN,zN);
		}
		else{
		norm.setX(xN);
		norm.setY(yN);
		norm.setZ(zN);
		}
		norm = normalize(norm);
	}
	
	public static Point sub(Point p1, Point p2){
		return new Point(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
	}
	
	public static Point cross(Point a, Point b){
		return new Point(a.y * b.z - a.z*b.y, a.z*b.x - a.x*b.z, a.x*b.y - a.y*b.x);
	}
	
	public static Normal normalize(Normal norm2) {
		double length = Math.sqrt(norm2.getX()*norm2.getX()+norm2.getY()*norm2.getY()+norm2.getZ()*norm2.getZ());
		int mul = 1;
		return new Point(mul * (norm2.getX()/length), mul*(norm2.getY()/length),mul*(norm2.getZ()/length));
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
	
	public void addNormal(Normal norm){
		if (this.norm == null && this.norm == null && this.norm == null){
			this.norm = new Point(norm.getX(), norm.getY(), norm.getZ());
		}
		else{
			this.norm.setX(this.norm.getX() + norm.getX());
			this.norm.setY(this.norm.getY() + norm.getY());
			this.norm.setZ(this.norm.getZ() + norm.getZ());
			this.norm = normalize(this.norm);
		}
	}

	public double getXN() {
		if(norm == null) return 0;
		return this.norm.getX();
	}

	public double getYN() {
		if(norm == null) return 0;
		return this.norm.getY();
	}

	public double getZN() {
		if(norm == null) return 0;
		return this.norm.getZ();
	}
	
}
