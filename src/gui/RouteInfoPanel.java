package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import applicationPart.Route;

/**
 * @Class Panel personnalisé affichant les détails d'une route sélectionnée
 * 
 */
public class RouteInfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel srcidLabel = new JLabel("Id: "), dstidLabel = new JLabel("Id: ");
	private JLabel srcNameLabel = new JLabel("Name: "), dstNameLabel = new JLabel("Name: ");
	private JLabel srcIATA_FAA_CodeLabel = new JLabel("IATA_FAA: "), dstIATA_FAA_CodeLabel = new JLabel("IATA_FAA: ");
	private JLabel srcICAO_CodeLabel = new JLabel("ICAO: "), dstICAO_CodeLabel = new JLabel("ICAO: ");
	private JLabel srcLatitudeLabel = new JLabel("Latitude: "), dstLatitudeLabel = new JLabel("Latitude: ");
	private JLabel srcLongitudeLabel = new JLabel("Longitude: "), dstLongitudeLabel = new JLabel("Longitude: ");
	private JLabel srcAltitudeLabel = new JLabel("Altitude: "), dstAltitudeLabel = new JLabel("Altitude: ");
	private JLabel srcTimezoneLabel = new JLabel("Timezone: "), dstTimezoneLabel = new JLabel("Timezone: ");
	private JLabel srcDSTLabel = new JLabel("DST: "), dstDSTLabel = new JLabel("DST: ");
	private JLabel srcTz_timezoneLabel = new JLabel("TZ_Timezone: "), dstTz_timezoneLabel = new JLabel("TZ_Timezone: ");
	private JLabel srcCountryLabel = new JLabel("Country: "), dstCountryLabel = new JLabel("Country: ");
	private JLabel srcCityLabel = new JLabel("City: "), dstCityLabel = new JLabel("City: ");

	private JLabel airlid = new JLabel("Id: ");
	private JLabel airlName = new JLabel("Name: ");
	private JLabel airlAlias = new JLabel("Alias: ");
	private JLabel airlIATA_Code = new JLabel("IATA: ");
	private JLabel airlICAO_Code = new JLabel("ICAO: ");
	private JLabel airlCallsign = new JLabel("Callsign: ");
	private JLabel airlCountry = new JLabel("Country: ");
	private JLabel airlActive = new JLabel("Active: ");

	public RouteInfoPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		JPanel srcPanel = new JPanel();
		srcPanel.setLayout(new GridBagLayout());

		MainWindow.addGridBagItem(srcPanel, srcidLabel, 0, 0, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcNameLabel, 1, 0, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcIATA_FAA_CodeLabel, 0, 1, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcICAO_CodeLabel, 1, 1, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcLatitudeLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcLongitudeLabel, 1, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcAltitudeLabel, 2, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcTimezoneLabel, 0, 3, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcDSTLabel, 1, 3, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcTz_timezoneLabel, 2, 3, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcCityLabel, 0, 4, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(srcPanel, srcCountryLabel, 1, 4, 1, 1, GridBagConstraints.WEST);

		srcPanel.setBorder(BorderFactory.createTitledBorder("Source Airport"));


		JPanel dstPanel = new JPanel();
		dstPanel.setLayout(new GridBagLayout());

		MainWindow.addGridBagItem(dstPanel, dstidLabel, 0, 0, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstNameLabel, 1, 0, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstIATA_FAA_CodeLabel, 0, 1, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstICAO_CodeLabel, 1, 1, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstLatitudeLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstLongitudeLabel, 1, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstAltitudeLabel, 2, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstTimezoneLabel, 0, 3, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstDSTLabel, 1, 3, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstTz_timezoneLabel, 2, 3, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstCityLabel, 0, 4, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(dstPanel, dstCountryLabel, 1, 4, 1, 1, GridBagConstraints.WEST);

		dstPanel.setBorder(BorderFactory.createTitledBorder("Destination Airport"));

		JPanel airportsPanel = new JPanel();
		airportsPanel.setLayout(new BoxLayout(airportsPanel, BoxLayout.X_AXIS));

		airportsPanel.add(srcPanel);
		airportsPanel.add(dstPanel);
		this.add(airportsPanel);


		JPanel airlinePanel = new JPanel();
		airlinePanel.setLayout(new GridBagLayout());

		MainWindow.addGridBagItem(airlinePanel, airlid, 0, 0, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(airlinePanel, airlName, 0, 1, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(airlinePanel, airlAlias, 1, 1, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(airlinePanel, airlIATA_Code, 0, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(airlinePanel, airlICAO_Code, 1, 2, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(airlinePanel, airlCountry, 0, 3, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(airlinePanel, airlCallsign, 0, 4, 1, 1, GridBagConstraints.WEST);
		MainWindow.addGridBagItem(airlinePanel, airlActive, 1, 4, 1, 1, GridBagConstraints.WEST);

		airlinePanel.setBorder(BorderFactory.createTitledBorder("Airline"));

		JPanel endPanel = new  JPanel();
		endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.X_AXIS));

		endPanel.add(airlinePanel);

		JButton hide = new JButton("Hide this");
		hide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RouteInfoPanel.this.setVisible(false);
			}
		});

		endPanel.add(hide);
		this.add(endPanel);
		this.setVisible(true);
	}

	public void setRoute(Route route) {
		srcidLabel.setText("ID : "+route.getSrcAirport().getId());
		srcNameLabel.setText("Name :"+route.getSrcAirport().getName());
		srcIATA_FAA_CodeLabel.setText("IATA_FAA : "+route.getSrcAirport().getIATA_FAA_Code());
		srcICAO_CodeLabel.setText("ICAO : "+route.getSrcAirport().getICAO_Code());
		srcLatitudeLabel.setText("Latitude : "+route.getSrcAirport().getLatitude());
		srcLongitudeLabel.setText("Longitude: "+route.getSrcAirport().getLongitude());
		srcAltitudeLabel.setText("Altitude : "+route.getSrcAirport().getAltitude());
		srcTimezoneLabel.setText("Timezone : "+route.getSrcAirport().getTimezone());
		srcDSTLabel.setText("DST : "+route.getSrcAirport().getDST());
		srcTz_timezoneLabel.setText("TZ_Timezone : "+route.getSrcAirport().getTz_timezone());
		srcCityLabel.setText("City : "+route.getSrcAirport().getCity().getName());
		srcCountryLabel.setText("Contry : "+route.getSrcAirport().getCountry().getName());
		dstidLabel.setText("ID : "+route.getDstAirport().getId());
		dstNameLabel.setText("Name :"+route.getDstAirport().getName());
		dstIATA_FAA_CodeLabel.setText("IATA_FAA : "+route.getDstAirport().getIATA_FAA_Code());
		dstICAO_CodeLabel.setText("ICAO : "+route.getDstAirport().getICAO_Code());
		dstLatitudeLabel.setText("Latitude : "+route.getDstAirport().getLatitude());
		dstLongitudeLabel.setText("Longitude: "+route.getDstAirport().getLongitude());
		dstAltitudeLabel.setText("Altitude : "+route.getDstAirport().getAltitude());
		dstTimezoneLabel.setText("Timezone : "+route.getDstAirport().getTimezone());
		dstDSTLabel.setText("DST : "+route.getDstAirport().getDST());
		dstTz_timezoneLabel.setText("TZ_Timezone : "+route.getDstAirport().getTz_timezone());
		dstCityLabel.setText("City : "+route.getDstAirport().getCity().getName());
		dstCountryLabel.setText("Contry : "+route.getDstAirport().getCountry().getName());
		airlid.setText("ID : "+route.getAirline().getId());
		airlName.setText("Name: "+route.getAirline().getId());
		airlAlias.setText("Alias: "+route.getAirline().getAlias());
		airlIATA_Code.setText("IATA: "+route.getAirline().getIATA_Code());
		airlICAO_Code.setText("ICAO: "+route.getAirline().getICAO_Code());
		airlCallsign.setText("Callsign: "+route.getAirline().getCallsign());
		airlCountry.setText("Country: "+route.getAirline().getCountry());
		airlActive.setText("Active: "+route.getAirline().getActive());
	}
}
