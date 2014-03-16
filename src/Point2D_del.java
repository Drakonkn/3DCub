import java.awt.Color;


public class Point2D_del {

	private int x;
	private int y;
	Color color;
	private double xN = 0;
	private double yN = 0;
	private double zN = 0;
	
	public void setNormal(Point norm){
		xN = norm.getX();
		yN = norm.getY();
		zN = norm.getZ();
	}
	
	public void setNormal(double xN, double yN, double zN){
		this.xN = xN;
		this.yN = yN;
		this.zN = zN;
	}
	
	public Point getNormal(){
		return new Point(xN, yN, zN);
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

	public Point2D_del(){}
	
	public Point2D_del(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	public Point2D_del(int x, int y, Color color){
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
