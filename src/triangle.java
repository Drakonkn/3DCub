import java.awt.Color;


    class Triangle
	{
    	Color color;
    	public Point A; 
    	public Point B;  
    	public Point C; 
    	double z;
    	Color AColor;
    	Color BColor;
    	Color CColor;
    	
    	public Triangle(Point A,Point B,Point C){
    		this.A = A;
    		this.B = B;
    		this.C = C;
    		Normal normal = normalCalc();
    		Normal norm = (Point) normal;
    		A.addNormal(norm);
    		B.addNormal(norm);
    		C.addNormal(norm);
    	}
    	
    	public Normal normalCalc(){
    		Point p = Point.sub(B, A);
    		Point q = Point.sub(C, A);
    		Normal n = Point.normalize(Point.cross(p, q));
    		return n;
    	}
	};
