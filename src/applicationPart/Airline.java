package applicationPart;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Airline implements Comparable<Airline>{

	private int id;
	private String name;
	private String alias;
	private String IATA_Code;
	private String ICAO_Code;
	private String callsign;
	private String country;
	private String active;

	private static ConcurrentHashMap<MultiKey, Airline> airlinesMap = null;

	public Airline(int id, String name, String alias, String iATA_Code,
			String iCAO_Code, String callsign, String country, String active) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		IATA_Code = iATA_Code;
		ICAO_Code = iCAO_Code;
		this.callsign = callsign;
		this.country = country;
		this.active = active;
	}

	/**
	 * @return the airlinesMap
	 */
	public static ConcurrentHashMap<MultiKey, Airline> getairlinesMap() {
		return airlinesMap;
	}




	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}





	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/*****************************************************/

	@SuppressWarnings("serial")
	public static void parse() {

		airlinesMap = new ConcurrentHashMap<MultiKey, Airline>(
				(int) (6048 / 0.75 + 1)) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {

				StringBuilder sb = new StringBuilder();
				Iterator<Airline> it = this.values().iterator();

				sb.append("List of Airlines : ");
				while (it.hasNext()) {
					Airline buff = it.next();
					sb.append("\n" + buff.toString() + "\n");
				}

				return sb.toString();
			}
		};

		String path = "ressources/airlines.dat";

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
			int id = Integer.valueOf(csvRecord.get(0));
			if (csvRecord.size() != 8) {
				System.err.println("Erreur lors du parsage d'un airline (id: "
						+ id + "), le nombre d'éléments est incorrecte: "
						+ csvRecord.size());
				System.err.println("La ligne est ignorée");
				continue;
			}
			String name = csvRecord.get(1);
			String alias = csvRecord.get(2);
			String IATA_Code = csvRecord.get(3);
			if (IATA_Code == "")
				IATA_Code = null;
			String ICAO_Code = csvRecord.get(4);
			if (ICAO_Code == "")
				ICAO_Code = null;
			String callsign = csvRecord.get(5);
			String country = csvRecord.get(6);
			String active = csvRecord.get(7);

			Airline buff = new Airline(id, name, alias, IATA_Code, ICAO_Code,
					callsign, country, active);

			if (id != -1)
				airlinesMap.computeIfAbsent(new MultiKey(id, null, null),
						(k) -> buff);
			if (IATA_Code != null)
				airlinesMap.computeIfAbsent(new MultiKey(-1, IATA_Code, null),
						(k) -> buff);
			if (ICAO_Code != null)
				airlinesMap.computeIfAbsent(new MultiKey(-1, null, ICAO_Code),
						(k) -> buff);

			// if(id != -1) airlinesMap.put(new MultiKey(id, null, null), buff);
			// if(IATA_Code != null) airlinesMap.put(new MultiKey(-1, IATA_Code,
			// null), buff);
			// if(ICAO_Code != null) airlinesMap.put(new MultiKey(-1, null,
			// ICAO_Code), buff);

			// Chercher dans toutes les clés si la ville existe, et si elle
			// existe, l'ajouter dans la map airlinesMap
			// Implique une obligation de changer les HashMap de
			// HashMap<MultiKey, ArrayList<Airlines>>
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//		return "Airline : " + name + "\n\t{" + "\n\t\tid = " + id
		//				+ "\n\t\talias = " + alias + "\n\t\tIATA_Code = " + IATA_Code
		//				+ "\n\t\tICAO_Code = " + ICAO_Code + "\n\t\tcallsign = "
		//				+ callsign + "\n\t\tcountry = " + country + "\n\t\tactive = "
		//				+ active + "\n\t}";

		return name +" (" + country + ")\t\t(id = " + id
				+ " / talias = " + alias 
				+ " / IATA_Code = " + IATA_Code
				+ " / ICAO_Code = " + ICAO_Code 
				+ " / callsign = " + callsign 
				+ " / country = " + country 
				+ " / active = " + active + ")\n";
	}

	public static ArrayList<Airline> filterByName(String name) {
		return getairlinesMap().values()
				.stream()
				.distinct()
				.filter(airport -> airport.getName().toLowerCase().startsWith(name.toLowerCase()))
				.sorted((airp1, airp2) -> airp1.getName().compareToIgnoreCase(airp2.getName()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public int compareTo(Airline o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
