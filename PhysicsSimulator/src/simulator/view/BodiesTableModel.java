package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	private int atributosCuerpo = 5; //los atributos del cuerpo que pierde masa
	private List<Body> cuerpos;

	BodiesTableModel(Controller ctrl) {
		cuerpos = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public int getRowCount() {
		return cuerpos.size();
	}

	public int getColumnCount() {	
		return atributosCuerpo;
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
			return cuerpos.get(rowIndex).getId();
		case 1:
			return cuerpos.get(rowIndex).getMasa();
		case 2:
			return cuerpos.get(rowIndex).getPosicion();
		case 3:
			return cuerpos.get(rowIndex).getVelocidad();
		case 4:
			return cuerpos.get(rowIndex).getAceleracion();
		default:
			return "-";
		}
	}

	// SimulatorObserver methods
	@Override
	public void onRegistraObserver(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				fireTableStructureChanged();
			}
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				fireTableStructureChanged();
			}
		});
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				fireTableStructureChanged();
			}
		});
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cuerpos = bodies;
				fireTableStructureChanged();
			}
		});
	}
	public void onDeltaTimeChanged(double dt) {}
	public void onGravityLawChanged(String gLawsDesc) {}
}
