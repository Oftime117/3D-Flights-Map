package applicationPart;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Airport {

	private int id;
	private String name;
	private String city;
	private String country;
	private String IATA_FAA_Code;
	private String ICAO_Code;
	private float latitude;
	private float longitude;
	private float altitude;
	private float timezone;
	private String DST;
	private String Tz_timezone;

	private static HashMap<TripleKey, Airport> airportsMap = null;

	public Airport(int id, String name, String city, String country,
			String iATA_FAA_Code, String iCAO_Code, float latitude,
			float longitude, float altitude, float timezone, String dST,
			String tz_timezone) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.country = country;
		IATA_FAA_Code = iATA_FAA_Code;
		ICAO_Code = iCAO_Code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.timezone = timezone;
		DST = dST;
		Tz_timezone = tz_timezone;
	}

	/**
	 * @return the airportsMap
	 */
	public static HashMap<TripleKey, Airport> getairportsMap() {
		if(airportsMap == null) Airport.parse();
		return airportsMap;
	}

	@SuppressWarnings("serial")
	public static void parse() {

		airportsMap = new HashMap<TripleKey, Airport>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {

				StringBuilder sb = new StringBuilder();
				Iterator<Airport> it = this.values().iterator();

				sb.append("List of Airports : ");
				while (it.hasNext()) {
					Airport buff = it.next();
					sb.append("\n\t" + buff.toString() + "\n");
				}

				return sb.toString();
			}
		};

		String path = "ressources/airports.dat";

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
			String city = csvRecord.get(2);
			String country = csvRecord.get(3);
			String IATA_FAA_Code = csvRecord.get(4);
			String ICAO_Code = csvRecord.get(5);
			float latitude = Float.valueOf(csvRecord.get(6));
			float longitude = Float.valueOf(csvRecord.get(7));
			float altitude = Float.valueOf(csvRecord.get(8));
			float timezone = Float.valueOf(csvRecord.get(9));
			String DST = csvRecord.get(10);
			String Tz_timezone = csvRecord.get(11);

			airportsMap.put(new TripleKey(id, IATA_FAA_Code, ICAO_Code), new Airport(id, name, city, country,
					IATA_FAA_Code, ICAO_Code, latitude, longitude,
					altitude, timezone, DST, Tz_timezone));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Airport : " + name + "\n\t{" + "\n\t\tid = " + id
				+ "\n\t\tcity = " + city + "\n\t\tcountry = " + country
				+ "\n\t\tIATA_FAA_Code = " + IATA_FAA_Code
				+ "\n\t\tICAO_Code = " + ICAO_Code + "\n\t\tlatitude = "
				+ latitude + "\n\t\tlongitude = " + longitude
				+ "\n\t\taltitude = " + altitude + "\n\t\ttimezone = "
				+ timezone + "\n\t\tDST = " + DST + "\n\t\tTz_timezone = "
				+ Tz_timezone + "\n\t}";
	}

}
