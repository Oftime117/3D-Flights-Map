package applicationPart;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Country implements Comparable<Country>{
	private String name;

	private ConcurrentHashMap<String, City> countryCityMap = null;

	private static ConcurrentHashMap<String, Country> countryMap = new ConcurrentHashMap<String, Country>();

	public Country(String name) {
		this.name = name;
		countryCityMap = new ConcurrentHashMap<String, City>();
	}

	public ConcurrentHashMap<String, City> getCCMap() {
		return countryCityMap;
	}

	public static ConcurrentHashMap<String, Country> getCMap() {
		return countryMap;
	}




	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Country o) {
		return this.name.compareToIgnoreCase(o.getName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name ;
	}

	public static ArrayList<Country> filterByName(String name) {
		return getCMap().values()
				.stream()
				.distinct()
				.filter(airport -> airport.getName().toLowerCase().startsWith(name.toLowerCase()))
				.sorted((airp1, airp2) -> airp1.getName().compareToIgnoreCase(airp2.getName()))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
