import java.awt.Color;


    class triangle
	{
    	public triangle(Point3D A,Point3D B,Point3D C){
    		this.A = A;
    		this.B = B;
    		this.C = C;
    		A2 = new Point2D();
    		B2 = new Point2D();
    		C2 = new Point2D();
    		color = new Color(0);
    	}
    	Color color;
    	public Point3D A; 
    	public Point3D B;  
    	public Point3D C; 
    	public Point2D A2;
    	public Point2D B2;
    	public Point2D C2;
    	double z;
	};
