package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import applicationPart.Airport;
import applicationPart.City;
import applicationPart.Country;
import applicationPart.Route;
import applicationPart.RoutesTableModel;


/**
 * @Class Panel personnalisé gérant et affichant les résultats de la recherche dans un tableau
 *
 */
public class ResultPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Country> countryName;
	private JComboBox<City> cityName;
	private JComboBox<Airport> airportName;
	private JTable routes;
	@SuppressWarnings("unused")
	private Earth3DMap earth3dMap;
	@SuppressWarnings("unused")
	private static RouteInfoPanel routeInfoPanel;
	
	@SuppressWarnings("static-access")
	public ResultPanel( Earth3DMap earth3dMap, RouteInfoPanel routeInfoPanel) {
		super();
		
		this.earth3dMap = earth3dMap;
		this.routeInfoPanel = routeInfoPanel;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Results"));
		setPreferredSize(new Dimension(300, getPreferredSize().height));
		
		countryName = new JComboBox<Country>();
		cityName = new JComboBox<City>();
		airportName = new JComboBox<Airport>();
		countryName.setPreferredSize(new Dimension(150, getMinimumSize().height));
		cityName.setPreferredSize(new Dimension(150, getMinimumSize().height));
		airportName.setPreferredSize(new Dimension(150, getMinimumSize().height));
		airportName.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(airportName.getSelectedItem() != null) {
					TableRowSorter<RoutesTableModel> sorter = (TableRowSorter<RoutesTableModel>) routes.getRowSorter();
					sorter.setRowFilter(RowFilter.regexFilter(airportName.getSelectedItem().toString(), 0, 1));
					
					countryName.setSelectedItem(null);
					cityName.setSelectedItem(null);
				}
				
			}
		});
		countryName.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(countryName.getSelectedItem() != null) {
					
//					Country buffCountry = (Country) countryName.getSelectedItem();
//					cityName.removeAll();
//					buffCountry.getCCMap().values().forEach(city -> cityName.addItem(city));
//					cityName.setSelectedItem(null);
					TableRowSorter<RoutesTableModel> sorter = (TableRowSorter<RoutesTableModel>) routes.getRowSorter();
					sorter.setRowFilter(RowFilter.regexFilter(countryName.getSelectedItem().toString(), 3, 5));

					airportName.setSelectedItem(null);
					cityName.setSelectedItem(null);
				}
			}
		});
		cityName.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cityName.getSelectedItem() != null) {
					TableRowSorter<RoutesTableModel> sorter = (TableRowSorter<RoutesTableModel>) routes.getRowSorter();
					sorter.setRowFilter(RowFilter.regexFilter(cityName.getSelectedItem().toString(), 4, 6));
					
					airportName.setSelectedItem(null);
					countryName.setSelectedItem(null);
				}
			}
		});
		
		routes = new JTable();
		routes.setAutoCreateRowSorter(true);
		routes.setPreferredSize(new Dimension(400,600));
		
		routes.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount() == 2) {
					Route route = ((RoutesTableModel)(routes.getModel())).getRouteAt(routes.getSelectedRow());
					routeInfoPanel.setRoute(route);
					earth3dMap.enqueue(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							earth3dMap.hideAllArcs();
							earth3dMap.displayArc(route.getSrcAirport().getLatitude(), route.getSrcAirport().getLongitude(), route.getDstAirport().getLatitude(), route.getDstAirport().getLongitude());
							return true;
						}
					});
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JPanel filtersPanel = new JPanel(true);
		filtersPanel.setLayout(new GridBagLayout());
		MainWindow.addGridBagItem(filtersPanel, new JLabel("Country * :"), 0, 0, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(filtersPanel,new JLabel("City :"), 0, 1, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(filtersPanel, new JLabel("Airport :"), 0, 2, 1, 1, GridBagConstraints.EAST);

		MainWindow.addGridBagItem(filtersPanel, countryName, 1, 0, 2, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(filtersPanel, cityName, 1, 1, 2, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(filtersPanel, airportName, 1, 2, 2, 1, GridBagConstraints.WEST);

		filtersPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				countryName.setSelectedItem(null);
				cityName.setSelectedItem(null);
				airportName.setSelectedItem(null);
				@SuppressWarnings("unchecked")
				TableRowSorter<RoutesTableModel> sorter = (TableRowSorter<RoutesTableModel>) routes.getRowSorter();
				sorter.allRowsChanged();
			}
		});
		MainWindow.addGridBagItem(filtersPanel, reset, 1, 3, 2, 1, GridBagConstraints.WEST);
		
		add(filtersPanel);
		add(Box.createVerticalStrut(10));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(routes);
		scrollPane.setMinimumSize(new Dimension(this.getWidth(), 440));
		scrollPane.setPreferredSize(new Dimension(this.getWidth(), 440));
		add(scrollPane);
		
		Box hideBox = Box.createHorizontalBox();
		
		JButton hide = new JButton("Hide");
		hide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((JButton)(e.getSource())).getParent().getParent().setVisible(false);
			}
		});
		
		JButton showAll = new JButton("Show All");
		showAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				earth3dMap.enqueue(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						earth3dMap.resetAllRoutes();
						return true;
					}
				});
			}
		});
		
		hideBox.add(Box.createHorizontalGlue());
		hideBox.add(showAll);
		hideBox.add(hide);
		add(hideBox);
		
		add(Box.createVerticalStrut(2000));
	}
	
	public void setTableDataDouble(ArrayList<ArrayList<Route>> routes) {
		RoutesTableModel model = new RoutesTableModel();
		model.fromDoubleAray(routes);
		updateField(model);
		this.routes.setModel(model);
		@SuppressWarnings("unchecked")
		TableRowSorter<RoutesTableModel> sorter = (TableRowSorter<RoutesTableModel>) this.routes.getRowSorter();
		sorter.allRowsChanged();
		if(this.routes.getColumnModel().getColumnCount()>0) {
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(6));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(5));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(4));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(3));
		}
	}
	
	public void setTableData(ArrayList<Route> routes) {
		RoutesTableModel model = new RoutesTableModel(routes);
		updateField(model);
		this.routes.setModel(model);
		@SuppressWarnings("unchecked")
		TableRowSorter<RoutesTableModel> sorter = (TableRowSorter<RoutesTableModel>) this.routes.getRowSorter();
		sorter.allRowsChanged();
		if(this.routes.getColumnModel().getColumnCount()>0) {
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(6));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(5));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(4));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(3));
		}
	}
	
	public void resetTable() {
//		routes.removeAll();
		routes.setModel(new RoutesTableModel());
	}
	
	private void updateField(RoutesTableModel model) {
		int rows = model.getRowCount();
		countryName.removeAll();
		cityName.removeAll();
		airportName.removeAll();
		for(int i=0; i<rows; i++) {
			if(((DefaultComboBoxModel<Airport>)airportName.getModel()).getIndexOf(model.getRouteAt(i).getSrcAirport()) == -1 ) {
				airportName.addItem(model.getRouteAt(i).getSrcAirport());
			}
			if(((DefaultComboBoxModel<Airport>)airportName.getModel()).getIndexOf(model.getRouteAt(i).getDstAirport()) == -1 ) {
				airportName.addItem(model.getRouteAt(i).getDstAirport());
			}
			airportName.setSelectedItem(null);
			if(((DefaultComboBoxModel<Country>)countryName.getModel()).getIndexOf(model.getRouteAt(i).getSrcAirport().getCountry()) == -1 ) {
				countryName.addItem(model.getRouteAt(i).getSrcAirport().getCountry());
			}
			if(((DefaultComboBoxModel<Country>)countryName.getModel()).getIndexOf(model.getRouteAt(i).getDstAirport().getCountry()) == -1 ) {
				countryName.addItem(model.getRouteAt(i).getDstAirport().getCountry());
			}
			countryName.setSelectedItem(null);
			if(((DefaultComboBoxModel<City>)cityName.getModel()).getIndexOf(model.getRouteAt(i).getSrcAirport().getCity()) == -1 ) {
				cityName.addItem(model.getRouteAt(i).getSrcAirport().getCity());
			}
			if(((DefaultComboBoxModel<City>)cityName.getModel()).getIndexOf(model.getRouteAt(i).getDstAirport().getCity()) == -1 ) {
				cityName.addItem(model.getRouteAt(i).getDstAirport().getCity());
			}
			cityName.setSelectedItem(null);

			countryName.setSelectedItem(null);
			cityName.setSelectedItem(null);
			airportName.setSelectedItem(null);
		}
	}
	
}
