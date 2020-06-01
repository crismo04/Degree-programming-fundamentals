package simulator.launcher;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/*
 * Examples of command-line parameters:
 * 
 *  -h
 *  -i resources/examples/ex4.4body.txt -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl ftcg
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl nlug
 *
 */

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;
import simulator.factories.*;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;
import simulator.control.*;

public class Main {

	// default values for some parameters
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static Integer _stimeDefaultValue = 150;

	// some attributes to stores values corresponding to command-line parameters
	private static Double _dtime = null;
	private static int _steps = 0;
	private static String _inFile = null;
	private static String _outFile = null;
	private static JSONObject _gravityLawsInfo = null;
	private static boolean salidaEstandar = false;
	private static boolean modoConsola = false;
	private static boolean entradaFichero = false;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<GravityLaws> _gravityLawsFactory;

	//inicializa las leyes y los cuerpos
	private static void init() {
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLosingBodyBuilder());
		bodyBuilders.add(new UFOBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);

		ArrayList<Builder<GravityLaws>> gravityLawsBuilders = new ArrayList<>();
		gravityLawsBuilders.add(new NewtonUniversalGravitationBuilder());
		gravityLawsBuilders.add(new FallingToCenterGravityBuilder());
		gravityLawsBuilders.add(new NoGravityBuilder());
		_gravityLawsFactory = new BuilderBasedFactory<GravityLaws>(gravityLawsBuilders);
	}
	
	//gestiona los argumentos del programa
	private static void parseArgs(String[] args) {		
		Options cmdLineOptions = buildOptions();// define the valid command line options
		
		CommandLineParser parser = new DefaultParser();// parse the command line as provided in args
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			ParseModeOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseDeltaTimeOption(line);
			parseOutputOption(line);
			ParseStepOption(line);
			parseGravityLawsOption(line);
			
		// if there are some remaining arguments, then something wrong is provided in the command line
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}

	//construye las opciones que se muestran con H
	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
		.desc("A double representing actual time, in seconds, per simulation step. Default value: " + _dtimeDefaultValue + ".").build());
		
		// gravity laws -- there is a workaround to make it work even when _gravityLawsFactory is null. 
		String gravityLawsValues = "N/A";
		String defaultGravityLawsValue = "N/A";
		/*
		if (_gravityLawsFactory != null) {
			gravityLawsValues = "";
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gravityLawsValues.length() > 0) {
					gravityLawsValues = gravityLawsValues + ", ";
				}
				gravityLawsValues = gravityLawsValues + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
			}
			defaultGravityLawsValue = _gravityLawsFactory.getInfo().get(0).getString("type");
		}*/
		
		cmdLineOptions.addOption(Option.builder("gl").longOpt("gravity-laws").hasArg()
				.desc("Gravity laws to be used in the simulator. Possible values: " + gravityLawsValues
						+ ". Default value: '" + defaultGravityLawsValue + "'.")
				.build());
		
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc(" Execution Mode. Possible values:\n" + 
				"’batch’ (Batch mode), ’gui’ (Graphical User Interfacemode).\n Default value: ’batch’").build());
		
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where output is written.\n" + 
				" Default value: the standard output.").build());
		
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("An integer representing the number of simulation steps.\n" + 
				"Default value: 150.").build());
		
		return cmdLineOptions;
	}

	//metodos parse para los argumentos
	
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			if(!modoConsola)
				throw new ParseException("An input file of bodies is required");
		}
			else 
				entradaFichero = true; // si estamos en modo consola activo para cargar el fichero
		}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static void parseGravityLawsOption(CommandLine line) throws ParseException {
		// this line is just a work around to make it work even when _gravityLawsFactory is null,
		// you can remove it when've defined _gravityLawsFactory // if (_gravityLawsFactory == null) return;	
		String gl = line.getOptionValue("gl");
		if (gl != null) {
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gl.equals(fe.getString("type"))) {
					_gravityLawsInfo = fe;
					break;
				}
			}
			if (_gravityLawsInfo == null) {
				throw new ParseException("Invalid gravity laws: " + gl);
			}
		} else {
			_gravityLawsInfo = _gravityLawsFactory.getInfo().get(0);
		}
	}

	private static void parseOutputOption(CommandLine line) {
		_outFile = line.getOptionValue("o");
		if(_outFile == null)
			salidaEstandar = true;
	}
	
	private static void ParseStepOption(CommandLine line) throws ParseException {
		String step = line.getOptionValue("s", _stimeDefaultValue.toString());
		try {
			_steps = Integer.parseInt(step);
			assert (_steps >= 0);
		} catch (Exception e) {
			throw new ParseException("Invalid steps value: " + step);
		}
	}
	private static void ParseModeOption(CommandLine line) throws ParseException {
		String modo = line.getOptionValue("m");
		if(modo == null)
			modo = "batch";
		if(modo.equalsIgnoreCase("batch") || modo.equalsIgnoreCase("gui")) {
			if(modo.equalsIgnoreCase("batch"))
				modoConsola = true;
		}
		else {
			throw new ParseException("El campo del modo no es valido");
		}
	}
	private static void startGUIMode() throws Exception{
		GravityLaws gravityLaws = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		PhysicsSimulator sim = new PhysicsSimulator(gravityLaws,_dtime);
		Controller ctrl = new Controller(sim,_bodyFactory, _gravityLawsFactory);
		if(entradaFichero) {
			InputStream in = new FileInputStream (_inFile);
			ctrl.loadBodies(in);
		}
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new MainWindow(ctrl);
			}
		});	
	}	
	
	private static void startBatchMode() throws Exception {
		InputStream in = new FileInputStream (_inFile);
		OutputStream os;
		if(!salidaEstandar) 
			os = new FileOutputStream(_outFile);
		else
			os = new FileOutputStream(FileDescriptor.out);
		GravityLaws gravityLaws = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		PhysicsSimulator sim = new PhysicsSimulator(gravityLaws,_dtime);
		Controller ctrl = new Controller(sim,_bodyFactory, _gravityLawsFactory);
		ctrl.loadBodies(in);
		ctrl.run(_steps, os);
	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if(modoConsola)
			startBatchMode();
		else
			startGUIMode();
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);	
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
