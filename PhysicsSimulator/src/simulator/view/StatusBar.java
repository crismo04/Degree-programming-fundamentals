package simulator.view;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	private int cuerpos;
	private double tiempo;
	private String ley;
	private JLabel tiempoActual; //etiqueta para el tiempo actual
	private JLabel leyes; //etiqueta para las leyes
	private JLabel numCuerpos; //etiqueta para el numero de cuerpos
	JToolBar toolBar;

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		toolBar = new JToolBar();
		numCuerpos = new JLabel();
		tiempoActual = new JLabel();
		leyes = new JLabel();
		toolBar.add(numCuerpos);
		toolBar.add(tiempoActual);
		toolBar.add(leyes);
		this.add(toolBar, BorderLayout.PAGE_END);
	}
	
	private void actualiza() {
		numCuerpos.setText("cuerpos:  " + cuerpos + "  |  ");
		tiempoActual.setText("Tiempo actual:  " + tiempo + "  |  ");
		leyes.setText("leyes:  " + ley + "  |  ");
	}
	
	// SimulatorObserver methods
	@Override
	public void onRegistraObserver(List<Body> bodies, double time, double dt, String gLawsDesc) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies.size();
				tiempo = time;
				ley = gLawsDesc;
				actualiza();
			}
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies.size();
				tiempo = time;
				ley = gLawsDesc;
				actualiza();
			}
		});
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies.size();
				actualiza();
			}
		});
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tiempo = time;
				actualiza();
			}
		});
		
	}
	public void onDeltaTimeChanged(double dt) {}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ley = gLawsDesc;
				actualiza();
			}
		});
	}
}
