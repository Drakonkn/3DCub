import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class MainWindow extends JFrame implements ActionListener {

	double q = 100;
	double f = 45;
	double a = 45;
	int activSpot = 0;
	
	double scale = 20;
	double xPosition = -500;
	double yPosition = 0;
	
	int lastMousePosX;
	int lastMousePosY;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Painter painter;
	Vector<SpotLight> spotLight;

	private Model origin;
	
	public MainWindow(){
		super();
		spotLight = new Vector<SpotLight>();
		spotLight.add(new SpotLight(new Color(0xFF0000),new Point3D(25, -25,  25)));
		spotLight.add(new SpotLight(new Color(0x00FF00),new Point3D(25, -25,  25)));
		spotLight.add(new SpotLight(new Color(0x0000FF),new Point3D(25,  25,  25)));
		origin = new Model();
		painter = new Painter();
		painter.setModel(origin);
		painter.setSpotLight(spotLight);
		JButton red = new JButton("Red");
		JButton gre = new JButton("Green");
		JButton blu = new JButton("Blue");
		red.setFocusable(false);
		gre.setFocusable(false);
		blu.setFocusable(false);
		
		red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activSpot = 0;				
			}
		});
		gre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activSpot = 1;
			}
		});
		blu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activSpot = 2;
				
			}
		});
		
		
		painter.add(red);
		painter.add(gre);
		painter.add(blu);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(500, 500);
		setContentPane(painter);
		
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int rotate = e.getWheelRotation();
				switch (rotate) {
				case -1:
					if (scale < 50){
						scale += 1;
						rep();}
					break;
				case 1:
					if (scale > 3){
						scale -= 1;
						rep();}
					break;

				default:
					break;
				}
				
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				f = f - (e.getX() - lastMousePosX); 
				a = a + (e.getY() - lastMousePosY);
				rep();
				lastMousePosX = e.getX();
				lastMousePosY = e.getY();
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				lastMousePosX = e.getX();
				lastMousePosY = e.getY();
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				int code = arg0.getKeyCode();
				switch (code){
					case KeyEvent.VK_LEFT: spotLight.get(activSpot).rotateLeft(); rep();break;
					case KeyEvent.VK_UP: spotLight.get(activSpot).rotateTop(); rep();break;
					case KeyEvent.VK_RIGHT: spotLight.get(activSpot).rotateRight(); rep();break;
					case KeyEvent.VK_DOWN: spotLight.get(activSpot).rotateBot(); rep();break;
				}
			}
		});
		rep();
		javax.swing.Timer timer  = new javax.swing.Timer(100,this);
		timer.start();
	}
	
	private void rep(){
		getProject();
		painter.repaint();
		setFocusable(true);
	}

	  public static void main(String[] args){
		    SwingUtilities.invokeLater(new Runnable(){
		      public void run(){
		        new MainWindow().setVisible(true);
		      }
		    });
		  }
		
	  private void getView(){			
			Vector<Triangle> vector = origin.getVector();
			for (int i = 0; i< vector.size();i++) {
				Triangle origTriangle =     origin.getVector().get(i);
				
				vector.get(i).AV = pointToView(origTriangle.A);
				vector.get(i).BV = pointToView(origTriangle.B);
				vector.get(i).CV = pointToView(origTriangle.C);
				
				for (int k =0; k<spotLight.size();k++){
					spotLight.get(k).setViewCoordinates(pointToView(spotLight.get(k).getCoordinates()));
				}
				
				double xN, yN,zN;
				
				
				Point3D p = Point3D.sub(origTriangle.BV, origTriangle.AV);
				Point3D q = Point3D.sub(origTriangle.CV, origTriangle.AV);
				Point3D n = Point3D.normalize(Point3D.cross(p, q));
				
				p = Point3D.sub(origTriangle.B, origTriangle.A);
				q = Point3D.sub(origTriangle.C, origTriangle.A);
				n = Point3D.normalize(Point3D.cross(p, q));
				
				origTriangle.norm = n;
				origTriangle.normV = pointToView(origTriangle.norm);
				xN = n.getX();
				yN = n.getY();
				zN = n.getZ();
				
				origTriangle.AV.color = calcColor(origTriangle.A, xN, yN, zN);
				origTriangle.BV.color = calcColor(origTriangle.B, xN, yN, zN);
				origTriangle.CV.color = calcColor(origTriangle.C, xN, yN, zN);
			}
			return ;
		}
	  
	  private Color calcColor(Point3D A,double xN,double yN,double zN){
		  	double xA,yA,zA;
			xA = A.getX();
			yA = A.getY();
			zA = A.getZ();
			int r = 0,g = 0,b = 0;

			SpotLight s;
			for (int i = 0; i<spotLight.size();i++){
				s = spotLight.get(i);	
				double cosA = s.getCos(xN,yN,zN,xA,yA,zA);
				double cosB = 1;
				double dist = Point3D.calcDist(s.getCoordinates(), A);
				dist /= 30;
				double mulA = (cosA*s.power*cosB)/dist;
				r += (int) (s.getR()* mulA);
				g += (int) (s.getG()* mulA);
				b += (int) (s.getB()* mulA);
			}
			int lightCount = spotLight.size();
			
			if (r>255)r=255;
			if (g>255)g=255;
			if (b>255)b=255;
			
			if (r<30)r=30;
			if (g<30)g=30;
			if (b<30)b=30;
			return new Color((int)(r/lightCount),(int)(g/lightCount),(int)(b/lightCount));
	  }
	  
	  public static double[][] product(double[][] first, double[][] second, int row1, int col1,
				int row2, int col2) {
			  double[][] res = new double[row1][col2];
		      if (col1 == row2) {
		         for (int i = 0; i < row1; i++)
		            for (int k = 0; k < col2; k++)
		               for (int j = 0; j < row2; j++)
		                  res[i][k] += first[i][j] * second[j][k];
		         return res;
		      } else
		         return null;
		   }
		
	  private void getProject(){
		
			getView();
			
			for (int k =0; k<spotLight.size();k++){
				spotLight.get(k).coordinates2D = pointTo2D(spotLight.get(k).getViewCoordinates());
			}
			
			Vector<Triangle> vector= origin.getVector();
			for ( int i =0; i<vector.size();i++) {
				origin.getVector().get(i).A2 = pointTo2D(origin.getVector().get(i).AV);
				origin.getVector().get(i).B2 = pointTo2D(origin.getVector().get(i).BV);
				origin.getVector().get(i).C2 = pointTo2D(origin.getVector().get(i).CV);
				origin.getVector().get(i).norm2D = pointTo2D(origin.getVector().get(i).normV);
				
				vector.get(i).z = (origin.getVector().get(i).AV.getZ() + origin.getVector().get(i).BV.getZ()
						+ origin.getVector().get(i).CV.getZ())/3;
			}
			
			return;
		}
		
		
		private Point3D pointToView(Point3D point){
			double cosa = Math.cos(Math.toRadians(a));
			double cosf = Math.cos(Math.toRadians(f));
			
			double sina = Math.sin(Math.toRadians(a));
			double sinf = Math.sin(Math.toRadians(f));
			
			double V[][] = 
				{
					{-sinf,-cosa*cosf,-sina*cosf,0},
					{cosf ,-cosa*sinf,-sina*sinf,0},
					{0    ,sina      ,-cosa     ,0},
					{0    ,0         ,q         ,1}
				};
			
			double scale[][] = 
				{
					{this.scale,0,0,0},
					{0,this.scale,0,0},
					{0,0,1,0},
					{xPosition,yPosition,0,0}
				};
			V = product(V, scale, 4, 4, 4, 4);
			return new Point3D(product(point.getMatrix(), V, 1, 4, 4, 4));
		}
		
		private Point2D pointTo2D(Point3D point){
			double screenDist = 11;
			double C1 = 100;
			double C2 = 100;
			int x = (int)(screenDist * (point.getX()/ point.getZ() + C1));
			int y = (int)(screenDist * (point.getY()/ point.getZ() + C2));
			return new Point2D(x,y,point.color);
		}
		
		double[][] extend(double[][] matrix) {
			double res[][] = new double[4][4];
			
			for (int i = 0; i<3;i++)
				for (int j = 0; j<3;j++)
					res[i][j] = matrix[i][j];
			
			res[3][0] = 0;
			res[3][1] = 0;
			res[3][2] = 0;
			res[3][3] = 1;
			
			res[0][3] = 0;
			res[1][3] = 0;
			res[2][3] = 0;
			res[3][3] = 1;

			
			return res;
			  
		   }

		@Override
		public void actionPerformed(ActionEvent e) {
			rep();
		}
}
