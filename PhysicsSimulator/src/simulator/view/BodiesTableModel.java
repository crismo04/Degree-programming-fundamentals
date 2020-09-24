package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	private int bodyAttributes = 5; //the attributes of the body that loses mass or teleport
	private List<Body> bodies;

	BodiesTableModel(Controller ctrl) {
		bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public int getRowCount() {
		return bodies.size();
	}

	public int getColumnCount() {	
		return bodyAttributes;
	}

	public String getColumnName(int column) {
		switch(column){
		case 0:
			return "id";
		case 1:
			return "mass";
		case 2:
			return "Position";
		case 3:
			return "Velocity";
		case 4:
			return "Aceleration";
		default:
			return "-";
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return bodies.get(rowIndex).getId();
		case 1:
			return bodies.get(rowIndex).getMass();
		case 2:
			return bodies.get(rowIndex).getPosition();
		case 3:
			return bodies.get(rowIndex).getVelocity();
		case 4:
			return bodies.get(rowIndex).getAcceleration();
		default:
			return "-";
		}
	}

	// SimulatorObserver methods
	@Override
	public void onRegistraObserver(List<Body> bdy, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bodies = bdy;
				fireTableStructureChanged();
			}
		});
	}
	@Override
	public void onReset(List<Body> bdy, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bodies = bdy;
				fireTableStructureChanged();
			}
		});
	}
	@Override
	public void onBodyAdded(List<Body> bdy, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bodies = bdy;
				fireTableStructureChanged();
			}
		});
	}
	@Override
	public void onAdvance(List<Body> bdy, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bodies = bdy;
				fireTableStructureChanged();
			}
		});
	}
	public void onDeltaTimeChanged(double dt) {}
	public void onGravityLawChanged(String gLawsDesc) {}
}
