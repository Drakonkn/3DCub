

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Painter extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Model model;
	final double resize = 1;
	final int move = -800;
	static final int simpleMode = 0;
	static final int fong = 1;
	int mode = simpleMode;
	Vector<SpotLight> spotLights;
public Painter() {
		JButton simple = new JButton("Simple");
		JButton fong   = new JButton("Fong");
		simple.setFocusable(false);
		fong.setFocusable(false);
		
		simple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Painter.simpleMode;
			}
		});
		
		fong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Painter.fong;
			}
		});
		
		add(fong);
		add(simple);
		model = new Model();
		setBackground(new Color(0x000000));
		setDoubleBuffered(true);
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setSpotLight(Vector<SpotLight> sp){
		this.spotLights = sp;
	}

	protected void paintComponent(Graphics graphic){
	    super.paintComponent(graphic);
	    
	    //drawTriangle(graphic, new Point(110, 10, new Color(0xFF0000)), new Point(100, 10, new Color(0x00FF00)), new Point(100,50,new Color(0x0000FF)));

		Vector<Triangle> vector =  model.getVector();
		Collections.sort(vector, new ZCompporator());
		switch (mode) {
		case simpleMode:
				simpleFill(graphic);
			break;
		case fong:
				fong(graphic);
			break;

		default:
			break;
		}
		
	    for (int i = 0;i<spotLights.size();i++){
	    	DrawDot(graphic, spotLights.get(i).getCoordinates());
	    }
		
	}
	
	private void guro(Graphics graphic){
		Vector<Triangle> vector =  model.getVector();
		for (int i = 0; i<vector.size();i++) {
			drawTriangleGuro(graphic,vector.get(i).A,vector.get(i).B,vector.get(i).C);
		}
	}
	
	private Point normInterp(Point norm1, Point norm2, double length, double position){
		double mul1 = (length-position)/length;
		double mul2 = (position)/length;

		if (mul1<0 || mul2<0)
			System.out.println("Mul< 0");
		
		double x = ((mul1)*norm1.getX()+mul2*norm2.getX());
		double y = ((mul1)*norm1.getY()+mul2*norm2.getY());
		double z = ((mul1)*norm1.getZ()+mul2*norm2.getZ());
		return new Point(x, y, z);
	}
	
	private void fong(Graphics graphic) {
		Vector<Triangle> vector =  model.getVector();
		for (int i = 0; i<vector.size();i++) {
			drawTriangle(graphic,vector.get(i));
			DrawDot(graphic, vector.get(i).A);
			//DrawDot(graphic, vector.get(i).B);
			//DrawDot(graphic, vector.get(i).C);
		}
	}
	
	private void simpleFill(Graphics graphic){
		Vector<Triangle> vector =  model.getVector();
		for (int i = 0; i<vector.size();i++) {
			int[] x = {(int)(vector.get(i).A.getX2D()*resize+move),
					(int)(vector.get(i).B.getX2D()*resize+move),
					(int)(vector.get(i).C.getX2D()*resize+move)};
			int[] y = {(int)(vector.get(i).A.getY2D()*resize+move),
					(int)(vector.get(i).B.getY2D()*resize+move),
					(int)(vector.get(i).C.getY2D()*resize+move)};
			int r,g,b;
			
			r = (vector.get(i).AColor.getRed() + vector.get(i).BColor.getRed() + vector.get(i).CColor.getRed())/3;
			g = (vector.get(i).AColor.getGreen() + vector.get(i).BColor.getGreen() + vector.get(i).CColor.getGreen())/3;
			b = (vector.get(i).AColor.getBlue() + vector.get(i).BColor.getBlue() + vector.get(i).CColor.getBlue())/3;
			
			graphic.setColor(new Color(r,g,b));
			graphic.fillPolygon(x, y, 3);
		}
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	private void DrawDot(Graphics g, Point point){
		g.setColor(point.getColor());
		g.fillRect((int)(point.getX2D()+move),(int)(point.getY2D()+move), 4, 4);
		g.drawString(String.valueOf((int)(point.getxV()+move))+" : "+String.valueOf((int)(point.getyV()+move))+" : "+String.valueOf((int)(point.getzV()+move)), (int)(point.getX2D()+move),(int)(point.getY2D()+move));
	}
	
	void drawTriangle(Graphics g, Triangle triangle){
		
		Point A = triangle.A;
		Point B = triangle.B;
		Point C = triangle.C;
		A.setColor(triangle.AColor);
		B.setColor(triangle.BColor);
		C.setColor(triangle.CColor);
		
		g.setColor(A.getColor());
		g.fillRect(400, 40, 20, 20);
		g.setColor(B.getColor());
		g.fillRect(400, 60, 20, 20);
		g.setColor(C.getColor());
		g.fillRect(400, 80, 20, 20);
		
		Point top = A;
		Point mid = B;
		Point bot = C;
		
		Point temp;
		
		if (mid.getY2D() < top.getY2D()) {
			temp = top;
			top=mid;
			mid=temp;
			
		} 
		if (bot.getY2D() < top.getY2D()) {
			
			temp = top;
			top=bot;
			bot=temp;
		}
		if (mid.getY2D() > bot.getY2D()) {
			
			temp = mid;
			mid=bot;
			bot=temp;
		}
		
		int x1 = top.getX2D()+move;
		int y1 = top.getY2D()+move;
		int x2 = mid.getX2D()+move;
		int y2 = mid.getY2D()+move;
		int x3 = bot.getX2D()+move;
		int y3 = bot.getY2D()+move;

		double dx13 = 0, dx12 = 0, dx23 = 0;
		if (y3 != y1) {
			dx13 = x3 - x1;
			dx13 /= (y3 - y1)+1;
		}
		else
		{
			dx13 = 0;
		}

		if (y2 != y1) {
			dx12 = x2 - x1;
			dx12 /= (y2 - y1)+1;
		}
		else
		{
			dx12 = 0;
		}

		if (y3 != y2) {
			dx23 = x3 - x2;
			dx23 /= (y3 - y2)+1;
		}
		else
		{
			dx23 = 0;
		}

		double wx1 = x1;
		double wx2 = wx1;
		
		double _dx13 = dx13;

		if (dx13 > dx12)
		{
			double t;
			t = dx13;
			dx13 = dx12;
			dx12 = t;
		}
		
		Color left;
		Color right;
		Color act;
		Color D = ColorIntr(top.getColor(),bot.getColor(), y3-y1, y2 -y1);
		int i = y1;
		while ( i <= y2){
			if(x2>(x1+x3)/2){
				left = ColorIntr(top.getColor(), D, y2-y1, i-y1);
				right = ColorIntr(top.getColor(), mid.getColor(), y2-y1, i-y1);
			}
			else{
				right = ColorIntr(top.getColor(), D, y2-y1, i-y1);
				left = ColorIntr(top.getColor(), mid.getColor(), y2-y1, i-y1);
			}
			double j = wx1;
			while ( j <=wx2){
				act = ColorIntr(left, right, wx2-wx1, j-wx1);
				g.setColor(act);
				g.drawRect((int)Math.ceil(j), i, 1, 1);
				j++;
			}
			wx1 += dx13;
			wx2 += dx12;
			i++;
		}
		if (y1 == y2){
			wx1 = x1;
			wx2 = x2;
		}
		if (_dx13 < dx23)
		{
			double t;
			t = _dx13;
			_dx13 = dx23;
			dx23 = t;
		}
		
		i = y2;
		while ( i <= y3){
			if(x2>(x1+x3)/2){
				left = ColorIntr(D, bot.getColor(), y3-y2, i-y2);
				right = ColorIntr(mid.getColor(), bot.getColor(), y3-y2, i-y2);
			}
			else{
				right = ColorIntr(D, bot.getColor(), y3-y2, i-y2);
				left = ColorIntr(mid.getColor(), bot.getColor(), y3-y2, i-y2);
			}
			double j = wx1;
			while ( j <= wx2){
				act = ColorIntr(left, right, wx2-wx1, j-wx1);
				g.setColor(act);
				g.drawRect((int)Math.ceil(j), i, 1, 1);
				j++;
			}
			wx1 += _dx13;
			wx2 += dx23;
			i++;
		}
	}
	
	void drawTriangleGuro(Graphics g, Point A, Point B, Point C){
		
//		g.setColor(A.getColor());
//		g.fillRect(400, 40, 20, 20);
//		g.setColor(B.getColor());
//		g.fillRect(400, 60, 20, 20);
//		g.setColor(C.getColor());
//		g.fillRect(400, 80, 20, 20);
//		
//		Point top = A;
//		Point mid = B;
//		Point bot = C;
//		
//		Point temp;
//		
//		if (mid.getY2D() < top.getY2D()) {
//			temp = top;
//			top=mid;
//			mid=temp;
//			
//		} 
//		if (bot.getY2D() < top.getY2D()) {
//			
//			temp = top;
//			top=bot;
//			bot=temp;
//		}
//		if (mid.getY2D() > bot.getY2D()) {
//			
//			temp = mid;
//			mid=bot;
//			bot=temp;
//		}
//		
//		int x1 = top.getX2D()+move;
//		int y1 = top.getY2D()+move;
//		int x2 = mid.getX2D()+move;
//		int y2 = mid.getY2D()+move;
//		int x3 = bot.getX2D()+move;
//		int y3 = bot.getY2D()+move;
//
//		double dx13 = 0, dx12 = 0, dx23 = 0;
//		if (y3 != y1) {
//			dx13 = x3 - x1;
//			dx13 /= (y3 - y1)+1;
//		}
//		else
//		{
//			dx13 = 0;
//		}
//
//		if (y2 != y1) {
//			dx12 = x2 - x1;
//			dx12 /= (y2 - y1)+1;
//		}
//		else
//		{
//			dx12 = 0;
//		}
//
//		if (y3 != y2) {
//			dx23 = x3 - x2;
//			dx23 /= (y3 - y2)+1;
//		}
//		else
//		{
//			dx23 = 0;
//		}
//
//		double wx1 = x1;
//		double wx2 = wx1;
//		
//		double _dx13 = dx13;
//
//		if (dx13 > dx12)
//		{
//			double t;
//			t = dx13;
//			dx13 = dx12;
//			dx12 = t;
//		}
//		
//		Point left;
//		Point right;
//		Point act;
//		
//		//Color D = ColorIntr(top.getColor(),bot.getColor(), y3-y1, y2 -y1);
//		Point D = normInterp(top.getNormal(), bot.getNormal(), y3-y1, y2-y1);
//		int i = y1;
//		while ( i <= y2){
//			if(x2>(x1+x3)/2){
//				left = normInterp(top.getNormal(), D, y2-y1, i-y1);
//				right = normInterp(top.getNormal(), mid.getNormal(), y2-y1, i-y1);
//				
//				//left = ColorIntr(top.getColor(), D, y2-y1, i-y1);
//				//right = ColorIntr(top.getColor(), mid.getColor(), y2-y1, i-y1);
//			}
//			else{
//				right = normInterp(top.getNormal(), D, y2-y1, i-y1);
//				left = normInterp(top.getNormal(), mid.getNormal(), y2-y1, i-y1);
//				//right = ColorIntr(top.getColor(), D, y2-y1, i-y1);
//				//left = ColorIntr(top.getColor(), mid.getColor(), y2-y1, i-y1);
//			}
//			double j = wx1;
//			while ( j <=wx2){
//				act = normInterp(left, right, wx2-wx1, j-wx1);
//				//act = ColorIntr(left, right, wx2-wx1, j-wx1);
//				g.setColor(calcColor(A, norm));
//				g.drawRect((int)Math.ceil(j), i, 1, 1);
//				j++;
//			}
//			wx1 += dx13;
//			wx2 += dx12;
//			i++;
//		}
//		if (y1 == y2){
//			wx1 = x1;
//			wx2 = x2;
//		}
//		if (_dx13 < dx23)
//		{
//			double t;
//			t = _dx13;
//			_dx13 = dx23;
//			dx23 = t;
//		}
//		
//		i = y2;
//		while ( i <= y3){
//			if(x2>(x1+x3)/2){
//				
//				left = ColorIntr(D, bot.getColor(), y3-y2, i-y2);
//				right = ColorIntr(mid.getColor(), bot.getColor(), y3-y2, i-y2);
//			}
//			else{
//				right = ColorIntr(D, bot.getColor(), y3-y2, i-y2);
//				left = ColorIntr(mid.getColor(), bot.getColor(), y3-y2, i-y2);
//			}
//			double j = wx1;
//			while ( j <= wx2){
//				act = ColorIntr(left, right, wx2-wx1, j-wx1);
//				g.setColor(act);
//				g.drawRect((int)Math.ceil(j), i, 1, 1);
//				j++;
//			}
//			wx1 += _dx13;
//			wx2 += dx23;
//			i++;
//		}
	}
	
	  private Color calcColor(Point A,Point norm){
		  double xN = norm.getX();
		  double yN = norm.getY();
		  double zN = norm.getZ();
		  	double xA,yA,zA;
			xA = A.getX();
			yA = A.getY();
			zA = A.getZ();
			int r = 0,g = 0,b = 0;

			SpotLight s;
			for (int i = 0; i<spotLights.size();i++){
				s = spotLights.get(i);	
				double cosA = s.getCos(xN,yN,zN,xA,yA,zA);
				double cosB = 1;
				double dist = Point.calcDist(s.getCoordinates(), A);
				dist /= 30;
				double mulA = (cosA*s.power*cosB)/dist;
				r += (int) (s.getR()* mulA);
				g += (int) (s.getG()* mulA);
				b += (int) (s.getB()* mulA);
			}
			int lightCount = spotLights.size();
			
			if (r>255)r=255;
			if (g>255)g=255;
			if (b>255)b=255;
			
			if (r<30)r=30;
			if (g<30)g=30;
			if (b<30)b=30;
			return new Color((int)(r/lightCount),(int)(g/lightCount),(int)(b/lightCount));
	  }

	Color ColorIntr (Color col1, Color col2, double length, double position){
		double mul1 = (length-position)/length;
		double mul2 = (position)/length;

		if (mul1<0 || mul2<0)
			System.out.println("Mul< 0");
		int r = (int) ((mul1)*col1.getRed()+mul2*col2.getRed());
		int g = (int) ((mul1)*col1.getGreen()+mul2*col2.getGreen());
		int b = (int) ((mul1)*col1.getBlue()+mul2*col2.getBlue());
		return new Color(r, g, b);
	}
	
	
	Color RGB2HSB(Color rgb){
		float color[] = new float[5];
		Color.RGBtoHSB(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), color);
		return new Color(color[0],color[1],color[2]);
	}

}

