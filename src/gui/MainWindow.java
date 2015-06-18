package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import applicationPart.Airport;
import applicationPart.Country;
import applicationPart.City;
import applicationPart.Airline;
import applicationPart.Filter;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Earth3DMap earth3dMap;
	private JPanel mainPanel;
	private Canvas canvas;


	private ComboBoxAutoComplete<Airport> dstAirpNameField, srcAirpNameField;
	private ComboBoxAutoComplete<City> srcCityNameField, dstCityNameField;
	private ComboBoxAutoComplete<Country>srcCountryNameField, dstCountryNameField;
	private ComboBoxAutoComplete<Airline> airlineNameField;
	private JCheckBox allAirpCheckBox;
	JSpinner airpSpinner, arcSpinner;

	
	public MainWindow() throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("3D Flight Map -- Amirali Ghazi, Enzo Hamelin");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO : Uncomment this in order to stop the application
				// when the windows will be closed.
				earth3dMap.stop();
			}
		});

		init3D();

		createMenu();
		
		
		mainPanel = new JPanel(new BorderLayout(1,1), true);
		
		JPanel searchPane = new JPanel(true);
		searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.Y_AXIS));
		
		/* Panel pour les vols de départ */
		JPanel srcPanel = new JPanel(true);
		srcPanel.setLayout(new GridBagLayout());
		
		srcCountryNameField = new ComboBoxAutoComplete<Country>(Country.getCMap().values());
		
		srcCityNameField = new ComboBoxAutoComplete<City>(
				Filter.filterCitiesByCountry(((Country)(srcCountryNameField.getSelectedItem())).getName()));
		
		ArrayList<ArrayList<Airport>> airportsByCity = 
				Filter.filterAirportsByCity(((City)(srcCityNameField.getSelectedItem())).getName());
		ArrayList<Airport> airports = new ArrayList<Airport>();
		Iterator<ArrayList<Airport>> it = airportsByCity.iterator();
		while(it.hasNext()) {
			Iterator<Airport> it2 = it.next().iterator();
			while(it2.hasNext()) {
				Airport airport = it2.next();
				if(airport.getCity().equals(srcCityNameField.getSelectedItem()))
					airports.add(airport);
			}
		}
		srcAirpNameField = new ComboBoxAutoComplete<Airport>(airports);
		
		//Listener selection des items des combo box
		srcCountryNameField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				srcAirpNameField.setItems(null);
				srcCityNameField.setItems(
						Filter.filterCitiesByCountry(((Country)(srcCountryNameField.getSelectedItem())).getName()));
			}
		});
		srcCityNameField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(srcCityNameField.getSelectedItem() != null) {
					ArrayList<ArrayList<Airport>> airportsByCity = 
							Filter.filterAirportsByCity(((City)(srcCityNameField.getSelectedItem())).getName());
					ArrayList<Airport> airports = new ArrayList<Airport>();
					Iterator<ArrayList<Airport>> it = airportsByCity.iterator();
					while(it.hasNext()) {
						Iterator<Airport> it2 = it.next().iterator();
						while(it2.hasNext()) {
							Airport airport = it2.next();
							if(airport.getCity().equals(srcCityNameField.getSelectedItem()))
								airports.add(airport);
						}
					}
					srcAirpNameField.setItems(airports);
				}
			}
		});
		
		
		addGridBagItem(srcPanel, new JLabel("Country * :"), 0, 0, 1, 1, GridBagConstraints.EAST);
		addGridBagItem(srcPanel,new JLabel("City :"), 0, 1, 1, 1, GridBagConstraints.EAST);
		addGridBagItem(srcPanel, new JLabel("Airport :"), 0, 2, 1, 1, GridBagConstraints.EAST);
		
		addGridBagItem(srcPanel, srcCountryNameField, 1, 0, 2, 1, GridBagConstraints.WEST);
		addGridBagItem(srcPanel, srcCityNameField, 1, 1, 2, 1, GridBagConstraints.WEST);
		addGridBagItem(srcPanel, srcAirpNameField, 1, 2, 2, 1, GridBagConstraints.WEST);
		
		srcPanel.setBorder(BorderFactory.createTitledBorder("Departure"));
		searchPane.add(srcPanel);
		searchPane.add(Box.createVerticalStrut(10));
		
		/* Panel pour les vols d'arrivée */
		JPanel dstPanel = new JPanel(true);
		dstPanel.setLayout(new GridBagLayout());
		

		dstCountryNameField = new ComboBoxAutoComplete<Country>(Country.getCMap().values());
		
		dstCityNameField = new ComboBoxAutoComplete<City>(
				Filter.filterCitiesByCountry(((Country)(dstCountryNameField.getSelectedItem())).getName()));
		
		airportsByCity = Filter.filterAirportsByCity(((City)(dstCityNameField.getSelectedItem())).getName());
		airports = new ArrayList<Airport>();
		it = airportsByCity.iterator();
		while(it.hasNext()) {
			Iterator<Airport> it2 = it.next().iterator();
			while(it2.hasNext()) {
				Airport airport = it2.next();
				if(airport.getCity().equals(dstCityNameField.getSelectedItem()))
					airports.add(airport);
			}
		}
		dstAirpNameField = new ComboBoxAutoComplete<Airport>(airports);
		
		//Listener selection des items des combo box
		dstCountryNameField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dstAirpNameField.setItems(null);
				dstCityNameField.setItems(
						Filter.filterCitiesByCountry(((Country)(dstCountryNameField.getSelectedItem())).getName()));
			}
		});
		dstCityNameField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dstCityNameField.getSelectedItem() != null) {
					ArrayList<ArrayList<Airport>> airportsByCity = 
							Filter.filterAirportsByCity(((City)(dstCityNameField.getSelectedItem())).getName());
					ArrayList<Airport> airports = new ArrayList<Airport>();
					Iterator<ArrayList<Airport>> it = airportsByCity.iterator();
					while(it.hasNext()) {
						Iterator<Airport> it2 = it.next().iterator();
						while(it2.hasNext()) {
							Airport airport = it2.next();
							if(airport.getCity().equals(dstCityNameField.getSelectedItem()))
								airports.add(airport);
						}
					}
					dstAirpNameField.setItems(airports);
				}
			}
		});
		
		addGridBagItem(dstPanel, new JLabel("Country * :"), 0, 0, 1, 1, GridBagConstraints.EAST);
		addGridBagItem(dstPanel, new JLabel("City :"), 0, 1, 1, 1, GridBagConstraints.EAST);
		addGridBagItem(dstPanel, new JLabel("Airport :"), 0, 2, 1, 1, GridBagConstraints.EAST);
		
		addGridBagItem(dstPanel, dstCountryNameField, 1, 0, 2, 1, GridBagConstraints.WEST);
		addGridBagItem(dstPanel, dstCityNameField, 1, 1, 2, 1, GridBagConstraints.WEST);
		addGridBagItem(dstPanel, dstAirpNameField, 1, 2, 2, 1, GridBagConstraints.WEST);
		
		dstPanel.setBorder(BorderFactory.createTitledBorder("Arrival"));
		searchPane.add(dstPanel);

		JPanel optionsPanel = new JPanel(true);
		optionsPanel.setLayout(new GridBagLayout());
		
		airlineNameField = new ComboBoxAutoComplete<>(Airline.getairlinesMap().values());
		
//		allAirpCheckBox.setHorizontalTextPosition(SwingUtilities.LEFT);
		
		addGridBagItem(optionsPanel, new JLabel("Airline :"), 0, 0, 1, 1, GridBagConstraints.EAST);
		addGridBagItem(optionsPanel, airlineNameField, 1, 0, 2, 1, GridBagConstraints.WEST);
		
		JPanel  airlOpPanel = new JPanel();
		airlOpPanel.setLayout(new BoxLayout(airlOpPanel, BoxLayout.Y_AXIS));
		
		allAirpCheckBox = new JCheckBox("Display all the airports", false);
		allAirpCheckBox.setAlignmentX(RIGHT_ALIGNMENT);
//		airlOpPanel.add(allAirpCheckBox, RIGHT_ALIGNMENT);
		
		addGridBagItem(optionsPanel, allAirpCheckBox, 2, 2, 2,1, GridBagConstraints.FIRST_LINE_END);
		
		
//		addGridBagItem(optionsPanel, arcOptionPanel, 2, 2, 1, 1, GridBagConstraints.EAST);
		
		
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		
		searchPane.add(Box.createVerticalStrut(10));
//		searchPane.add(optionsPanel);
		
		JButton option = new JButton("More...");
		
		option.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new OptionDialog(null, true);
			}
		});
		airlOpPanel.add(option);
		addGridBagItem(optionsPanel, option, 2, 3, 1, 1, GridBagConstraints.EAST);
		searchPane.add(optionsPanel);
		searchPane.add(Box.createVerticalStrut(2000));
//		searchPane.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
		searchPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		
		
		mainPanel.add(searchPane, BorderLayout.WEST);
		mainPanel.add(canvas, BorderLayout.CENTER);
		mainPanel.setOpaque(true);
		setContentPane(mainPanel);
		this.setVisible(true);
		pack();
	}

	private void init3D() {
		AppSettings settings = new AppSettings(true);
		settings.setResolution(1600, 1000);
		settings.setSamples(8);
		settings.setVSync(true);

		earth3dMap = new Earth3DMap();
		earth3dMap.setSettings(settings);
		earth3dMap.setShowSettings(false);
		earth3dMap.setPauseOnLostFocus(false);

		earth3dMap.createCanvas();

		JmeCanvasContext ctx = (JmeCanvasContext) earth3dMap.getContext();
		canvas = ctx.getCanvas();
		Dimension dim = new Dimension(settings.getWidth(), settings.getHeight());
		canvas.setPreferredSize(dim);
	}

	private void createMenu() {
		// Create the menus
		final JMenuBar menubar = new JMenuBar();
		final JMenu objectsMenu = new JMenu("File");
		final JMenu helpMenu = new JMenu("Help");

		final JMenuItem createObjectItem = new JMenuItem("Create an object");
		final JMenuItem deleteObjectItem = new JMenuItem("Delete an object");
		final JMenuItem getControlsItem = new JMenuItem("Get controls");

		objectsMenu.add(createObjectItem);
		objectsMenu.add(deleteObjectItem);
		helpMenu.add(getControlsItem);
		menubar.add(objectsMenu);
		menubar.add(helpMenu);
		setJMenuBar(menubar);
	}

	private void addGridBagItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
	    GridBagConstraints gc = new GridBagConstraints();
	    gc.gridx = x;
	    gc.gridy = y;
	    gc.gridwidth = width;
	    gc.gridheight = height;
	    gc.weightx = 100.0;
	    gc.weighty = 100.0;
	    gc.insets = new Insets(5, 5, 5, 5);
	    gc.anchor = align;
	    gc.fill = GridBagConstraints.VERTICAL;
	    p.add(c, gc);
	  }
	
}
