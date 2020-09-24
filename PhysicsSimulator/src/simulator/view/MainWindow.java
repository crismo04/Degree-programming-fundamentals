package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
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
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS) );
		BodiesTable BTable = new BodiesTable(control);
		center.add(BTable, BorderLayout.NORTH);
		Viewer view = new Viewer(control);
		center.add(view, BorderLayout.SOUTH);
		mainPanel.add(center, BorderLayout.CENTER);
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);		
	}

}
