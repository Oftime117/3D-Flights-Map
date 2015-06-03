package applicationPart;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Airline {

	private int id;
	private String name;
	private String alias;
	private String IATA_Code;
	private String ICAO_Code;
	private String callsign;
	private String country;
	private String active;

	private static HashMap<TripleKey, Airline> airlinesMap = null;

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
	public static HashMap<TripleKey, Airline> getairlinesMap() {
		return airlinesMap;
	}

	@SuppressWarnings("serial")
	public static void parse() {

		airlinesMap = new HashMap<TripleKey, Airline>() {
			/* (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {

				StringBuilder sb = new StringBuilder();
				Iterator<Airline> it = this.values().iterator();

				sb.append("List of Airlines : ");
				while(it.hasNext()) {
					Airline buff = it.next();
					sb.append("\n\t" + buff.toString() + "\n");
				}

				return sb.toString();
			}  
		};

		String path = "ressources/airlines.dat";

		File file = new File(path);
		CSVParser parser = null;
		try {
			parser = CSVParser.parse(file, Charset.forName("ISO-8859-1"), CSVFormat.MYSQL.withDelimiter(',').withQuote('"').withNullString("\\N"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (CSVRecord csvRecord : parser) {
			int id = Integer.valueOf(csvRecord.get(0));
			String name = csvRecord.get(1);
			String alias = csvRecord.get(2); 
			String IATA_Code = csvRecord.get(3);
			String ICAO_Code = csvRecord.get(4);
			String callsign =csvRecord.get(5);
			String country = csvRecord.get(6);
			String active = csvRecord.get(7);

			airlinesMap.put(new TripleKey(id, IATA_Code, ICAO_Code), new Airline(id, name, alias, IATA_Code,
					ICAO_Code, callsign, country, active));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Airline : " + name + "\n\t{" + "\n\t\tid = " + id
				+ "\n\t\talias = " + alias + "\n\t\tIATA_Code = " + IATA_Code
				+ "\n\t\tICAO_Code = " + ICAO_Code + "\n\t\tcallsign = "
				+ callsign + "\n\t\tcountry = " + country + "\n\t\tactive = "
				+ active + "\n\t}";
	}
}
