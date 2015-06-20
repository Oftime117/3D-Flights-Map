package applicationPart;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Class classe contenant les informations d'une ville
 * 
 */
public class City implements Comparable<City>{

	private String name;
	private float timezone;
	private String DST;
	private String Tz_timezone;

	private Country country;

	private ConcurrentHashMap<String, Airport> cityAirportMap;

	private static ConcurrentHashMap<String, City> cityMap = new ConcurrentHashMap<String, City>();

	public City(String name, float timezone, String dST, String tz_timezone,
			Country country) {
		super();
		this.name = name;
		this.timezone = timezone;
		DST = dST;
		Tz_timezone = tz_timezone;
		this.country = country;

		cityAirportMap = new ConcurrentHashMap<String, Airport>();
	}

	/**
	 * @return the cityAirportMap
	 */
	public ConcurrentHashMap<String, Airport> getCityAirportMap() {
		return cityAirportMap;
	}




	/**
	 * @return the cityMap
	 */
	public static ConcurrentHashMap<String, City> getCityMap() {
		return cityMap;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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


	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	public static ArrayList<City> filterByName(String name) {
		return getCityMap().values()
				.stream()
				.distinct()
				.filter(airport -> airport.getName().toLowerCase().startsWith(name.toLowerCase()))
				.sorted((airp1, airp2) -> airp1.getName().compareToIgnoreCase(airp2.getName()))
				.collect(Collectors.toCollection(ArrayList::new));

	}
	

	@Override
	public int compareTo(City o) {
		return this.name.compareToIgnoreCase(o.getName());
	}
}
