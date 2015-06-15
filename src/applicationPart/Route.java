package applicationPart;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Route implements Comparable<Route>{

	private String codeshare;
	private int stops;
	private String equipment;

	private Airport srcAirport;
	private Airport dstAirport;
	private Airline airline;


	private static ArrayList<Route> routesList = null;

	private ArrayList<ArrayList<Route>> pathList = null;

	public Route(String airlineCode, int airlineID, String srcAirportCode,
			int srcAirportID, String dstAirportCode, int dstAirportID,
			String codeshare, int stops, String equipment) {

		this.codeshare = codeshare;
		this.stops = stops;
		this.equipment = equipment;

		if (airlineID != -1)
			airline = Airline.getairlinesMap().get(
					new MultiKey(airlineID, null, null));
		else {
			if (airlineCode.length() == 2)
				airline = Airline.getairlinesMap().get(
						new MultiKey(-1, airlineCode, null));
			else if (airlineCode.length() == 3)
				airline = Airline.getairlinesMap().get(
						new MultiKey(-1, null, airlineCode));
			else
				System.err.println("Erreur sur une Route, elle n'a ni d'id ni de code valide (airline)");
		}

		if (srcAirportID != -1)
			srcAirport = Airport.getairportsMap().get(
					new MultiKey(srcAirportID, null, null));
		else {
			if (srcAirportCode.length() == 3)
				srcAirport = Airport.getairportsMap().get(
						new MultiKey(-1, srcAirportCode, null));
			else if (srcAirportCode.length() == 4)
				srcAirport = Airport.getairportsMap().get(
						new MultiKey(-1, null, srcAirportCode));
			else
				System.err.println("Erreur sur une Route, elle n'a ni d'id ni de code valide (srcAirport)");

		}

		if (dstAirportID != -1)
			dstAirport = Airport.getairportsMap().get(
					new MultiKey(dstAirportID, null, null));
		else {
			if (dstAirportCode.length() == 3)
				dstAirport = Airport.getairportsMap().get(
						new MultiKey(-1, dstAirportCode, null));
			else if (dstAirportCode.length() == 4)
				dstAirport = Airport.getairportsMap().get(
						new MultiKey(-1, null, dstAirportCode));
			else
				System.err
				.println("Erreur sur le nombre de caractère du dstAirportCode\ndstAirportCode = "
						+ dstAirportCode);

		}
	}

	public Route(Airline airline, Airport srcAirport, Airport dstAirport, String codeshare, int stops, String equipment) {

		this.airline = airline;
		this.srcAirport = srcAirport;
		this.dstAirport = dstAirport;
		this.codeshare = codeshare;
		this.stops = stops;
		this.equipment = equipment;
	}


	
	
	/**
	 * @return the pathList
	 */
	public ArrayList<ArrayList<Route>> getPathList() {
		return pathList;
	}

	/**
	 * @return the routesList
	 */
	public static ArrayList<Route> getRoutesList() {
		return routesList;
	}

	/**
	 * @return the srcAirport
	 */
	public Airport getSrcAirport() {
		return srcAirport;
	}

	/**
	 * @return the dstAirport
	 */
	public Airport getDstAirport() {
		return dstAirport;
	}

	/**
	 * @return the airline
	 */
	public Airline getAirline() {
		return airline;
	}

	@SuppressWarnings("serial")
	public static void parse() {

		routesList = new ArrayList<Route>((int) (67663 / 0.75 + 1)) {
//			/*
//			 * (non-Javadoc)
//			 * 
//			 * @see java.lang.Object#toString()
//			 */
//			@Override
//			public String toString() {
//
//				StringBuilder sb = new StringBuilder();
//				//				Iterator<Route> it = this.values().iterator();
//
//				this.stream().distinct().forEach((route) -> {
//					sb.append("Liste des routes de " + route. + ":\n");
//					entry.getValue().stream().distinct().forEach((route) -> {
//						sb.append("\t" + route + "\n");
//					});
//
//				});
//				this.values().forEach((list) -> sb.append("\n" + list + "\n"));
//								sb.append("List of Routes : ");
//								while (it.hasNext()) {
//									Route buff = it.next();
//									sb.append("\n" + buff.toString() + "\n");
//								}
//				return sb.toString();
//			}
		};

		String path = "ressources/routes.dat";

		File file = new File(path);
		CSVParser parser = null;
		try {
			parser = CSVParser.parse(file, Charset.forName("ISO-8859-1"),
					CSVFormat.MYSQL.withDelimiter(',').withQuote('"')
					.withNullString("\\N"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (CSVRecord csvRecord : parser) {
			String airlineCode = csvRecord.get(0);
			int airlineID;
			if (csvRecord.get(1) != null)
				airlineID = Integer.valueOf(csvRecord.get(1));
			else
				airlineID = -1;
			String srcAirportCode = csvRecord.get(2);
			int srcAirportID;
			if (csvRecord.get(3) != null)
				srcAirportID = Integer.valueOf(csvRecord.get(3));
			else
				srcAirportID = -1;
			String dstAirportCode = csvRecord.get(4);
			int dstAirportID;
			if (csvRecord.get(5) != null)
				dstAirportID = Integer.valueOf(csvRecord.get(5));
			else
				dstAirportID = -1;
			if (csvRecord.size() != 9) {
				System.err
				.println("Erreur lors du parsage d'une route (idAirline: "
						+ airlineID
						+ "srcAirportID: "
						+ srcAirportID
						+ ", dstAirportID"
						+ dstAirportID
						+ "), le nombre d'éléments est incorrecte: "
						+ csvRecord.size());
				System.err.println("La ligne est ignorée");
				continue;
			}




			final Airline airline;
			final Airport dstAirport , srcAirport;

			String codeshare = csvRecord.get(6);
			int stops = Integer.valueOf(csvRecord.get(7));
			String equipment = csvRecord.get(8);

			if (airlineID != -1)
				airline = Airline.getairlinesMap().get(
						new MultiKey(airlineID, null, null));
			else {
				if (airlineCode.length() == 2)
					airline = Airline.getairlinesMap().get(
							new MultiKey(-1, airlineCode, null));
				else if (airlineCode.length() == 3)
					airline = Airline.getairlinesMap().get(new MultiKey(-1, null, airlineCode));
				else {
					System.err.println("Erreur sur une Route, elle n'a ni d'id ni de code valide (airline)");
					airline = null;
				}
			}

			if (srcAirportID != -1)
				srcAirport = Airport.getairportsMap().get(
						new MultiKey(srcAirportID, null, null));
			else {
				if (srcAirportCode.length() == 3)
					srcAirport = Airport.getairportsMap().get(new MultiKey(-1, srcAirportCode, null));
				else if (srcAirportCode.length() == 4)
					srcAirport = Airport.getairportsMap().get(new MultiKey(-1, null, srcAirportCode));
				else {
					System.err.println("Erreur sur une Route, elle n'a ni d'id ni de code valide (srcAirport)");
					srcAirport = null;
				}

			}

			if (dstAirportID != -1)
				dstAirport = Airport.getairportsMap().get(
						new MultiKey(dstAirportID, null, null));
			else {
				if (dstAirportCode.length() == 3)
					dstAirport = Airport.getairportsMap().get(new MultiKey(-1, dstAirportCode, null));
				else if (dstAirportCode.length() == 4)
					dstAirport = Airport.getairportsMap().get(new MultiKey(-1, null, dstAirportCode));
				else {
					System.err.println("Erreur sur une Route, elle n'a ni d'id ni de code valide (srcAirport)");
					dstAirport = null;
				}
			}

			if(airline == null || srcAirport == null || dstAirport == null) continue;

			Route buffRoute = new Route(airline, srcAirport, dstAirport, codeshare, stops, equipment);

			dstAirport.getRoutesFromList().add(buffRoute);
			srcAirport.getRoutesToList().add(buffRoute);
			
			srcAirport.getGroupedRoutesFromMap().computeIfAbsent(dstAirport.getName()
					+srcAirport.getCity().getName()
					+srcAirport.getCountry().getName(), key -> {
				ArrayList<Route> buff = new ArrayList<Route>();
				buff.add(buffRoute);
				return buff;
			});

			srcAirport.getGroupedRoutesFromMap().computeIfPresent(dstAirport.getName()
					+srcAirport.getCity().getName()
					+srcAirport.getCountry().getName(), (key, value) -> {
				if(!value.contains(buffRoute)) value.add(buffRoute);
				return value;
			});
			
			dstAirport.getGroupedRoutesToMap().computeIfAbsent(srcAirport.getName()
					+dstAirport.getCity().getName()
					+dstAirport.getCountry().getName(), key -> {
				ArrayList<Route> buff = new ArrayList<Route>();
				buff.add(buffRoute);
				return buff;
			});

			dstAirport.getGroupedRoutesToMap().computeIfPresent(srcAirport.getName()
					+dstAirport.getCity().getName()
					+dstAirport.getCountry().getName(), (key, value) -> {
					if(!value.contains(buffRoute)) value.add(buffRoute);
				return value;
			});
			
			routesList.add(buffRoute);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//		return "Route " + "\n{" + "\n\tairline = " + airline
		//				+ "\n\n\tsrcAirport = " + srcAirport + "\n\n\tdstAirport = "
		//				+ dstAirport + "\n\n\tcodeshare = " + codeshare
		//				+ "\n\tstops = " + stops + "\n\tequipment = " + equipment
		//				+ "\n}\n";
		return "Aéroport de départ : " + srcAirport
				+ "\nAéroport d'arrivée : " + dstAirport
				+ "\nAirline : " + airline.getName()
				+ "\t(codeshare = " + codeshare
				+ " / stops = " + stops 
				+ " / equipment = " + equipment + ")\n";
	}

	@Override
	public int compareTo(Route o) {
		return this.srcAirport.getName().compareToIgnoreCase(o.getSrcAirport().getName());
	}

}
