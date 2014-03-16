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
		spotLight.add(new SpotLight(new Color(0xFF0000),new Point(25, -25,  25)));
		spotLight.add(new SpotLight(new Color(0x00FF00),new Point(25, -25,  25)));
		spotLight.add(new SpotLight(new Color(0x0000FF),new Point(25,  25,  25)));
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
				
				origTriangle.A.setView(pointToView(origTriangle.A));
				origTriangle.B.setView(pointToView(origTriangle.B));
				origTriangle.C.setView(pointToView(origTriangle.C));
				
				for (int k =0; k<spotLight.size();k++){
					spotLight.get(k).getCoordinates().setView(pointToView(spotLight.get(k).getCoordinates()));
				}
				
				origTriangle.norm.setView(pointToView(origTriangle.norm));
				double xN = origTriangle.norm.getX();
				double yN = origTriangle.norm.getY();
				double zN = origTriangle.norm.getZ();
				
				origTriangle.AColor = calcColor(origTriangle.A, xN, yN, zN);
				origTriangle.BColor = calcColor(origTriangle.B, xN, yN, zN);
				origTriangle.CColor = calcColor(origTriangle.C, xN, yN, zN);
			}
			return ;
		}
	  
	  private Color calcColor(Point A,double xN,double yN,double zN){
		  	double xA,yA,zA;
			xA = A.getX();
			yA = A.getY();
			zA = A.getZ();
			int r = 0,g = 0,b = 0;

			SpotLight s;
			for (int i = 0; i<spotLight.size();i++){
				s = spotLight.get(i);	
				double cosA = s.getCos(xN,yN,zN,xA,yA,zA);
				System.out.println(xN+" "+yN+" "+zN);
				double cosB = 1;
				double dist = Point.calcDist(s.getCoordinates(), A);
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
			
			if (r<20)r=20;
			if (g<20)g=20;
			if (b<20)b=20;
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
				pointTo2D(spotLight.get(k).coordinates);
			}
			
			Vector<Triangle> vector= origin.getVector();
			for ( int i =0; i<vector.size();i++) {
				pointTo2D(origin.getVector().get(i).A);
				pointTo2D(origin.getVector().get(i).B);
				pointTo2D(origin.getVector().get(i).C);
				pointTo2D(origin.getVector().get(i).norm);
				
				vector.get(i).z = (origin.getVector().get(i).A.getzV() + origin.getVector().get(i).B.getzV()
						+ origin.getVector().get(i).C.getzV())/3;
			}
			
			return;
		}
		
		
		private Point pointToView(Point point){
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
			
			Point res = new Point(product(point.getMatrix(), V, 1, 4, 4, 4));
			res.setNorm(point.getxN(), point.getyN(), point.getzN());
			return res;
		}
		
		private void pointTo2D(Point point){
			double screenDist = 11;
			double C1 = 100;
			double C2 = 100;
			int x = (int)(screenDist * (point.getxV()/ point.getzV() + C1));
			int y = (int)(screenDist * (point.getyV()/ point.getzV() + C2));
			point.setX2D(x);
			point.setY2D(y);
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
