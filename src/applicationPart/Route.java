package applicationPart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Route {

	private String airlineCode;
	private int airlineID;
	private String srcAirportCode;
	private int srcAirportID;
	private String dstAirportCode;
	private int dstAirportID;
	private String codeshare;
	private int stops;
	private String equipment;


	private static ArrayList<Route> RoutesList; 



	public Route(String airlineCode, int airlineID, String srcAirportCode,
			int srcAirportID, String dstAirportCode, int dstAirportID,
			String codeshare, int stops, String equipment) {
		super();
		this.airlineCode = airlineCode;
		this.airlineID = airlineID;
		this.srcAirportCode = srcAirportCode;
		this.srcAirportID = srcAirportID;
		this.dstAirportCode = dstAirportCode;
		this.dstAirportID = dstAirportID;
		this.codeshare = codeshare;
		this.stops = stops;
		this.equipment = equipment;
	}


	/**
	 * @return the routesList
	 */
	public static ArrayList<Route> getRoutesList() {
		return RoutesList;
	}


	@SuppressWarnings("serial")
	public static void parse() {

		RoutesList = new ArrayList<Route>() {
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
			int airlineID =  Integer.valueOf(csvRecord.get(1));
			String srcAirportCode = csvRecord.get(2);
			int srcAirportID = Integer.valueOf(csvRecord.get(3));
			String dstAirportCode = csvRecord.get(4);
			int dstAirportID = Integer.valueOf(csvRecord.get(5));
			String codeshare = csvRecord.get(6);
			int stops = Integer.valueOf(csvRecord.get(7));
			String equipment = csvRecord.get(8);

			RoutesList.add(new Route(airlineCode, airlineID, srcAirportCode,
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
				+ "\n\t\tairlineCode = " + airlineCode 
				+ "\n\t\tairlineID = " + airlineID
				+ "\n\t\tsrcAirportCode = " + srcAirportCode 
				+ "\n\t\tsrcAirportID = " + srcAirportID 
				+ "\n\t\tdstAirportCode = " + dstAirportCode
				+ "\n\t\tdstAirportID = " + dstAirportID 
				+ "\n\t\tcodeshare = " + codeshare
				+ "\n\t\tstops = " + stops 
				+ "\n\t\tequipment = " + equipment
				+ "\n\t}";
	}



}
