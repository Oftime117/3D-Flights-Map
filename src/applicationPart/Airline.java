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

/**
 * @Class Classe contenant les informations des compagnies aériennes
 * 
 */
public class Airline implements Comparable<Airline>{

	private int id;
	private String name;
	private String alias;
	private String IATA_Code;
	private String ICAO_Code;
	private String callsign;
	/**
	 * @return the callsign
	 */
	public String getCallsign() {
		return callsign;
	}

	/**
	 * @param callsign the callsign to set
	 */
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

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

			/* Suivant la présence ou non de l'id, du IATA ou du ICAO, on crée une nouvelle entrée dans la hashMap */
			if (id != -1)
				airlinesMap.computeIfAbsent(new MultiKey(id, null, null),
						(k) -> buff);
			if (IATA_Code != null)
				airlinesMap.computeIfAbsent(new MultiKey(-1, IATA_Code, null),
						(k) -> buff);
			if (ICAO_Code != null)
				airlinesMap.computeIfAbsent(new MultiKey(-1, null, ICAO_Code),
						(k) -> buff);
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name +" (" + country + ")";
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
	
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the iATA_Code
	 */
	public String getIATA_Code() {
		return IATA_Code;
	}

	/**
	 * @param iATA_Code the iATA_Code to set
	 */
	public void setIATA_Code(String iATA_Code) {
		IATA_Code = iATA_Code;
	}

	/**
	 * @return the iCAO_Code
	 */
	public String getICAO_Code() {
		return ICAO_Code;
	}

	/**
	 * @param iCAO_Code the iCAO_Code to set
	 */
	public void setICAO_Code(String iCAO_Code) {
		ICAO_Code = iCAO_Code;
	}

}
