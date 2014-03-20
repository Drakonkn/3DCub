

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
	static final int guro = 2;
	int mode = fong;
	Vector<SpotLight> spotLights;
public Painter() {
	setDoubleBuffered(true);
		JButton simpleButton = new JButton("Simple");
		JButton fongButton   = new JButton("Fong");
		JButton guroButton   = new JButton("Guro");
		simpleButton.setFocusable(false);
		fongButton.setFocusable(false);
		guroButton.setFocusable(false);
		
		simpleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Painter.simpleMode;
			}
		});
		
		fongButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Painter.fong;
			}
		});
		
		guroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Painter.guro;
			}
		});
		
		add(fongButton);
		add(simpleButton);
		add(guroButton);
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
		Vector<Triangle> vector =  model.getVector();
		Collections.sort(vector, new ZCompporator());
		switch (mode) {
		case simpleMode:
				simpleFill(graphic);
			break;
		case fong:
				fong(graphic);
			break;
		case guro:
			guro(graphic);
		break;
		default:
			break;
		}
		
	    for (int i = 0;i<spotLights.size();i++){
	    	//DrawDot(graphic, spotLights.get(i).getCoordinates());
	    }
		
	}
	
	private void fong(Graphics graphic){
		Vector<Triangle> vector =  model.getVector();
		for (int i = 0; i<vector.size();i++) {
			drawTriangleFong2(graphic,vector.get(i).A,vector.get(i).B,vector.get(i).C);
			DrawDot(graphic, vector.get(i).A);
			DrawDot(graphic, vector.get(i).B);
			DrawDot(graphic, vector.get(i).C);
		}
	}
	
	private Normal normInterp(Normal norm1, Normal norm2, double length, double position){
		double mul1 = Math.abs(length-position)/Math.abs(length);
		double mul2 = Math.abs(position)/Math.abs(length);

		if (mul1<0 || mul2<0)
			System.out.println("Mul< 0");
		
		double x = ((mul1)*norm1.getX()+mul2*norm2.getX());
		double y = ((mul1)*norm1.getY()+mul2*norm2.getY());
		double z = ((mul1)*norm1.getZ()+mul2*norm2.getZ());
		return Point.normalize(new Point(x, y, z));
	}
	
	private void guro(Graphics graphic) {
		Vector<Triangle> vector =  model.getVector();
		for (int i = 0; i<vector.size();i++) {
			drawTriangleGuro(graphic,vector.get(i));
			DrawDot(graphic, vector.get(i).A);
			DrawDot(graphic, vector.get(i).B);
			DrawDot(graphic, vector.get(i).C);
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
			DrawDot(graphic, vector.get(i).A);
			DrawDot(graphic, vector.get(i).B);
			DrawDot(graphic, vector.get(i).C);
			
		}
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	private void DrawDot(Graphics g, Point point){
		g.setColor(point.getColor());
		point = MainWindow.pointTo2D(point);
		g.fillRect((int)(point.getX2D()+move),(int)(point.getY2D()+move), 4, 4);
		g.drawString(String.valueOf((int)(point.getXV()))+" : "+String.valueOf((int)(point.getYV()))+" : "+String.valueOf((int)(point.getZV()))+" : "+(point.getX2D()+move)+" : "+(point.getY2D()+move), (int)(point.getX2D()+move),(int)(point.getY2D()+move));
	}
	
	void drawTriangleGuro(Graphics g, Triangle triangle){
		
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
	
	void drawTriangleFong(Graphics g, Point A, Point B, Point C){
		
		Point top = A;
		Point mid = B;
		Point bot = C;
		
		Point temp;
		
		if (mid.getYV() < top.getYV()) {
			temp = top;
			top=mid;
			mid=temp;
			
		} 
		if (bot.getYV() < top.getYV()) {
			
			temp = top;
			top=bot;
			bot=temp;
		}
		if (mid.getYV() > bot.getYV()) {
			
			temp = mid;
			mid=bot;
			bot=temp;
		}
		
		double x1 = top.getXV();
		double y1 = top.getYV();
		double z1 = top.getZV();
		double x2 = mid.getXV();
		double y2 = mid.getYV();
		double z2 = mid.getZV();
		double x3 = bot.getXV();
		double y3 = bot.getYV();
		double z3 = bot.getZV();
		

		double dx13 = 0, dx12 = 0, dx23 = 0;
		double dz13 = 0, dz12 = 0, dz23 = 0;
		if (y3 != y1) {
			dx13 = x3 - x1;
			dx13 /= (y3 - y1)+1;
			dz13 = z3 - z1;
			dz13 /= (y3 - y1)+1;
		}
		else
		{
			dx13 = 0;
			dz13 = 0;
		}

		if (y2 != y1) {
			dx12 = x2 - x1;
			dx12 /= (y2 - y1)+1;
			dz12 = z2 - z1;
			dz12 /= (y2 - y1)+1;
		}
		else
		{
			dx12 = 0;
			dz12 = 0;
		}

		if (y3 != y2) {
			dx23 = x3 - x2;
			dx23 /= (y3 - y2)+1;
			dz23 = z3 - z2;
			dz23 /= (y3 - y2)+1;
		}
		else
		{
			dx23 = 0;
			dz23 = 0;
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
		
		Normal left;
		Normal right;
		Normal act;
		double Zright, Zleft;
		Normal D = normInterp(top.getNormal(),bot.getNormal(), y3-y1, y2 -y1);
		double Dz =  top.getZV() * Math.abs((y3-y1)-(y2 -y1))/Math.abs(y3-y1) + bot.getZV() * Math.abs(y2 -y1)/Math.abs(y3-y1);
		double i = y1;
		while ( i <= y2){
			if(x2>(x1+x3)/2){
				left = normInterp(top.getNormal(), D, y2-y1, i-y1);
				right = normInterp(top.getNormal(), mid.getNormal(), y2-y1, i-y1);
				Zleft = top.getZV() * Math.abs((y2-y1)-(i-y1))/Math.abs(y2-y1) + Dz * Math.abs(i-y1)/Math.abs(y2-y1);
				Zright = top.getZV() * Math.abs((y2-y1)-(i-y1))/Math.abs(y2-y1) + mid.getZV() * Math.abs(i-y1)/Math.abs(y2-y1);
			}
			else{
				right = normInterp(top.getNormal(), D, y2-y1, i-y1);
				left = normInterp(top.getNormal(), mid.getNormal(), y2-y1, i-y1);
				Zright = top.getZV() * Math.abs((y2-y1)-(i-y1))/Math.abs(y2-y1) + Dz * Math.abs(i-y1)/Math.abs(y2-y1);
				Zleft = top.getZV() * Math.abs((y2-y1)-(i-y1))/Math.abs(y2-y1) + mid.getZV() * Math.abs(i-y1)/Math.abs(y2-y1);
			}
			double j = wx1;
			while ( j <=wx2){
				act = normInterp(left, right, wx2-wx1, j-wx1);
				double actZ = Zleft * Math.abs((wx2-wx1)-(j-wx1))/Math.abs(wx2-wx1) + Zright * Math.abs(j-wx1)/Math.abs(wx2-wx1);
				Point pp = new Point(j,i,actZ);
				pp.setXV(j);
				pp.setYV(i);
				pp.setZV(actZ);
				g.setColor(calcColor(pp, act));
				MainWindow.pointTo2D(pp);
				g.drawRect((int)Math.ceil(pp.getX2D()+move), (int)Math.ceil(pp.getY2D()+move), 1, 1);
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
				left = normInterp(D, bot.getNormal(), y3-y2, i-y2);
				right = normInterp(mid.getNormal(), bot.getNormal(), y3-y2, i-y2);
				Zleft = top.getZV() * Math.abs((y3-y2)-(i-y2))/Math.abs(y3-y2) + Dz * Math.abs(i-y2)/Math.abs(y3-y2);
				Zright = top.getZV() * Math.abs((y3-y2)-(i-y2))/Math.abs(y3-y2) + mid.getZV() * Math.abs(i-y2)/Math.abs(y3-y2);
			}
			else{
				right = normInterp(D, bot.getNormal(), y3-y2, i-y2);
				left = normInterp(mid.getNormal(), bot.getNormal(), y3-y2, i-y2);
				
				Zright = top.getZV() * Math.abs((y3-y2)-(i-y2))/Math.abs(y3-y2) + Dz * Math.abs(i-y2)/Math.abs(y3-y2);
				Zleft = top.getZV() * Math.abs((y3-y2)-(i-y2))/Math.abs(y3-y2) + mid.getZV() * Math.abs(i-y2)/Math.abs(y3-y2);
			}
			double j = wx1;
			while ( j <= wx2){
				act = normInterp(left, right, wx2-wx1, j-wx1);
				double actZ = Zleft * Math.abs((wx2-wx1)-(j-wx1))/Math.abs(wx2-wx1) + Zright * Math.abs(j-wx1)/Math.abs(wx2-wx1);
				Point pp = new Point(j,i,actZ);
				pp.setXV(j);
				pp.setYV(i);
				pp.setZV(actZ);
				g.setColor(calcColor(pp, act));
				MainWindow.pointTo2D(pp);
				g.drawRect((int)Math.ceil(pp.getX2D()+move), (int)Math.ceil(pp.getY2D()+move), 1, 1);
				j++;
			}
			wx1 += _dx13;
			wx2 += dx23;
			i++;
		}
	}
	
	void drawTriangleFong2(Graphics g, Point A, Point B, Point C){
		
		Point top = A;
		Point mid = B;
		Point bot = C;
		
		Point temp;
		
		if (mid.getYV() < top.getYV()) {
			temp = top;
			top=mid;
			mid=temp;
			
		} 
		if (bot.getYV() < top.getYV()) {
			
			temp = top;
			top=bot;
			bot=temp;
		}
		if (mid.getYV() > bot.getYV()) {
			
			temp = mid;
			mid=bot;
			bot=temp;
		}
		
		double xTop = top.getXV();
		double yTop = top.getYV();
		double xMid = mid.getXV();
		double yMid = mid.getYV();
		double xBot = bot.getXV();
		double yBot = bot.getYV();
		double zTop = top.getZV();
		double zMid = mid.getZV();
		double zBot = bot.getZV();
		
		
		
		
		//double dxNTopBot, dyNTopBot, dzNTopBot, dxNTopMid, dyNTopMid, dzNTopMid,dxNMidBot, dyNMidBot, dzNMidBot;
		double dzTopBot, dzTopMid, dzMidBot, dxTopBot, dxTopMid, dxMidBot;
		if (yBot != yTop) {
			dxTopBot = xBot - xTop;
			dxTopBot /= (yBot - yTop)+1;
			dzTopBot = zBot - zTop;
			dzTopBot /= (yBot - yTop)+1;
		}
		else
		{
			dxTopBot = 0;
			dzTopBot = 0;
		}

		if (yMid != yTop) {
			dxTopMid = xMid - xTop;
			dxTopMid /= (yMid - yTop)+1;
			dzTopMid = zMid - zTop;
			dzTopMid /= (yMid - yTop)+1;
		}
		else
		{
			dxTopMid = 0;
			dzTopMid = 0;
		}

		if (yBot != yMid) {
			dxMidBot = xBot - xMid;
			dxMidBot /= (yBot - yMid)+1;
			dzMidBot = zBot - zMid;
			dzMidBot /= (yBot - yMid)+1;
		}
		else
		{
			dxMidBot = 0;
			dzMidBot = 0;
		}
		Normal normalInD = normInterp(top.getNormal(), bot.getNormal(), yBot-yTop, yMid-yTop);
		double curentX = 0;
		double curentY = yTop;
		double curentZ = 0;
		Normal rightNormal;
		Normal leftNormal;
		
		double lineStartX = xTop;
		double lineEndX = lineStartX;
		double lineStartZ = zTop;
		double lineEndZ = lineStartZ;
		Normal curentNormal;
		
		//double _dxTopBot = dxTopBot;

//		if (dxTopBot > dxTopMid)
//		{
//			double t;
//			t = dxTopBot;
//			dxTopBot = dxTopMid;
//			dxTopMid = t;
//		}
		double dx1,dx2,dz1,dz2;
		
		if(xMid>(xTop+xBot)/2){
			dx1 = dxTopBot;
			dx2 = dxTopMid;
			dz1 = dzTopBot;
			dz2 = dzTopMid;
		}
		else{
			dx1 = dxTopMid;
			dx2 = dxTopBot;
			dz1 = dzTopMid;
			dz2 = dzTopMid;
		}
		
		
		while (curentY <= yBot) {
			if(curentY >= yMid){
				if(xMid>(xTop+xBot)/2){
					dx1 = dxTopBot;
					dx2 = dxMidBot;
					dz1 = dzTopBot;
					dz2 = dzMidBot;
				}
				else{
					dx1 = dxMidBot;
					dx2 = dxTopBot;
					dz1 = dzMidBot;
					dz2 = dzTopMid;
				}
			}
			if(xMid>(xTop+xBot)/2){
				leftNormal = normInterp(top.getNormal(), normalInD, yMid-yTop, curentY-yTop);
				rightNormal = normInterp(top.getNormal(), mid.getNormal(), yMid-yTop, curentY-yTop);
			}
			else{
				rightNormal = normInterp(top.getNormal(), normalInD, yMid-yTop, curentY-yTop);
				leftNormal = normInterp(top.getNormal(), mid.getNormal(), yMid-yTop, curentY-yTop);
			}
			curentX = lineStartX;
			curentZ = lineStartZ;
			double dz = (lineStartZ - lineEndZ)/(lineStartX - lineEndX);
			while ( curentX < lineEndX){
				curentNormal = normInterp(leftNormal, rightNormal, lineEndX-lineStartX, curentX-lineStartX);
				Point curentPoint = new Point(curentX, curentY, curentZ);
				curentPoint.setXV(curentX);
				curentPoint.setYV(curentY);
				curentPoint.setZV(curentZ);
				g.setColor(calcColor(curentPoint, curentNormal));
				curentPoint = MainWindow.pointTo2D(curentPoint);
				g.drawRect((int)Math.ceil(curentPoint.getX2D()+move), (int)Math.ceil(curentPoint.getY2D()+move), 1, 1);
				curentX++;
				curentZ += dz;
			}
			lineStartX += dx1;
			lineEndX += dx2;
			lineStartZ += dz1;
			lineEndZ += dz2;
			curentY++;
		}
		
//		if (yTop == yMid){
//			lineStartX = xTop;
//			lineEndX = xMid;
//			lineStartZ = zTop;
//			lineEndZ = zMid;
//		}
//		
//		if (dxTopBot < dxMidBot)
//		{
//			double t;
//			t = dxTopBot;
//			dxTopBot = dxMidBot;
//			dxMidBot = t;
//			
//			t = dzTopBot;
//			dzTopBot = dzMidBot;
//			dzMidBot = t;
//		}
//		Point p = new Point(0, 0, 0);
//		p.setXV(curentX);
//		p.setYV(curentY);
//		p.setZV(curentZ);
//		//DrawDot(g, p);
//		lineEndX = mid.getXV();
//		lineEndZ = mid.getZV();
//		lineStartX = top.getXV() + dxTopBot * (top.getYV() - mid.getYV()-1);
//		curentY = mid.getYV();
//		
//		//System.out.println("cur X: "+curentX+" cur y: "+curentY+" cur Z: "+curentZ);
//		while (curentY <= yBot) {
//			if(xMid>(xTop+xBot)/2){
//				leftNormal = normInterp(normalInD,bot.getNormal(), yBot-yMid, curentY-yMid);
//				rightNormal = normInterp(mid.getNormal(), bot.getNormal(), yBot-yMid, curentY-yMid);
//			}
//			else{
//				rightNormal = normInterp(normalInD,bot.getNormal(), yBot-yMid, curentY-yMid);
//				leftNormal = normInterp(mid.getNormal(), bot.getNormal(), yBot-yMid, curentY-yMid);
//			}
//			curentX = lineStartX;
//			curentZ = lineStartZ;
//			double dz = (lineStartZ - lineEndZ)/(lineStartX - lineEndX);
//			while ( curentX < lineEndX){
////				if (curentX < -1066 || curentX > -499|| curentZ > 114 || curentZ < 65){
////					System.out.println("Warning");
////					System.out.println("cur X: "+curentX+" cur y: "+curentY+" cur Z: "+curentZ);
////				}
//				curentNormal = normInterp(leftNormal, rightNormal, lineEndX-lineStartX, curentX-lineStartX);
//				Point curentPoint = new Point(curentX, curentY, curentZ);
//				curentPoint.setXV(curentX);
//				curentPoint.setYV(curentY);
//				curentPoint.setZV(curentZ);
//				g.setColor(calcColor(curentPoint, curentNormal));
//				curentPoint=MainWindow.pointTo2D(curentPoint);
//				
//				g.drawRect((int)Math.ceil(curentPoint.getX2D()+move), (int)Math.ceil(curentPoint.getY2D()+move), 1, 1);
//				curentX++;
//				curentZ += dz;
//			}
//			lineStartX += dxTopBot;
//			lineEndX += dxMidBot;
//			lineStartZ += dzTopBot;
//			lineEndZ += dzMidBot;
//			curentY++;
//		}
		
		
//System.out.println("-------------------------------------------------------------");
	}
	
	
	private Color calcColor(Point A, Normal n){
	  	double xA,yA,zA,xN,yN,zN;
		xA = A.getXV();
		yA = A.getYV();
		zA = A.getZV();
		xN = n.getX();
		yN = n.getY();
		zN = n.getZ();
		int r = 0,g = 0,b = 0;

		SpotLight s;
		for (int i = 0; i<spotLights.size();i++){
			s = spotLights.get(i);	
			double cosA = s.getCosV(xN,yN,zN,xA,yA,zA);
			double cosB = 1;
			double dist = Point.calcDist(s.getCoordinates(), A);
			dist /= 30;
			double mulA = (cosA*s.power*cosB);//dist;
			r += (int) (s.getR()* mulA);
			g += (int) (s.getG()* mulA);
			b += (int) (s.getB()* mulA);
		}
		int lightCount = spotLights.size();
		
		if (r>255)r=255;
		if (g>255)g=255;
		if (b>255)b=255;
		
		if (r<20)r=20;
		if (g<20)g=20;
		if (b<20)b=20;
		return new Color((int)(r),(int)(g),(int)(b));
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

