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

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class MainWindow extends JFrame implements ActionListener {

	double q = 100;
	double f = 45;
	double a = 45;
	
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
	private Model viewModel;
	Vector<SpotLight> spotLight;

	private Model origin;
	
	public MainWindow(){
		super();
		spotLight = new Vector<SpotLight>();
		spotLight.add(new SpotLight(new Color(0xFF0000),new Point3D(30, 30, 30)));
		//spotLight.add(new SpotLight(new Color(0x00FF00),new Point3D(-13, 13, 13)));
		//spotLight.add(new SpotLight(new Color(0x0000FF),new Point3D(-13,-13,-13)));
		origin = new Model();
		viewModel = new Model();
		painter = new Painter();
		painter.setModel(viewModel);
		painter.setSpotLight(spotLight);
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
					case KeyEvent.VK_LEFT: spotLight.get(0).rotate(); xPosition--; rep();break;
					case KeyEvent.VK_UP: yPosition--; rep();break;
					case KeyEvent.VK_RIGHT: xPosition++; rep();break;
					case KeyEvent.VK_DOWN: yPosition++; rep();break;
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
	}

	  public static void main(String[] args){
		    SwingUtilities.invokeLater(new Runnable(){
		      public void run(){
		        new MainWindow().setVisible(true);
		      }
		    });
		  }
		
	  private void getView(){			
			Vector<Triangle> vector = viewModel.getVector();
			for (int i = 0; i< vector.size();i++) {
				Triangle origTriangle =     origin.getVector().get(i);
				Triangle viewTriangle = viewModel.getVector().get(i);
				
				vector.get(i).A = pointToView(origTriangle.A);
				vector.get(i).B = pointToView(origTriangle.B);
				vector.get(i).C = pointToView(origTriangle.C);
				
				for (int k =0; k<spotLight.size();k++){
					spotLight.get(k).setViewCoordinates(pointToView(spotLight.get(k).getCoordinates()));
				}
				
				
				double xN, yN,zN;
				
				
				Point3D p = Point3D.sub(viewTriangle.B, viewTriangle.A);
				Point3D q = Point3D.sub(viewTriangle.C, viewTriangle.A);
				Point3D n = Point3D.normalize(Point3D.cross(p, q));
				
				xN = n.getX();
				yN = n.getY();
				zN = n.getZ();
				
				viewTriangle.A.color = calcColor(origTriangle.A, xN, yN, zN);
				viewTriangle.B.color = calcColor(origTriangle.B, xN, yN, zN);
				viewTriangle.C.color = calcColor(origTriangle.C, xN, yN, zN);
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
				double cosB = 1;// calcCos(xN,yN,zN,xA,yA,zA);
				double mulA = cosA*s.power*cosB;
				r += (int) (s.getR()* mulA);
				g += (int) (s.getG()* mulA);
				b += (int) (s.getB()* mulA);
			}
			int lightCount = spotLight.size();
			return new Color((int)(r/lightCount),(int)(g/lightCount),(int)(b/lightCount));
	  }
	  
		double calcCos(double Nx, double Ny, double Nz, double x, double y, double z){
			double Ex = q * Math.sin(Math.toRadians(-f))*Math.cos(Math.toRadians(-a));
			double Ey = q * Math.sin(Math.toRadians(-f))*Math.sin(Math.toRadians(-a));
			double Ez = q * Math.cos(Math.toRadians(-f));
			

			
			double X = Ex -x;
			double Y = Ey -y;
			double Z = Ez -z;
			
			double length = Math.sqrt(X*X + Y*Y + Z*Z);
			
			X /= length;
			Y /= length;
			Z /= length;
			
			double mul = (x*X) + (y*Y) + (z*Z);
			double length1 = Math.sqrt(X*X+Y*Y+Z*Z);
			double length2 = Math.sqrt(x*x+y*y+z*z);
			return Math.abs(mul/(length1*length2));
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
			
			Vector<Triangle> vector= viewModel.getVector();
			for ( int i =0; i<vector.size();i++) {
				viewModel.getVector().get(i).A2 = pointTo2D(viewModel.getVector().get(i).A);
				viewModel.getVector().get(i).B2 = pointTo2D(viewModel.getVector().get(i).B);
				viewModel.getVector().get(i).C2 = pointTo2D(viewModel.getVector().get(i).C);
				
				vector.get(i).z = (viewModel.getVector().get(i).A.getZ() + viewModel.getVector().get(i).B.getZ()
						+ viewModel.getVector().get(i).C.getZ())/3;
				
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
