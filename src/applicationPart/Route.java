package applicationPart;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Route {

	private String codeshare;
	private int stops;
	private String equipment;

	private Airport srcAirport;
	private Airport dstAirport;
	private Airline airline;

	private static ArrayList<Route> routesList = null; 

	String airlineCode; int airlineID; String srcAirportCode;
	int srcAirportID; String dstAirportCode; int dstAirportID;

	public Route(String airlineCode, int airlineID, String srcAirportCode,
			int srcAirportID, String dstAirportCode, int dstAirportID,
			String codeshare, int stops, String equipment) {
		super();

		this.codeshare = codeshare;
		this.stops = stops;
		this.equipment = equipment;

		this.airlineCode = airlineCode;
		this.airlineID = airlineID;
		this.srcAirportCode = srcAirportCode;
		this.srcAirportID = srcAirportID;
		this.dstAirportCode = dstAirportCode;
		this.dstAirportID = dstAirportID;

		if(airlineCode.length() == 2) airline = Airline.getairlinesMap().get(new TripleKey(airlineID, airlineCode, ""));
		else if (airlineCode.length() == 3) airline = Airline.getairlinesMap().get(new TripleKey(airlineID, "", airlineCode));
		else System.err.println("Erreur sur le nombre de caractère du airlineCode\nairlineCode = " + airlineCode);

		if(srcAirportCode.length() == 3) srcAirport = Airport.getairportsMap().get(new TripleKey(srcAirportID, srcAirportCode, ""));
		else if(srcAirportCode.length() == 4) srcAirport = Airport.getairportsMap().get(new TripleKey(srcAirportID, "", srcAirportCode));
		else System.err.println("Erreur sur le nombre de caractère du srcAirportCode\nsrcAirportCode = " + srcAirportCode);


		if(dstAirportCode.length() == 3) dstAirport = Airport.getairportsMap().get(new TripleKey(dstAirportID, dstAirportCode, ""));
		else if(dstAirportCode.length() == 4) dstAirport = Airport.getairportsMap().get(new TripleKey(dstAirportID, "", dstAirportCode));
		else System.err.println("Erreur sur le nombre de caractère du dstAirportCode\ndstAirportCode = " + dstAirportCode);
	}


	/**
	 * @return the routesList
	 */
	public static ArrayList<Route> getroutesList() {
		if(routesList == null) Route.parse();
		return routesList;
	}

	@SuppressWarnings("serial")
	public static void parse() {

		routesList = new ArrayList<Route>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {

				StringBuilder sb = new StringBuilder();
				Iterator<Route> it = this.iterator();

				sb.append("List of Routes : ");
				while (it.hasNext()) {
					Route buff = it.next();
					sb.append("\n\t" + buff.toString() + "\n");
				}

				return sb.toString();
			}
		};

		String path = "ressources/routes.dat";

		File file = new File(path);
		CSVParser parser = null;
		try {
			parser = CSVParser.parse(file, Charset.forName("ISO-8859-1"), CSVFormat.MYSQL.withDelimiter(',').withQuote('"').withNullString("\\N"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (CSVRecord csvRecord : parser) {
			String airlineCode = csvRecord.get(0);
			int airlineID;
			if(csvRecord.get(1) != null)
				airlineID =  Integer.valueOf(csvRecord.get(1));
			else
				airlineID = -1;
			String srcAirportCode = csvRecord.get(2);
			int srcAirportID;
			if(csvRecord.get(3) != null)
				srcAirportID = Integer.valueOf(csvRecord.get(3));
			else
				srcAirportID = -1;
			String dstAirportCode = csvRecord.get(4);
			int dstAirportID;
			if(csvRecord.get(5) != null)
				dstAirportID = Integer.valueOf(csvRecord.get(5));
			else 
				dstAirportID = -1;
			String codeshare = csvRecord.get(6);
			int stops = Integer.valueOf(csvRecord.get(7));
			String equipment = csvRecord.get(8);

			routesList.add(new Route(airlineCode, airlineID, srcAirportCode,
					srcAirportID, dstAirportCode, dstAirportID, codeshare,
					stops, equipment));
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Route "
				+ "\n\t{"
				+ "\n\t\tairline = " + airline 
				+ "\n\t\tsrcAirport = " + srcAirport
				+ "\n\t\tdstAirport = " + dstAirport
				+ "\n\t\tcodeshare = " + codeshare
				+ "\n\t\tstops = " + stops 
				+ "\n\t\tequipment = " + equipment
				+ "\n\t}";
	}

	public String test() {

		return "airlineCode: " + airlineCode +"\nairlineID: " + airlineID
				+ "\nsrcAirportCode: " + srcAirportCode + "\nsrcAirportID: " + srcAirportID
				+ "\ndstAirportCode: " + dstAirportCode + "\ndstAirportID: " + dstAirportID + '\n';
	}
}
