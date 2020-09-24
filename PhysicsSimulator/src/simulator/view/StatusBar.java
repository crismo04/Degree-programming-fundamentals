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
	private int bodies;
	private double time;
	private String law;
	private JLabel actualTime; //label for the actual time
	private JLabel laws; //label for the laws
	private JLabel numbodies; //label for the number of bodies
	JToolBar toolBar;

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		toolBar = new JToolBar();
		numbodies = new JLabel();
		actualTime = new JLabel();
		laws = new JLabel();
		toolBar.add(numbodies);
		toolBar.add(actualTime);
		toolBar.add(laws);
		this.add(toolBar, BorderLayout.PAGE_END);
	}
	
	private void update() {
		numbodies.setText("bodies:  " + bodies + "  |  ");
		actualTime.setText("actual time:  " + time + "  |  ");
		laws.setText("laws:  " + law + "  |  ");
	}
	
	// SimulatorObserver methods
	@Override
	public void onRegistraObserver(List<Body> bdy, double t, double dt, String gLawsDesc) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bodies = bdy.size();
				time = t;
				law = gLawsDesc;
				update();
			}
		});
	}
	@Override
	public void onReset(List<Body> bdy, double t, double dt, String gLawsDesc) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bodies = bdy.size();
				time = t;
				law = gLawsDesc;
				update();
			}
		});
	}
	@Override
	public void onBodyAdded(List<Body> bdy, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bodies = bdy.size();
				update();
			}
		});
		
	}
	@Override
	public void onAdvance(List<Body> bdy, double t) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				time = t;
				update();
			}
		});
		
	}
	public void onDeltaTimeChanged(double dt) {}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				law = gLawsDesc;
				update();
			}
		});
	}
}
