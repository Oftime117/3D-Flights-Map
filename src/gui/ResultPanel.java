package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;

import applicationPart.Airport;
import applicationPart.City;
import applicationPart.Country;
import applicationPart.Route;

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
		
		routes = new JTable();
		
		add(countryName);
		add(cityName);
		add(airportName);
		
		add(routes);
		
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
	
}
