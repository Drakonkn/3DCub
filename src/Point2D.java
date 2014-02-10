import java.awt.Color;


public class Point2D {

	private int x;
	private int y;
	Color color;
	
	public Point2D(){}
	
	public Point2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	public Point2D(int x, int y, Color color){
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
