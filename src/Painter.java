

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
	    
	    drawTriangle(graphic, new Point2D(110, 10, new Color(0xFF0000)), new Point2D(100, 10, new Color(0x00FF00)), new Point2D(100,50,new Color(0x0000FF)));

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
	    	DrawDot(graphic, spotLights.get(i));
	    }
		
	}
	
	private void fong(Graphics graphic) {
		Vector<Triangle> vector =  model.getVector();
		for (int i = 0; i<vector.size();i++) {
			int r,g,b;
			
			r = (vector.get(i).A2.color.getRed() + vector.get(i).B2.color.getRed() + vector.get(i).C2.color.getRed())/3;
			g = (vector.get(i).A2.color.getGreen() + vector.get(i).B2.color.getGreen() + vector.get(i).C2.color.getGreen())/3;
			b = (vector.get(i).A2.color.getBlue() + vector.get(i).B2.color.getBlue() + vector.get(i).C2.color.getBlue())/3;
			
			graphic.setColor(new Color(r,g,b));
			drawTriangle(graphic,vector.get(i).A2,vector.get(i).B2,vector.get(i).C2);
		}
	}
	
	private void simpleFill(Graphics graphic){
		Vector<Triangle> vector =  model.getVector();
		for (int i = 0; i<vector.size();i++) {
			int[] x = {(int)(vector.get(i).A2.getX()*resize+move),
					(int)(vector.get(i).B2.getX()*resize+move),
					(int)(vector.get(i).C2.getX()*resize+move)};
			int[] y = {(int)(vector.get(i).A2.getY()*resize+move),
					(int)(vector.get(i).B2.getY()*resize+move),
					(int)(vector.get(i).C2.getY()*resize+move)};
			int r,g,b;
			
			r = (vector.get(i).A2.color.getRed() + vector.get(i).B2.color.getRed() + vector.get(i).C2.color.getRed())/3;
			g = (vector.get(i).A2.color.getGreen() + vector.get(i).B2.color.getGreen() + vector.get(i).C2.color.getGreen())/3;
			b = (vector.get(i).A2.color.getBlue() + vector.get(i).B2.color.getBlue() + vector.get(i).C2.color.getBlue())/3;
			
			graphic.setColor(new Color(r,g,b));
			graphic.fillPolygon(x, y, 3);
		}
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	private void DrawDot(Graphics g, SpotLight point){
		g.setColor(point.getCoordinates2D().getColor());
		g.fillRect((int)(point.getCoordinates2D().getX()+move),(int)(point.getCoordinates2D().getY()+move), 4, 4);
		g.drawString(String.valueOf((int)(point.getViewCoordinates().getX()+move))+" : "+String.valueOf((int)(point.getViewCoordinates().getY()+move))+" : "+String.valueOf((int)(point.getViewCoordinates().getZ()+move)), (int)(point.getCoordinates2D().getX()+move),(int)(point.getCoordinates2D().getY()+move));
	}
	
	void drawTriangle(Graphics g, Point2D A, Point2D B, Point2D C){
		
		g.setColor(A.getColor());
		g.fillRect(400, 40, 20, 20);
		g.setColor(B.getColor());
		g.fillRect(400, 60, 20, 20);
		g.setColor(C.getColor());
		g.fillRect(400, 80, 20, 20);
		
		Point2D top = A;
		Point2D mid = B;
		Point2D bot = C;
		
		Point2D temp;
		
		if (mid.getY() < top.getY()) {
			temp = top;
			top=mid;
			mid=temp;
			
		} 
		if (bot.getY() < top.getY()) {
			
			temp = top;
			top=bot;
			bot=temp;
		}
		if (mid.getY() > bot.getY()) {
			
			temp = mid;
			mid=bot;
			bot=temp;
		}
		
		int x1 = top.getX()+move;
		int y1 = top.getY()+move;
		int x2 = mid.getX()+move;
		int y2 = mid.getY()+move;
		int x3 = bot.getX()+move;
		int y3 = bot.getY()+move;

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

