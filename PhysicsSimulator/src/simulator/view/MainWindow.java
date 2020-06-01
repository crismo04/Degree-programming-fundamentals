package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	// ...
	Controller control;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		control = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		this.setLocation(275, 100);
		JPanel mainPanel = new JPanel(new BorderLayout());
		//this.setContentPane(mainPanel);
		ControlPanel controlP = new ControlPanel(control);
		mainPanel.add(controlP, BorderLayout.PAGE_START);
		StatusBar statusB = new StatusBar(control);
		mainPanel.add(statusB, BorderLayout.PAGE_END);
		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS) );
		BodiesTable BTable = new BodiesTable(control);
		centro.add(BTable, BorderLayout.NORTH);
		Viewer vista = new Viewer(control);
		centro.add(vista, BorderLayout.SOUTH);
		mainPanel.add(centro, BorderLayout.CENTER);
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);		
	}

}
