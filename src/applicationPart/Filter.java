package applicationPart;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @Class Classe contenant l'implémentation de tous les filtres
 * 
 */
public final class Filter {

	private Filter() {}

	public static ArrayList<?> filterByName(String name, String type) {

		ArrayList<?> buff = null;

		switch (type) {
		case "airport":
		case "Airport":
		case "airp":
			buff = Airport.filterByName(name);
			break;
		case "airline":
		case "Airline":
		case "airl":
			buff = Airline.filterByName(name);
			break;
		case "city":
		case "City":
		case "ville":
			buff = City.filterByName(name);
			break;
		case "country":
		case "Country":
		case "pays":
			buff = Country.filterByName(name);
			break;
		default:
			System.err.println("Erreur sur le type recherché (" + type + "1)");
		}
		return buff;
	}

	public static ArrayList<ArrayList<Airport>> filterAirportsByCity(String name) {

		ArrayList<ArrayList<Airport>> buff = new ArrayList<ArrayList<Airport>>();

		City.getCityMap()
		.values()
		.stream()
		.filter(city -> city.getName().equals(name))
		.forEachOrdered(city -> {
			buff.add(city.getCityAirportMap()
			.values()
			.stream()
			.collect(Collectors.toCollection(ArrayList::new)));
		});

		return buff;
	}

	public static ArrayList<ArrayList<Airport>> filterAirportByCountry(String name) {
		
		ArrayList<ArrayList<Airport>> buff = new ArrayList<ArrayList<Airport>>();
		
		Country.getCMap()
		.get(name)
		.getCCMap()
		.values()
		.forEach(city -> {
			buff.add(city.getCityAirportMap()
					.values()
					.stream()
					.collect(Collectors.toCollection(ArrayList::new)));
		});
		
		return buff;
	}
	
	public static ArrayList<City> filterCitiesByCountry(String name) {

		return Country
				.getCMap()
				.get(name)
				.getCCMap()
				.values()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public static ArrayList<Route> filterRoutesByAirline(String name, boolean activeOnly) {
		ArrayList<Route> routes;
		if(activeOnly)
			routes = Route.getRoutesList().stream()
			.distinct()
			.filter(route -> route.getAirline().getActive().equals("Y"))
					.sorted()
					.collect(Collectors.toCollection(ArrayList::new));
			else
				routes =  Route.getRoutesList().stream()
				.distinct()
				.filter(route -> route.getAirline() != null)
				.sorted()
				.collect(Collectors.toCollection(ArrayList::new));
			if(name != null)
				routes = routes.stream().filter(route -> route.getAirline()
						.getName()
						.toLowerCase()
						.startsWith(name.toLowerCase()))
						.collect(Collectors.toCollection(ArrayList::new));
			return routes;
	}

	public static ArrayList<ArrayList<Route>> filterRoutesFromAirport2(String name) {
		ArrayList<ArrayList<Route>> buff = new ArrayList<ArrayList<Route>>();

		Airport.getairportsMap().values()
		.stream()
		.distinct()
		.filter(airport -> airport.getName().toLowerCase().equals(name.toLowerCase()))
		.sorted()
		.forEach(airport -> buff.addAll(airport.getGroupedRoutesFromMap()
				.values()
				.stream()
				//.distinct()
				.sorted((array1, array2) -> array1.get(0).getDstAirport().getName().compareToIgnoreCase(array2.get(0).getDstAirport().getName()))
				.collect(Collectors.toCollection(ArrayList::new))));

		return buff;
	}

	public static ArrayList<ArrayList<Route>> filterRoutesToAirport2(String name) {

		ArrayList<ArrayList<Route>> buff = new ArrayList<ArrayList<Route>>();

		Airport.getairportsMap().values()
		.stream()
		.distinct()
		.filter(airport -> airport.getName().toLowerCase().equals(name.toLowerCase()))
		.sorted()
		.forEach(airport -> buff.addAll(airport.getGroupedRoutesToMap()
				.values()
				.stream()
				.distinct()
				.filter(array -> !array.isEmpty())
				.sorted((array1, array2) -> array1.get(0).getSrcAirport().getName().compareToIgnoreCase(array2.get(0).getSrcAirport().getName()))
				.collect(Collectors.toCollection(ArrayList::new))));

		return buff;
	}

	public static ArrayList<ArrayList<Route>> filterRoutesToOrFromAirport2(String name, String type) {
		switch (type) {
		case "from":
		case "départ de":
			return filterRoutesFromAirport2(name);

		case "to":
		case "arrivée de":
			return filterRoutesToAirport2(name);
		default:
			System.err.println("Erreur sur le type entré" + "(" + type + "1)");
			return null;
		}
	}

	public static ArrayList<ArrayList<Route>> filterRoutesToCity2(String name) {

		ArrayList<ArrayList<Route>> routeList = new ArrayList<ArrayList<Route>>();

		City.getCityMap()
		.values()
		.stream()
		.distinct()
		.filter(city -> city.getName().equals(name))
		.forEach(city -> city.getCityAirportMap().values()
				.forEach(airport -> routeList.addAll(airport.getGroupedRoutesToMap()
						.values()
						.stream()
						.distinct()
						.sorted((array1, array2) -> array1.get(0).getSrcAirport().getName().compareToIgnoreCase(array2.get(0).getSrcAirport().getName()))
						.collect(Collectors.toCollection(ArrayList::new)))));


		return routeList;
	}

	public static ArrayList<ArrayList<Route>> filterRoutesFromCity2(String name) {

		ArrayList<ArrayList<Route>> routeList = new ArrayList<ArrayList<Route>>();

		City.getCityMap()
		.values()
		.stream()
		.distinct()
		.filter(city -> city.getName().equals(name))
		.forEach(city -> city.getCityAirportMap().values()
				.forEach(airport -> routeList.addAll(airport.getGroupedRoutesFromMap()
						.values()
						.stream()
						.distinct()
						.sorted((array1, array2) -> array1.get(0).getSrcAirport().getName().compareToIgnoreCase(array2.get(0).getSrcAirport().getName()))
						.collect(Collectors.toCollection(ArrayList::new)))));

		return routeList;
	}

	public static ArrayList<ArrayList<Route>> filterRoutesToOrFromCity2(String name, String type) {
		switch (type) {
		case "from":
		case "départ de":
			return filterRoutesFromCity2(name);

		case "to":
		case "arrivée de":
			return filterRoutesToCity2(name);
		default:
			System.err.println("Erreur sur le type entré" + "(" + type + ")");
			return null;
		}
	}

	public static ArrayList<ArrayList<Route>> filterRoutesToCountry2(String name) {
		ArrayList<ArrayList<Route>> routeList = new ArrayList<ArrayList<Route>>();

		try {
			Country.getCMap()
			.get(name)
			.getCCMap()
			.values()
			.stream()
			.distinct()
			.sorted()
			.forEach(city -> {
				city.getCityAirportMap()
				.values()
				.stream()
				.distinct()
				.sorted()
				.forEach(airport -> routeList.addAll(airport.getGroupedRoutesToMap()
						.values()
						.stream()
						.distinct()
						.sorted((array1, array2) -> array1.get(0).getSrcAirport().getName().compareToIgnoreCase(array2.get(0).getSrcAirport().getName()))
						.collect(Collectors.toCollection(ArrayList::new))));
			});

		} catch (NullPointerException e) {
			System.out.println("Aucun pays contenant la chaine : " + name);
		}
		return routeList;
	}

	public static ArrayList<ArrayList<Route>> filterRoutesFromCountry2(String name) {
		ArrayList<ArrayList<Route>> routeList = new ArrayList<ArrayList<Route>>();

		try {
			Country.getCMap()
			.get(name)
			.getCCMap()
			.values()
			.stream()
			.distinct()
			.sorted()
			.forEach(city -> {
				city.getCityAirportMap()
				.values()
				.stream()
				.distinct()
				.sorted()
				.forEach(airport -> routeList.addAll(airport.getGroupedRoutesFromMap()
						.values()
						.stream()
						.sorted((array1, array2) -> array1.get(0).getDstAirport().getName().compareToIgnoreCase(array2.get(0).getDstAirport().getName()))
						.collect(Collectors.toCollection(ArrayList::new))));
			});

		} catch (NullPointerException e) {
			System.out.println("Aucun pays contenant la chaine : " + name);
		}
		return routeList;
	}

	public static ArrayList<ArrayList<Route>> filterRoutesToOrFromCountry2(String name, String type) {
		switch (type) {
		case "from":
		case "départ de":
			return filterRoutesFromCountry2(name);

		case "to":
		case "arrivée de":
			return filterRoutesToCountry2(name);
		default:
			System.err.println("Erreur sur le type entré" + "(" + type + ")");
			return null;
		}
	}

	public static ArrayList<ArrayList<Route>> filterRoutesToOrFrom2(String toOrFrom, String name, String type) {
		ArrayList<ArrayList<Route>> buff = new ArrayList<ArrayList<Route>>();

		switch (type) {
		case "airport":
		case "Airport":
		case "airp":
			buff = filterRoutesToOrFromAirport2(name, toOrFrom);
			break;
		case "city":
		case "City":
		case "ville":
			buff = filterRoutesToOrFromCity2(name, toOrFrom);
			break;
		case "country":
		case "Country":
		case "pays":
			buff = filterRoutesToOrFromCountry2(name, toOrFrom);
			break;
		default:
			System.err.println("Erreur sur le type recherché (" + type + ")");
		}
		return buff;
	}

	public static ArrayList<Route> filterByPlaceAndAirline2( String toOrFrom, String name, String type, String airline, boolean activeOnly) {
		ArrayList<Route> buff = new ArrayList<Route>();

		filterRoutesToOrFrom2(toOrFrom, name, type).forEach(array -> {
			array.forEach(route -> {
				buff.add(route);
			}); 
		});
		buff.retainAll(filterRoutesByAirline(airline, activeOnly));
		return buff;
	}

	public static ArrayList<ArrayList<Route>> filterByDirectAirpToAirp2(String srcAirp, String dstAirp, String airline, boolean activeOnly) {
		ArrayList<ArrayList<Route>> routesFrom = null;
		ArrayList<ArrayList<Route>> routesTo = filterRoutesToAirport2(dstAirp);

		if(airline != null) {

			ArrayList<Route> buffFiltre = filterByPlaceAndAirline2("from", srcAirp, "airp", airline, activeOnly);
			routesTo.forEach(array -> array.retainAll(buffFiltre));
			routesTo.removeIf(array -> array.isEmpty());
		}
		else {
			routesFrom = filterRoutesFromAirport2(srcAirp);
			routesTo.retainAll(routesFrom);
		}
		return routesTo;
	}

	public static ArrayList<ArrayList<Route>> filterByDirectCityToCity2(String srcCity, String dstCity, String airline, boolean activeOnly) {
		ArrayList<ArrayList<Route>> routesFrom = null;
		ArrayList<ArrayList<Route>> routesTo = filterRoutesToCity2(dstCity);

		if(airline != null) {

			ArrayList<Route> buffFiltre = filterByPlaceAndAirline2("from", srcCity, "city", airline, activeOnly);
			routesTo.forEach(array -> array.retainAll(buffFiltre));
			routesTo.removeIf(array -> array.isEmpty());
		}
		else {
			routesFrom = filterRoutesFromCity2(srcCity);
			routesTo.retainAll(routesFrom);
		}
		return routesTo;
	}

	public static ArrayList<ArrayList<Route>> filterByDirectCountryToCountry2(String srcCountry, String dstCountry, String airline, boolean activeOnly) {
		ArrayList<ArrayList<Route>> routesFrom = null;
		ArrayList<ArrayList<Route>> routesTo = filterRoutesToCountry2(dstCountry);

		if(airline != null) {

			ArrayList<Route> buffFiltre = filterByPlaceAndAirline2("from", srcCountry, "country", airline, activeOnly);
			routesTo.forEach(array -> array.retainAll(buffFiltre));
			routesTo.removeIf(array -> array.isEmpty());
		}
		else {
			routesFrom = filterRoutesFromCountry2(srcCountry);
			routesTo.retainAll(routesFrom);
		}
		return routesTo;
	}

	public static ArrayList<ArrayList<Route>> filterByDirectPlaceToPlace2(String srcPlace, String dstPlace, String type, String airline, boolean activeOnly) {
		ArrayList<ArrayList<Route>> buff = new ArrayList<ArrayList<Route>>();

		switch (type) {
		case "airport":
		case "Airport":
		case "airp":
			buff = filterByDirectAirpToAirp2(srcPlace, dstPlace, airline, activeOnly);
			break;
		case "city":
		case "City":
		case "ville":
			buff = filterByDirectCityToCity2(srcPlace, dstPlace, airline, activeOnly);
			break;
		case "country":
		case "Country":
		case "pays":
			buff = filterByDirectCountryToCountry2(srcPlace, dstPlace, airline, activeOnly);
			break;
		default:
			System.err.println("Erreur sur le type recherché (" + type + ")");
		}
		return buff;
	}

}