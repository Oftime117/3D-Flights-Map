package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import applicationPart.Airport;
import applicationPart.City;
import applicationPart.Country;
import applicationPart.Filter;
import applicationPart.Route;
import applicationPart.RoutesTableModel;

public class ResultPanel extends JPanel {

	private JComboBox<Country> countryName;
	private JComboBox<City> cityName;
	private JComboBox<Airport> airportName;
	private JTable routes;
	private Container parent;
	
	public ResultPanel(Container parent) {
		super();
		
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Results"));
		setPreferredSize(new Dimension(300, getPreferredSize().height));
		
		countryName = new JComboBox<Country>();
		cityName = new JComboBox<City>();
		airportName = new JComboBox<Airport>();
		countryName.setMaximumSize(new Dimension(200, getMinimumSize().height));
		cityName.setMaximumSize(new Dimension(200, getMinimumSize().height));
		airportName.setMaximumSize(new Dimension(200, getMinimumSize().height));
		airportName.addActionListener(new ActionListener() {
			
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cityName.getSelectedItem() != null) {
					TableRowSorter<RoutesTableModel> sorter = (TableRowSorter<RoutesTableModel>) routes.getRowSorter();
					sorter.setRowFilter(RowFilter.regexFilter(cityName.getSelectedItem().toString(), 4, 6));

					
//					City buffCity = (City) cityName.getSelectedItem();
//					cityName.removeAll();
//					buffCity.getCityAirportMap().values().forEach(airport -> airportName.addItem(airport));
//					airportName.setSelectedItem(null);

//					airportName.setSelectedItem(null);
//					countryName.setSelectedItem(null);

				}
				
			}
		});
		
		routes = new JTable();
		routes.setAutoCreateRowSorter(true);
		
		
		routes.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					Route route = ((RoutesTableModel)(routes.getModel())).getRouteAt(routes.getSelectedRow());
					System.out.println(route);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
		
		add(new JLabel("Filters"));
	    add(Box.createVerticalStrut(10));
		add(countryName);
		add(Box.createVerticalStrut(10));
		add(cityName);
		add(Box.createVerticalStrut(10));
		add(airportName);
		add(Box.createVerticalStrut(10));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(routes);
		scrollPane.setMinimumSize(new Dimension(this.getWidth(), 700));
		scrollPane.setPreferredSize(new Dimension(this.getWidth(), 1000));
		add(scrollPane);
		
		Box hideBox = Box.createHorizontalBox();
		
		JButton hide = new JButton("Hide");
		hide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((JButton)(e.getSource())).getParent().getParent().setVisible(false);
			}
		});
		
		hideBox.add(Box.createHorizontalGlue());
		hideBox.add(hide);
		
		add(hideBox);
		
		add(Box.createVerticalStrut(2000));
	}
	
	public void setTableDataDouble(ArrayList<ArrayList<Route>> routes) {
		RoutesTableModel model = new RoutesTableModel();
		model.fromDoubleAray(routes);
		updateField(model);
		this.routes.setModel(model);
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
		if(this.routes.getColumnModel().getColumnCount()>0) {
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(6));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(5));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(4));
			this.routes.getColumnModel().removeColumn(this.routes.getColumnModel().getColumn(3));
		}
	}
	
	private void updateField(RoutesTableModel model) {
		int rows = model.getRowCount();
		Country buffCountry;
		countryName.removeAll();
		cityName.removeAll();
		airportName.removeAll();
		for(int i=0; i<rows; i++) {
//			if(((DefaultComboBoxModel<Airport>)airportName.getModel()).getIndexOf(model.getRouteAt(i).getSrcAirport()) == -1 ) {
//				airportName.addItem(model.getRouteAt(i).getSrcAirport());
//			}
//			if(((DefaultComboBoxModel<Airport>)airportName.getModel()).getIndexOf(model.getRouteAt(i).getDstAirport()) == -1 ) {
//				airportName.addItem(model.getRouteAt(i).getDstAirport());
//			}
//			airportName.setSelectedItem(null);
//			if(((DefaultComboBoxModel<Country>)countryName.getModel()).getIndexOf(model.getRouteAt(i).getSrcAirport().getCountry()) == -1 ) {
//				countryName.addItem(model.getRouteAt(i).getSrcAirport().getCountry());
//			}
//			if(((DefaultComboBoxModel<Country>)countryName.getModel()).getIndexOf(model.getRouteAt(i).getDstAirport().getCountry()) == -1 ) {
//				countryName.addItem(model.getRouteAt(i).getDstAirport().getCountry());
//			}
//			countryName.setSelectedItem(null);
//			if(((DefaultComboBoxModel<City>)cityName.getModel()).getIndexOf(model.getRouteAt(i).getSrcAirport().getCity()) == -1 ) {
//				cityName.addItem(model.getRouteAt(i).getSrcAirport().getCity());
//			}
//			if(((DefaultComboBoxModel<City>)cityName.getModel()).getIndexOf(model.getRouteAt(i).getDstAirport().getCity()) == -1 ) {
//				cityName.addItem(model.getRouteAt(i).getDstAirport().getCity());
//			}
//			cityName.setSelectedItem(null);
			
			if(((DefaultComboBoxModel<Country>)countryName.getModel()).getIndexOf(model.getRouteAt(i).getSrcAirport().getCountry()) == -1 ) {
				buffCountry = model.getRouteAt(i).getSrcAirport().getCountry();
				countryName.addItem(buffCountry);
				
				buffCountry.getCCMap().values().forEach(city -> {
					cityName.addItem(city);
					city.getCityAirportMap().values().forEach(airport -> airportName.addItem(airport));
				});
				
			}
			if(((DefaultComboBoxModel<Country>)countryName.getModel()).getIndexOf(model.getRouteAt(i).getDstAirport().getCountry()) == -1 ) {
				buffCountry = model.getRouteAt(i).getDstAirport().getCountry();
				countryName.addItem(buffCountry);
				
				buffCountry.getCCMap().values().forEach(city -> {
					cityName.addItem(city);
					city.getCityAirportMap().values().forEach(airport -> airportName.addItem(airport));
				});
			}
			countryName.setSelectedItem(null);
			cityName.setSelectedItem(null);
			airportName.setSelectedItem(null);
			
		}
	}
}
