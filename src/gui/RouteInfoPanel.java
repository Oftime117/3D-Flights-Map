package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RouteInfoPanel extends JPanel {

	private JLabel srcidLabel, dstidLabel;
	private JLabel srcNameLabel, dstNameLabel;
	private JLabel srcIATA_FAA_CodeLabel, dstIATA_FAA_CodeLabel;
	private JLabel srcICAO_CodeLabel, dstICAO_CodeLabel;
	private JLabel srcLatitudeLabel, dstLatitudeLabel;
	private JLabel srcMongitudeLabel, dstLongitudeLabel;
	private JLabel srcAltitudeLabel, dstAltitudeLabel;
	private JLabel srcTimezoneLabel, dstTimezoneLabel;
	private JLabel srcDSTLabel, dstDSTLabel;
	private JLabel srcTz_timezoneLabel, dstTz_timezoneLabel;
	private JLabel srcCountryLabel, dstCountryLabel;
	private JLabel srcCityLabel, dstCityLabel;
	
	public RouteInfoPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		JPanel srcPanel = new JPanel();
		srcPanel.setLayout(new GridBagLayout());
		
		MainWindow.addGridBagItem(srcPanel, srcidLabel, 0, 0, 1, 1, GridBagConstraints.EAST);
		MainWindow.addGridBagItem(srcPanel, srcNameLabel, 0, 1, 1, 1, GridBagConstraints.EAST);
		
		srcPanel.setBorder(BorderFactory.createTitledBorder("Source Airport"));
		
		JPanel airportsPanel = new JPanel();
		airportsPanel.setLayout(new BoxLayout(airportsPanel, BoxLayout.X_AXIS));
		
		airportsPanel.add(srcPanel);
		
		this.add(airportsPanel);
		
		this.setVisible(true);
	}

}
