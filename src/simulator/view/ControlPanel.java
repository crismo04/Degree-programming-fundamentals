package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
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
	private JSpinner pasos;
	private JSpinner delay;
	private JTextField dt;	
	JToolBar toolBar;
	
	private volatile Thread hilo;
	
	private Controller controlador;
	double deltaTimeDefault;
	double	pasosDefault;

	ControlPanel(Controller ctrl) {
		controlador = ctrl;
		deltaTimeDefault = 2500.0;
		pasosDefault = 150;
		hilo = null;
		initGUI();
		controlador.addObserver(this);
	}
	
	private void initGUI(){
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(780,50));
		toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(700,50));
		
		//crear botones
		open = crearBoton("Abrir un fichero", "open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				InputStream in = null;
				JFileChooser fileCh = new JFileChooser(new File(".").getAbsolutePath());
				int f = fileCh.showOpenDialog(toolBar);
				if(f == JFileChooser.APPROVE_OPTION)
					try {
						in = new FileInputStream (fileCh.getSelectedFile());
						controlador.reset();
						controlador.loadBodies(in);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(toolBar , e.getMessage(), "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		physics = crearBoton("Cargar fisicas", "physics");
		physics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int numLeyes =controlador.getGravityLawsFactory().getInfo().toArray().length;
				String[] s = new String[numLeyes];
				for(int i = 0; i < numLeyes; i++ ) 
					s[i] = controlador.getGravityLawsFactory().getInfo().get(i).getString("desc");
				String ley = (String) JOptionPane.showInputDialog(toolBar,"Elige una ley:","Selector de leyes",
						JOptionPane.INFORMATION_MESSAGE, null, s, "Plane");
				JSONObject GL = null;
				for (JSONObject fe : controlador.getGravityLawsFactory().getInfo()) {
					if (ley.equals(fe.getString("desc"))) {
						GL = fe;
						break;
					}
				}
				
				//notifico a los observadores
				controlador.setGravityLaws(GL);
			}
		});
		
		run = crearBoton("Inicia la ejecucion", "run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				open.setEnabled(false);
				physics.setEnabled(false);
				run.setEnabled(false);
				Double delta = null;
				
				try {
					pasos.commitEdit();
					delay.commitEdit();
					delta = Double.parseDouble(dt.getText());
				} catch(Exception e) {
					//JOptionPane.showMessageDialog(toolBar ,"error en los campos de control panel, revisa el valor", "Error", JOptionPane.ERROR_MESSAGE);
				}
				/*no funciona con el objet que devuelve el spinner
				String paso = pasos.getValue() +"";
				paso.replaceAll(".0", "");
				*/
				//solo funciona asi
				int pas = (int) ((double) pasos.getValue());
				long delTime = (Integer) delay.getValue();  
				if(pas > 0 && delTime >= 0 && delTime <= 1000 && delta != null) { //!delta.isNaN()
					controlador.SetDeltaTime(delta);
					hilo = new Thread(new Runnable() {
						@Override
						public void run() {
								run_sim(pas, delTime);	
								open.setEnabled(true);
								physics.setEnabled(true);
								run.setEnabled(true);
								hilo = null;
							}
						}	
					);
					hilo.start();
				}
				else {
					JOptionPane.showMessageDialog(toolBar ,"error en los campos de control panel, revisa el valor", "Error", JOptionPane.ERROR_MESSAGE);
					open.setEnabled(true);
					physics.setEnabled(true);
					run.setEnabled(true);
					hilo = null;
				}
			}
		});

		stop = crearBoton("Detiene la ejecucion", "stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(hilo != null)
					hilo.interrupt(); //lanza una InterruptedException???????
			}
		});
		
		exit = crearBoton("Salir del programa", "exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String opc[] = {"yes", "no"};
				int n = JOptionPane.showOptionDialog(toolBar , "¿Estas seguro de que quieres salir?", "Salir",  
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opc, opc[1]);
				if(n == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		//creo resto de campos	
		JLabel LabDelay = new JLabel("delay: ");
		SpinnerNumberModel delayModel = new SpinnerNumberModel(1, 0, 1000, 1);
		delay = new JSpinner(delayModel);
		delay.setValue(1); //valor por defecto
		delay.setMaximumSize(new Dimension(60,50));
		
		JLabel LabPas = new JLabel("pasos: ");
		SpinnerNumberModel pasosModel = new SpinnerNumberModel(pasosDefault, 0, 10000000, 10);
		pasos = new JSpinner(pasosModel);
		pasos.setMaximumSize(new Dimension(70,50));
		
		JLabel Labdt = new JLabel("Delta Time: ");
		dt = new JTextField();
		dt.setEditable(true);
		dt.setText("" + deltaTimeDefault);  //valor por defecto
		dt.setMaximumSize(new Dimension(100,50));
		
		//añado a la toolBar
		toolBar.add(open);
		toolBar.add(physics);
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(LabDelay);
		toolBar.add(delay);
		toolBar.add(LabPas);
		toolBar.add(pasos);
		toolBar.add(Labdt);
		toolBar.add(dt);
		//toolBar.add(exit, BorderLayout.LINE_END);
		this.add(toolBar);
		this.add(exit, BorderLayout.LINE_END);
	}
	
	// other private/protected methods	
	private JButton crearBoton(String ToolTipText, String nombre) {
		JButton bot = new JButton();
		bot.setToolTipText(ToolTipText);
		bot.setIcon(new ImageIcon("resources/icons/" + nombre +".png"));
		return bot;
	}
	
	//ejecuta n pasos en el simulador
	private void run_sim(int n, long delayT) {
		
		while ( n>0 && !Thread.currentThread().isInterrupted()){
			
			try {controlador.run(1);} 
			catch (Exception e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(toolBar , "error en run", "Error", JOptionPane.ERROR_MESSAGE);
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
		
		// SimulatorObserver methods
	@Override
	public void onRegistraObserver(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltaTimeDefault = dt;
				pasosDefault = time;
			}
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				deltaTimeDefault = dt;
				pasosDefault = time;
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
