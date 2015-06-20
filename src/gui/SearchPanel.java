package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import applicationPart.Airline;
import applicationPart.Airport;
import applicationPart.City;
import applicationPart.Country;
import applicationPart.Filter;
import applicationPart.Route;

/**
 * @Class Panel personnalisé gérant la recherche
 * 
 */
@SuppressWarnings("serial")
public class SearchPanel extends JPanel {

	@SuppressWarnings("unused")
	private ResultPanel resultsPane;
	
	@SuppressWarnings("unused")
	private Earth3DMap earth3dMap;
	/* Combobox personnalisé avec autocomplétion */
	private ComboBoxAutoComplete<Airport> srcAirpNameField, dstAirpNameField;
	private ComboBoxAutoComplete<City> srcCityNameField, dstCityNameField;
	private ComboBoxAutoComplete<Country>srcCountryNameField, dstCountryNameField;
	private ComboBoxAutoComplete<Airline> airlineNameField;
	/* Widgets pour les options */ 
	private JCheckBox allAirpCheckBox, activeAirlinesCheckBox;
	JSpinner airpSpinner, arcSpinner;
	
	
	
	public SearchPanel(ResultPanel resultsPane, Earth3DMap earth3dMap) {
		
		this.resultsPane = resultsPane;
		this.earth3dMap = earth3dMap;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		/* Panel pour les vols de départ */
		JPanel srcPanel = new JPanel(true);
		srcPanel.setLayout(new GridBagLayout());

		srcCountryNameField = new ComboBoxAutoComplete<Country>(Country.getCMap().values());

		srcCityNameField = new ComboBoxAutoComplete<City>(City.getCityMap().values());

		srcAirpNameField = new ComboBoxAutoComplete<Airport>(Airport.getairportsMap().values());

		//Listener selection des items des combo box
		srcCountryNameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(srcCountryNameField.getSelectedItem() instanceof Country && srcCountryNameField.getSelectedItem() != null) {

					srcCityNameField.setItems(
							Filter.filterCitiesByCountry(((Country)(srcCountryNameField.getSelectedItem())).getName()));
					srcCityNameField.setSelectedItem(null);
				}
				else if(srcCountryNameField.getSelectedItem() == null) {
					srcCityNameField.setItems(City.getCityMap().values());
					srcCityNameField.setSelectedItem(null);
				}
			}
		});
		srcCityNameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(srcCityNameField.getSelectedItem() instanceof City && srcCityNameField.getSelectedItem() != null) {
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
					srcAirpNameField.setSelectedItem(null);
				} else if(srcCityNameField.getSelectedItem() == null)
					srcAirpNameField.setItems(Airport.getairportsMap().values());
				srcAirpNameField.setSelectedItem(null);
			}
		});

		MainWindow.addGridBagItem(srcPanel, new JLabel("Country * :"), 0, 0, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(srcPanel,new JLabel("City :"), 0, 1, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(srcPanel, new JLabel("Airport :"), 0, 2, 1, 1, GridBagConstraints.EAST);

		MainWindow.addGridBagItem(srcPanel, srcCountryNameField, 1, 0, 2, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcCityNameField, 1, 1, 2, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcAirpNameField, 1, 2, 2, 1, GridBagConstraints.WEST);

		srcPanel.setBorder(BorderFactory.createTitledBorder("Departure"));
		this.add(srcPanel);
		this.add(Box.createVerticalStrut(10));

		/* Panel pour les vols d'arrivée */
		JPanel dstPanel = new JPanel(true);
		dstPanel.setLayout(new GridBagLayout());


		dstCountryNameField = new ComboBoxAutoComplete<Country>(Country.getCMap().values());

		dstCityNameField = new ComboBoxAutoComplete<City>(City.getCityMap().values());

		dstAirpNameField = new ComboBoxAutoComplete<Airport>(Airport.getairportsMap().values());

		//Listener selection des items des combo box
		dstCountryNameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(dstCountryNameField.getSelectedItem() instanceof Country && dstCountryNameField.getSelectedItem() != null)

					if(dstCountryNameField.getSelectedItem() instanceof Country && dstCountryNameField.getSelectedItem() != null) {
						dstCityNameField.setItems(
								Filter.filterCitiesByCountry(((Country)(dstCountryNameField.getSelectedItem())).getName()));
						dstCityNameField.setSelectedItem(null);
					}
					else if(dstCountryNameField.getSelectedItem() == null) {
						dstCityNameField.setItems(City.getCityMap().values());
						dstCityNameField.setSelectedItem(null);
					}

			}
		});
		dstCityNameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(dstCityNameField.getSelectedItem() instanceof City && dstCityNameField.getSelectedItem() != null) {
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
					dstAirpNameField.setSelectedItem(null);
				} else if(dstCityNameField.getSelectedItem() == null)
					dstAirpNameField.setItems(Airport.getairportsMap().values());
				dstAirpNameField.setSelectedItem(null);
			}
		});

		MainWindow.addGridBagItem(dstPanel, new JLabel("Country * :"), 0, 0, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(dstPanel, new JLabel("City :"), 0, 1, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(dstPanel, new JLabel("Airport :"), 0, 2, 1, 1, GridBagConstraints.EAST);

		MainWindow.addGridBagItem(dstPanel, dstCountryNameField, 1, 0, 2, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstCityNameField, 1, 1, 2, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstAirpNameField, 1, 2, 2, 1, GridBagConstraints.WEST);

		dstPanel.setBorder(BorderFactory.createTitledBorder("Arrival"));
		this.add(dstPanel);

		JPanel optionsPanel = new JPanel(true);
		optionsPanel.setLayout(new GridBagLayout());

		airlineNameField = new ComboBoxAutoComplete<>(Airline.getairlinesMap().values());

		MainWindow.addGridBagItem(optionsPanel, new JLabel("Airline :"), 0, 0, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(optionsPanel, airlineNameField, 1, 0, 2, 1, GridBagConstraints.WEST);

		JPanel  airlOpPanel = new JPanel();
		airlOpPanel.setLayout(new BoxLayout(airlOpPanel, BoxLayout.Y_AXIS));

		activeAirlinesCheckBox = new JCheckBox("Display only active airlines", true);
		activeAirlinesCheckBox.setAlignmentX(RIGHT_ALIGNMENT);

		
		allAirpCheckBox = new JCheckBox("Display all the airports", false);
		allAirpCheckBox.setAlignmentX(RIGHT_ALIGNMENT);

		allAirpCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(allAirpCheckBox.isSelected()) {
					earth3dMap.enqueue(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							earth3dMap.displayAllGeneralAirports();
							return true;
						}

					});
				}
				else {
					earth3dMap.enqueue(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							earth3dMap.hideAllGeneralAirports();
							return true;
						}
					});
				}
			}
		});
		
		MainWindow.addGridBagItem(optionsPanel, activeAirlinesCheckBox, 2, 2, 1, 1, GridBagConstraints.FIRST_LINE_END);
		MainWindow.addGridBagItem(optionsPanel, allAirpCheckBox, 2, 3, 2, 1, GridBagConstraints.FIRST_LINE_END);

		

		optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));

		this.add(Box.createVerticalStrut(10));

		JButton option = new JButton("More...");

		option.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new OptionDialog(null, true, earth3dMap);
			}
		});
		airlOpPanel.add(option);
		MainWindow.addGridBagItem(optionsPanel, option, 2, 4, 1, 1, GridBagConstraints.EAST);
		this.add(optionsPanel);
		
		Box searchRoutesPanel = Box.createHorizontalBox();
		
		
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String airlineName = null;
				resultsPane.resetTable();
				
				
				ArrayList<ArrayList<Route>> buffRoute = new ArrayList<ArrayList<Route>>();
				
				if(airlineNameField.getSelectedItem() != null && airlineNameField.getSelectedItem() instanceof Airline)
					airlineName = ((Airline)(airlineNameField.getSelectedItem())).getName();
				if(srcAirpNameField.getSelectedItem() != null && dstAirpNameField.getSelectedItem() != null) {
					String srcAirpName = ((Airport)(srcAirpNameField.getSelectedItem())).getName();
					String dstAirpName = ((Airport)(dstAirpNameField.getSelectedItem())).getName();
					buffRoute.addAll(Filter.filterByDirectAirpToAirp2(srcAirpName, dstAirpName, airlineName, activeAirlinesCheckBox.isSelected()));
					
				} else if(srcAirpNameField.getSelectedItem() != null && dstAirpNameField.getSelectedItem() == null) {
					String srcAirpName = ((Airport)(srcAirpNameField.getSelectedItem())).getName();
					buffRoute.add(Filter.filterByPlaceAndAirline2("from", srcAirpName, "airport", airlineName, activeAirlinesCheckBox.isSelected()));
					
				} else if(srcAirpNameField.getSelectedItem() == null && dstAirpNameField.getSelectedItem() != null) {
					String dstAirpName = ((Airport)(dstAirpNameField.getSelectedItem())).getName();
					buffRoute.add(Filter.filterByPlaceAndAirline2("to", dstAirpName, "airport", airlineName, activeAirlinesCheckBox.isSelected()));
					
				} else if(srcCityNameField.getSelectedItem() != null && dstCityNameField.getSelectedItem() != null) {
					String srcCityName = ((City)(srcCityNameField.getSelectedItem())).getName();
					String dstCityName = ((City)(dstCityNameField.getSelectedItem())).getName();
					buffRoute.addAll(Filter.filterByDirectCityToCity2(srcCityName, dstCityName, airlineName, activeAirlinesCheckBox.isSelected()));
					
				} else if(srcCityNameField.getSelectedItem() != null && dstCityNameField.getSelectedItem() == null) {
					String srcCityName = ((City)(srcCityNameField.getSelectedItem())).getName();
					buffRoute.add(Filter.filterByPlaceAndAirline2("from", srcCityName, "city", airlineName, activeAirlinesCheckBox.isSelected()));
					
				} else if(srcCityNameField.getSelectedItem() == null && dstCityNameField.getSelectedItem() != null) {
					String dstCityName = ((City)(dstCityNameField.getSelectedItem())).getName();
					buffRoute.add(Filter.filterByPlaceAndAirline2("to", dstCityName, "city", airlineName, activeAirlinesCheckBox.isSelected()));
					
				}  else if(srcCountryNameField.getSelectedItem() != null && dstCountryNameField.getSelectedItem() != null) {
					String srcCountryName = ((Country)(srcCountryNameField.getSelectedItem())).getName();
					String dstCountryName = ((Country)(dstCountryNameField.getSelectedItem())).getName();
					buffRoute.addAll(Filter.filterByDirectCountryToCountry2(srcCountryName, dstCountryName, airlineName, activeAirlinesCheckBox.isSelected()));
					
				} else if(srcCountryNameField.getSelectedItem() != null && dstCountryNameField.getSelectedItem() == null) {
					String srcCountryName = ((Country)(srcCountryNameField.getSelectedItem())).getName();
					buffRoute.add(Filter.filterByPlaceAndAirline2("from", srcCountryName, "country", airlineName, activeAirlinesCheckBox.isSelected()));
				
				} else if(srcCountryNameField.getSelectedItem() == null && dstCountryNameField.getSelectedItem() != null) {
					String dstCountryName = ((Country)(dstCountryNameField.getSelectedItem())).getName();
					buffRoute.add(Filter.filterByPlaceAndAirline2("to", dstCountryName, "country", airlineName, activeAirlinesCheckBox.isSelected()));
				}
				resultsPane.setTableDataDouble(buffRoute);
				
				earth3dMap.enqueue(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						earth3dMap.resetOverlay();
						earth3dMap.displayRoutes(buffRoute);
						return true;
					}
				});
			}
		});
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				srcCountryNameField.setSelectedItem(null);
				srcCityNameField.setItems(City.getCityMap().values());
				srcCityNameField.setSelectedItem(null);
				srcAirpNameField.setItems(Airport.getairportsMap().values());
				srcAirpNameField.setSelectedItem(null);
				dstCountryNameField.setSelectedItem(null);
				dstCityNameField.setItems(City.getCityMap().values());
				dstCityNameField.setSelectedItem(null);
				dstAirpNameField.setItems(Airport.getairportsMap().values());
				dstAirpNameField.setSelectedItem(null);
				airlineNameField.setItems(Airline.getairlinesMap().values());
				airlineNameField.setSelectedItem(null);
				
				resultsPane.resetTable();
				earth3dMap.enqueue(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						earth3dMap.resetOverlay();
						return true;
					}
				});
			}
		});
		
		searchRoutesPanel.add(Box.createHorizontalGlue());
		searchRoutesPanel.add(reset);
		searchRoutesPanel.add(search);
		
		this.add(searchRoutesPanel);

		this.add(Box.createVerticalStrut(2000));
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
	}

}
