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
 * @Class Classe contenant les informations d'un aéroport
 * 
 */
public class Airport implements Comparable<Airport> {

	private int id;
	private String name;
	private String IATA_FAA_Code;
	private String ICAO_Code;
	private float latitude;
	private float longitude;
	private float altitude;
	private float timezone;
	private String DST;
	private String Tz_timezone;

	private Country country;
	private City city;

//	private ArrayList<Route> routesToList;
//	private ArrayList<Route> routesFromList;

	/* Lists contenant la liste des routes de et jusqu'ici
	 * rangé par ordre alphabétiques (chaque arraylist contient les 
	 * routes allant/venant du même airport
	 */
	private ConcurrentHashMap<String, ArrayList<Route>> groupedRoutesToMap;
	private ConcurrentHashMap<String, ArrayList<Route>> groupedRoutesFromMap;
	
	private static ConcurrentHashMap<MultiKey, Airport> airportsMap = null;

	public Airport(int id, String name, String city, String country,
			String iATA_FAA_Code, String iCAO_Code, float latitude,
			float longitude, float altitude, float timezone, String dST,
			String tz_timezone) {
		super();
		this.id = id;
		this.name = name;
		IATA_FAA_Code = iATA_FAA_Code;
		ICAO_Code = iCAO_Code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.timezone = timezone;
		DST = dST;
		Tz_timezone = tz_timezone;

		/* Ajout de l'aéroport dans les villes et dans les pays*/
		/* Si le pays n'existe pas dans la liste des pays, on crée le pays, la ville et on ajoute l"aéroport à la ville crée */
		Country.getCMap().computeIfAbsent(country, (Countrykey) -> {
			Country buff = new Country(Countrykey);
			City buffCity = new City(city, timezone, dST, tz_timezone, buff);
			/* On considère que si le pays vient d'être crée 
			 * il n'y a aucune ville, ni d'aéroports */
			buffCity.getCityAirportMap().put(name, this);
			buff.getCCMap().put(country, buffCity);

			this.city = buffCity;
			this.country = buff;
			return buff;
		});

		/* Sinon si le pays existe
		 * On regarde si la ville existe
		 * Si non existante, on la crée et ajoute l'aéroport
		 * Sinon on ajoute l'aéroport (si elle n'est pas déjà dedans) */
		Country.getCMap().computeIfPresent(country, (countryName,countryValue) -> {

			countryValue.getCCMap().computeIfAbsent(city+country, (cityCountryName) -> {

				City buffCity = new City(city, timezone, dST, tz_timezone, countryValue);

				City.getCityMap().put(cityCountryName, buffCity);
				buffCity.getCityAirportMap().put(name, this);
				this.city = buffCity;
				return buffCity;
			});
			countryValue.getCCMap().computeIfPresent(city+country, (cityName, cityValue) -> {
				cityValue.getCityAirportMap().putIfAbsent(name, this);
				this.city = cityValue;
				return cityValue;
			});
			this.country = countryValue;
			return countryValue;
		});

		
		groupedRoutesFromMap = new ConcurrentHashMap<String, ArrayList<Route>>();
		groupedRoutesToMap = new ConcurrentHashMap<String, ArrayList<Route>>();
		
	}

	/**
	 * @return the iATA_FAA_Code
	 */
	public String getIATA_FAA_Code() {
		return IATA_FAA_Code;
	}

	/**
	 * @return the iCAO_Code
	 */
	public String getICAO_Code() {
		return ICAO_Code;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the airportsMap
	 */
	public static ConcurrentHashMap<MultiKey, Airport> getairportsMap() {
		if(airportsMap == null) Airport.parse();
		return airportsMap;
	}

	/**
	 * @return the groupedRoutesToMap
	 */
	public ConcurrentHashMap<String, ArrayList<Route>> getGroupedRoutesToMap() {
		return groupedRoutesToMap;
	}

	/**
	 * @return the groupedRoutesFromMap
	 */
	public ConcurrentHashMap<String, ArrayList<Route>> getGroupedRoutesFromMap() {
		return groupedRoutesFromMap;
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
	public Country getCountry() {
		return country;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}
	
	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @return the altitude
	 */
	public float getAltitude() {
		return altitude;
	}

	/**
	 * @return the timezone
	 */
	public float getTimezone() {
		return timezone;
	}

	/**
	 * @return the dST
	 */
	public String getDST() {
		return DST;
	}

	/**
	 * @return the tz_timezone
	 */
	public String getTz_timezone() {
		return Tz_timezone;
	}

	@SuppressWarnings("serial")
	public static void parse() {

		airportsMap = new ConcurrentHashMap<MultiKey, Airport>((int) (8107/0.75 + 1)) {

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
					sb.append("\n" + buff.toString() + "\n");
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
			if(csvRecord.size() != 12) {
				System.err.println("Erreur lors du parsage d'un aéroport (id: " + id +"), le nombre d'éléments est incorrecte: " + csvRecord.size());
				System.err.println("La ligne est ignorée");
				continue;
			}

			String name = csvRecord.get(1);
			String city = csvRecord.get(2);
			String country = csvRecord.get(3);
			String IATA_FAA_Code = csvRecord.get(4);
			if(IATA_FAA_Code == "") IATA_FAA_Code = null;
			String ICAO_Code = csvRecord.get(5);
			if(ICAO_Code == "") ICAO_Code = null;

			float latitude = Float.valueOf(csvRecord.get(6));
			float longitude = Float.valueOf(csvRecord.get(7));
			float altitude = Float.valueOf(csvRecord.get(8));
			float timezone = Float.valueOf(csvRecord.get(9));
			String DST = csvRecord.get(10);
			String Tz_timezone = csvRecord.get(11);

			Airport buff = new Airport(id, name, city, country,
					IATA_FAA_Code, ICAO_Code, latitude, longitude,
					altitude, timezone, DST, Tz_timezone);

			/* Suivant la présence ou non de l'id, du IATA ou du ICAO, on crée une nouvelle entrée dans la hashMap */
			if(id != -1) airportsMap.computeIfAbsent(new MultiKey(id, null, null), (k) -> buff);
			if(IATA_FAA_Code != null) airportsMap.computeIfAbsent(new MultiKey(-1, IATA_FAA_Code, null), (k) -> buff);
			if(ICAO_Code != null) airportsMap.computeIfAbsent(new MultiKey(-1, null, ICAO_Code), (k) -> buff);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return  (name != null ? name : "No Name");
	}

	public static ArrayList<Airport> filterByName(String name) {
		return getairportsMap().values()
				.stream()
				.distinct()
				.filter(airport -> airport.getName().toLowerCase().startsWith(name.toLowerCase()))
				.sorted()
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public int compareTo(Airport o) {
		return this.name.compareToIgnoreCase(o.getName());
	}
}
