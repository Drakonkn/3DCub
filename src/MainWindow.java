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

	double q = 10;
	double f = 45;
	double a = 45;
	
	double scale = 150;
	double xPosition = 0;
	double yPosition = 0;
	
	int lastMousePosX;
	int lastMousePosY;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Painter painter;
	private Model model3D;
	SpotLight s = new SpotLight();

	private Model origin;
	
	public MainWindow(){
		super();
		origin = new Model();
		model3D = new Model();
		painter = new Painter();
		painter.setModel(model3D);
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
					case 37: xPosition--; rep();break;
					case 38: yPosition--; rep();break;
					case 39: xPosition++; rep();break;
					case 40: yPosition++; rep();break;
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
			Vector<triangle> vector = model3D.getVector();
			for (int i = 0; i< vector.size();i++) {
				triangle tr = origin.getVector().get(i);
				triangle tr2 = vector.get(i);
				
				vector.get(i).A = pointToView(tr.A);//new Point3D(product(tr.A.getMatrix(), V, 1, 4, 4, 4));
				vector.get(i).B = pointToView(tr.B);//new Point3D(product(tr.B.getMatrix(), V, 1, 4, 4, 4));
				vector.get(i).C = pointToView(tr.C);//new Point3D(product(tr.C.getMatrix(), V, 1, 4, 4, 4));
				
				//s.coordinates = pointToView(s.coordinates);
				
				double xA,xB,xC,yA,yB,yC,zA,zB,zC, xN, yN,zN;
				xA = tr2.A.getX();
				xB = tr2.B.getX();
				xC = tr2.C.getX();
				yA = tr2.A.getY();
				yB = tr2.B.getY();
				yC = tr2.C.getY();
				zA = tr2.A.getZ();
				zB = tr2.B.getZ();
				zC = tr2.C.getZ();
				
				

				xN = (yB-yA)*(zC-zA)-(yC-yA)*(zB-zA);
				yN = (xB-xA)*(zC-zA)-(xC-xA)*(zB-zA);
				zN = (xB-xA)*(yC-yA)-(xC-xA)*(yB-yA);
				
				double lengthN = Math.sqrt(xN*xN+yN*yN+zN*zN);
				
				xN /= lengthN;
				yN /= lengthN;
				zN /= lengthN;
				int r,g,b;

				
				double cosA = s.getCos(xN,yN,zN,xA,yA,zA);
				double cosB = s.getCos(xN,yN,zN,xB,yB,zB);
				double cosC = s.getCos(xN,yN,zN,xC,yC,zC);
				
				double mulA = cosA*s.power;
				double mulB = cosB*s.power;
				double mulC = cosC*s.power;
				
				
				
				r = (s.getR()* mulA)<10?(int) (s.getR()* mulA)+10:(int) (s.getR()* mulA);
				g = (s.getG()* mulA)<10?(int) (s.getG()* mulA)+10:(int) (s.getG()* mulA);
				b = (s.getB()* mulA)<10?(int) (s.getB()* mulA)+10:(int) (s.getB()* mulA);
				tr2.A.color = new Color(r,g,b);
				
				r = (s.getR()* mulB)<10?(int) (s.getR()* mulB)+10:(int) (s.getR()* mulB);
				g = (s.getG()* mulB)<10?(int) (s.getG()* mulB)+10:(int) (s.getG()* mulB);
				b = (s.getB()* mulB)<10?(int) (s.getB()* mulB)+10:(int) (s.getB()* mulB);
				tr2.B.color = new Color(r,g,b);
				
				r = (s.getR()* mulC)<10?(int) (s.getR()* mulC)+10:(int) (s.getR()* mulC);
				g = (s.getG()* mulC)<10?(int) (s.getG()* mulC)+10:(int) (s.getG()* mulC);
				b = (s.getB()* mulC)<10?(int) (s.getB()* mulC)+10:(int) (s.getB()* mulC);
				tr2.C.color = new Color(r,g,b);
			}
			
			return ;
			
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
			Vector<triangle> vector= model3D.getVector();
			for ( int i =0; i<vector.size();i++) {
			
				
				
				model3D.getVector().get(i).A2 = pointTo2D(model3D.getVector().get(i).A);
				model3D.getVector().get(i).B2 = pointTo2D(model3D.getVector().get(i).B);
				model3D.getVector().get(i).C2 = pointTo2D(model3D.getVector().get(i).C);
				
				vector.get(i).z = (model3D.getVector().get(i).A.getZ() + model3D.getVector().get(i).B.getZ()
						+ model3D.getVector().get(i).C.getZ())/3;
				
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
					{0,0,5,0},
					{xPosition,yPosition,0,0}
				};
			V = product(V, scale, 4, 4, 4, 4);
			return new Point3D(product(point.getMatrix(), V, 1, 4, 4, 4));
		}
		
		private Point2D pointTo2D(Point3D point){
			double screenDist = 5;
			double C1 = 10;
			double C2 = 10;
			double x = screenDist * (point.getX()/ point.getZ() + C1);
			double y = screenDist * (point.getY()/ point.getZ() + C2);
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
