import java.awt.Color;


public class Point2D {

	private double x;
	private double y;
	Color color;
	
	public Point2D(){}
	
	public Point2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	
	public Point2D(double x, double y, Color color){
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
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
}
