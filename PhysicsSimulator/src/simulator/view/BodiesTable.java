package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(780,110));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),"Bodies",TitledBorder.LEFT, TitledBorder.TOP));
		JTable tabla = new JTable( new BodiesTableModel(ctrl));
		JScrollPane area = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		area.setPreferredSize(new Dimension(780,100));
		this.add(area, BorderLayout.NORTH);
		this.setVisible(true);
	}
}