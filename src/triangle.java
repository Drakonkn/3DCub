import java.awt.Color;


    class Triangle
	{
    	Color color;
    	public Point A; 
    	public Point B;  
    	public Point C; 
    	public Point norm;
    	double z;
    	Color AColor;
    	Color BColor;
    	Color CColor;
    	public Triangle(Point A,Point B,Point C){
    		this.A = A;
    		this.B = B;
    		this.C = C;
    		Point normal = normalCalc();
    		this.norm = normal;
    		A.addNormal(normal);
    		B.addNormal(normal);
    		C.addNormal(normal);
    		color = new Color(0);
    	}
    	
    	
    	private Point normalCalc(){
    		Point p = Point.sub(B, A);
    		Point q = Point.sub(C, A);
    		Point n = Point.normalize(Point.cross(p, q));
    		return n;
    	}
	};
