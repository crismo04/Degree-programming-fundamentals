package simulator.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	// ...
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> cuerpos;
	private boolean _showHelp;
	
	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),"Viewer",TitledBorder.LEFT, TitledBorder.TOP));
		this.setPreferredSize(new Dimension(780,333));
		cuerpos = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
				break;
				case '=':
					autoScale();
				break;
				case 'h':
					_showHelp = !_showHelp;
					break;
				default:
				}
				repaint();
			}

			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
		});
		
		addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gr.setBackground(Color.CYAN);
		//calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		//cruz en el centro
		gr.setPaint(Color.GRAY);
		gr.drawString("+", _centerX, _centerY +10);
		
		//draw bodies
		for(Body b: cuerpos) {
			int x = _centerX + (int)(b.getPosicion().coordinate(0)/_scale);
			int y = _centerY - (int)(b.getPosicion().coordinate(1)/_scale);
			gr.setStroke(new BasicStroke(2)); //grosor de 2 px
			gr.setPaint(Color.BLUE);
			gr.drawOval(x, y, 5, 5);
			gr.setPaint(Color.BLACK);
			gr.drawString(b.getId(), x + 10, y); //dibuja el ID del cuerpo un poco a la derecha	
		}
		//muestra la ayuda si esta activada
		if(_showHelp) {
			String help = "h: help on/off  | ";
			help +="+: zoom in  | ";
			help +="-: zoom out  | ";
			help +="=: fit  | ";
			help +="escala: " + _scale;
			gr.setPaint(Color.RED);
			gr.drawString(help, 10, 25); //coloca la ayuda arriba a la izquierda	
		}
	}
	
	//other private/protected methods
	private void autoScale() {
		double max = 1000.0;
		for (Body b : cuerpos) {
			Vector p = b.getPosicion();
			for (int i = 0; i < p.dim(); i++)
				max = Math.max(max,
						Math.abs(b.getPosicion().coordinate(i)));
		}
		double size = Math.max(1.0, Math.min((double) getWidth(), (double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	
	//SimulatorObserver methods
	@Override
	public void onRegistraObserver(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				autoScale();
				repaint();
			}
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				autoScale();
				repaint();
			}
		});
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				repaint();
			}
		});
	}
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				repaint();
			}
		});
	}
	public void onDeltaTimeChanged(double dt) {}
	public void onGravityLawChanged(String gLawsDesc) {}
}
