package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	// ...
	private JButton open;
	private JButton physics;
	private JButton run;
	private JButton stop;
	private JButton exit;
	private JSpinner steps;
	private JSpinner delay;
	private JTextField dt;	
	JToolBar toolBar;
	
	private volatile Thread thr;
	
	private Controller control;
	double deltaTimeDefault;
	double	defaultSteps;

	ControlPanel(Controller ctrl) {
		control = ctrl;
		deltaTimeDefault = 2500.0;
		defaultSteps = 150;
		thr = null;
		initGUI();
		control.addObserver(this);
	}
	
	private void initGUI(){
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(780,50));
		toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(700,50));
		
		//create buttons
		open = createButton("Open a file", "open");
		open.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){
				InputStream in = null;
				JFileChooser fileCh = new JFileChooser(new File(".").getAbsolutePath());
				int f = fileCh.showOpenDialog(toolBar);
				if(f == JFileChooser.APPROVE_OPTION)
					try {
						in = new FileInputStream (fileCh.getSelectedFile());
						control.reset();
						control.loadBodies(in);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(toolBar , e.getMessage(), "Error opening the file", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		physics = createButton("Load the physics", "physics");
		physics.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int numLaws =control.getGravityLawsFactory().getInfo().toArray().length;
				String[] s = new String[numLaws];
				for(int i = 0; i < numLaws; i++ ) 
					s[i] = control.getGravityLawsFactory().getInfo().get(i).getString("desc");
				String ley = (String) JOptionPane.showInputDialog(toolBar,"Chose a law:","Law selector",
						JOptionPane.INFORMATION_MESSAGE, null, s, "Plane");
				JSONObject GL = null;
				for (JSONObject fe : control.getGravityLawsFactory().getInfo()) {
					if (ley.equals(fe.getString("desc"))) {
						GL = fe;
						break;
					}
				}
				
				//notice to observers
				control.setGravityLaws(GL);
			}
		});
		
		run = createButton("Start the ejecution", "run");
		
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open.setEnabled(false);
				physics.setEnabled(false);
				run.setEnabled(false);
				Double delta = null;
				
				try {
					steps.commitEdit();
					delay.commitEdit();
					delta = Double.parseDouble(dt.getText());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(toolBar ,"error in the control panel fields, check the value", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				int s = (int) ((double) steps.getValue());
				long delTime = (Integer) delay.getValue();  
				if(s > 0 && delTime >= 0 && delTime <= 1000 && delta != null) { //!delta.isNaN()
					control.SetDeltaTime(delta);
					
					thr = new Thread(new Runnable() {
						public void run() {
								run_sim(s, delTime);	
								open.setEnabled(true);
								physics.setEnabled(true);
								run.setEnabled(true);
								thr = null;
							}
						}	
					);
					thr.start();
				}
				else {
					JOptionPane.showMessageDialog(toolBar ,"rror in the control panel fields, check the value", "Error", JOptionPane.ERROR_MESSAGE);
					open.setEnabled(true);
					physics.setEnabled(true);
					run.setEnabled(true);
					thr = null;
				}
			}
		});

		stop = createButton("Stop the ejecution", "stop");
		stop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(thr != null)
					thr.interrupt(); //throw InterruptedException
			}
		});
		
		exit = createButton("Quit the application", "exit");
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String opc[] = {"yes", "no"};
				int n = JOptionPane.showOptionDialog(toolBar , "Are you sure you want to exit?", "Exit",  
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opc, opc[1]);
				if(n == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		
		//create the rest of fields
		JLabel LabDelay = new JLabel("delay: ");
		SpinnerNumberModel delayModel = new SpinnerNumberModel(1, 0, 1000, 1);
		delay = new JSpinner(delayModel);
		delay.setValue(1); //default value
		delay.setMaximumSize(new Dimension(60,50));
		
		JLabel LabPas = new JLabel("steps: ");
		SpinnerNumberModel modelSteps = new SpinnerNumberModel(defaultSteps, 0, 10000000, 10);
		steps = new JSpinner(modelSteps);
		steps.setMaximumSize(new Dimension(70,50));
		
		JLabel Labdt = new JLabel("Delta Time: ");
		dt = new JTextField();
		dt.setEditable(true);
		dt.setText("" + deltaTimeDefault);  //default value
		dt.setMaximumSize(new Dimension(100,50));
		
		//add the toolBar
		toolBar.add(open);
		toolBar.add(physics);
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(LabDelay);
		toolBar.add(delay);
		toolBar.add(LabPas);
		toolBar.add(steps);
		toolBar.add(Labdt);
		toolBar.add(dt);
		//toolBar.add(exit, BorderLayout.LINE_END);
		this.add(toolBar);
		this.add(exit, BorderLayout.LINE_END);
	}
	
	// other private/protected methods	
	private JButton createButton(String ToolTipText, String name) {
		JButton bot = new JButton();
		bot.setToolTipText(ToolTipText);
		bot.setIcon(new ImageIcon("resources/icons/" + name +".png"));
		return bot;
	}
	
	//execute n steps in the simulator
	private void run_sim(int n, long delayT) {
		
		while ( n>0 && !Thread.currentThread().isInterrupted()){	
			try {control.run(1);} 
			catch (Exception e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(toolBar , "run error", "Error", JOptionPane.ERROR_MESSAGE);
					}
				});
				return;
			}
			
			try {Thread.sleep(delayT);} 
			catch (InterruptedException e) {
				return;
			}
			n--;
		}
	}
		
	//SimulatorObserver methods
	@Override
	public void onRegistraObserver(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltaTimeDefault = dt;
				defaultSteps = time;
			}
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltaTimeDefault = dt;
				defaultSteps = time;
			}
		});
	}
	public void onBodyAdded(List<Body> bodies, Body b) {}
	public void onAdvance(List<Body> bodies, double time) {}
	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltaTimeDefault = dt;
			}
		});
	}
	public void onGravityLawChanged(String gLawsDesc) {}
}
