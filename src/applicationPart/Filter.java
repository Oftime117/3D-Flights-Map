package applicationPart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

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
		if(activeOnly)
			return Route.getRoutesList().stream()
					.distinct()
					.filter(route -> route.getAirline().getActive().equals("Y"))
					.sorted()
					.collect(Collectors.toCollection(ArrayList::new));

		return Route.getRoutesList().stream()
				.distinct()
				.filter(route -> route.getAirline().getName().toLowerCase().startsWith(name.toLowerCase()))
				.sorted()
				.collect(Collectors.toCollection(ArrayList::new));
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
				.sorted((array1, array2) -> array1.get(0).getSrcAirport().getName().compareToIgnoreCase(array2.get(0).getSrcAirport().getName()))
				.collect(Collectors.toCollection(ArrayList::new))));

		return buff;
	}

	public static ArrayList<Route> filterRoutesFromAirport(String name) {

		ArrayList<Route> buff = new ArrayList<Route>();


		Airport.getairportsMap().values()
		.stream()
		.distinct()
		.filter(airport -> airport.getName().toLowerCase().equals(name.toLowerCase()))
		.sorted()
		.forEach(airport -> buff.addAll(airport.getRoutesToList()));


		return buff;
	}

	public static ArrayList<Route> filterRoutesToAirport(String name) {

		ArrayList<Route> buff = new ArrayList<Route>();

		/**Version 1: Nom exacte*/
		Airport.getairportsMap().values()
		.stream()
		.distinct()
		.filter(airport -> airport.getName().toLowerCase().equals(name.toLowerCase()))
		.sorted()
		.forEach(airport -> buff.addAll(airport.getRoutesFromList()));

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

	public static ArrayList<Route> filterRoutesToOrFromAirport(String name, String type) {
		switch (type) {
		case "from":
		case "départ de":
			return filterRoutesFromAirport(name);

		case "to":
		case "arrivée de":
			return filterRoutesToAirport(name);
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
						.filter(array -> array.isEmpty())
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
						.sorted((array1, array2) -> array1.get(0).getDstAirport().getName().compareToIgnoreCase(array2.get(0).getDstAirport().getName()))
						.collect(Collectors.toCollection(ArrayList::new)))));


		return routeList;
	}

	public static ArrayList<Route> filterRoutesToCity(String name) {

		ArrayList<Route> routeList = new ArrayList<Route>();

		City.getCityMap()
		.values()
		.stream()
		.filter(city -> city.getName().equals(name))
		.forEach(city -> city.getCityAirportMap().values()
				.forEach(airport -> routeList.addAll(airport.getRoutesFromList())));

		return routeList;
	}

	public static ArrayList<Route> filterRoutesFromCity(String name) {

		ArrayList<Route> routeList = new ArrayList<Route>();

		City.getCityMap()
		.values()
		.stream()
		.filter(city -> city.getName().equals(name))
		.forEach(city -> city.getCityAirportMap().values()
				.forEach(airport -> routeList.addAll(airport.getRoutesToList())));
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

	public static ArrayList<Route> filterRoutesToOrFromCity(String name, String type) {
		switch (type) {
		case "from":
		case "départ de":
			return filterRoutesFromCity(name);

		case "to":
		case "arrivée de":
			return filterRoutesToCity(name);
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
			//.map(city -> city.getCityAirportMap().values())
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

	public static ArrayList<Route> filterRoutesToCountry(String name) {
		ArrayList<Route> routeList = new ArrayList<Route>();

		try {
			Country.getCMap()
			.get(name)
			.getCCMap()
			.values()
			.stream()
			.sorted()
			.forEach(city -> {
				city.getCityAirportMap()
				.values()
				.stream()
				.sorted()
				.forEach(airport -> routeList.addAll(airport.getRoutesFromList()));
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

	public static ArrayList<Route> filterRoutesFromCountry(String name) {
		ArrayList<Route> routeList = new ArrayList<Route>();
		try {
			Country.getCMap()
			.get(name)
			.getCCMap()
			.values()
			.stream()
			.sorted()
			.forEach(city -> {
				city.getCityAirportMap()
				.values()
				.stream()
				.sorted()
				.forEach(airport -> routeList.addAll(airport.getRoutesToList()));
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

	public static ArrayList<Route> filterRoutesToOrFromCountry(String name, String type) {
		switch (type) {
		case "from":
		case "départ de":
			return filterRoutesFromCountry(name);

		case "to":
		case "arrivée de":
			return filterRoutesToCountry(name);
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

	public static ArrayList<Route> filterRoutesToOrFrom(String toOrFrom, String name, String type) {
		ArrayList<Route> buff = new ArrayList<Route>();

		switch (type) {
		case "airport":
		case "Airport":
		case "airp":
			buff = filterRoutesToOrFromAirport(name, toOrFrom);
			break;
		case "city":
		case "City":
		case "ville":
			buff = filterRoutesToOrFromCity(name, toOrFrom);
			break;
		case "country":
		case "Country":
		case "pays":
			buff = filterRoutesToOrFromCountry(name, toOrFrom);
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
		
//		ArrayList<ArrayList<Route>> buff = new ArrayList<ArrayList<Route>>();
//		
//		filterRoutesToOrFrom2(toOrFrom, name, type).forEach(array -> {
//			@SuppressWarnings("unchecked")
//			ArrayList<Route> buffArray = (ArrayList<Route>) array.clone();
//			buffArray
//			buff.add(array);
//			
//		});
//		
//		return null;
	}

	public static ArrayList<Route> filterByPlaceAndAirline( String toOrFrom, String name, String type, String airline, boolean activeOnly) {
		ArrayList<Route> buff = filterRoutesToOrFrom(toOrFrom, name, type);
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

	public static ArrayList<Route> filterByDirectAirpToAirp(String srcAirp, String dstAirp, String airline, boolean activeOnly) {
		ArrayList<Route> buff = new ArrayList<Route>();
		ArrayList<Route> buff2 = filterRoutesToAirport(dstAirp);
		if(airline != null) buff = filterByPlaceAndAirline("from", srcAirp, "airp", airline, activeOnly);
		else buff = filterRoutesFromAirport(srcAirp);
		buff.retainAll(buff2);
		return buff;
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

	public static ArrayList<Route> filterByDirectCityToCity(String srcCity, String dstCity, String airline, boolean activeOnly) {
		ArrayList<Route> buff = new ArrayList<Route>();
		ArrayList<Route> buff2 = filterRoutesToCity(dstCity);
		if(airline != null) buff = filterByPlaceAndAirline("from", srcCity, "city", airline, activeOnly);
		else buff = filterRoutesFromCity(srcCity);
		buff.retainAll(buff2);
		return buff;
	}
	public static ArrayList<Route> filterByDirectCountryToCountry(String srcCountry, String dstCountry, String airline, boolean activeOnly) {
		ArrayList<Route> buff = new ArrayList<Route>();
		ArrayList<Route> buff2 = filterRoutesToCountry(dstCountry);
		if(airline != null) buff = filterByPlaceAndAirline("from", srcCountry, "country", airline, activeOnly);
		else buff = filterRoutesFromCountry(srcCountry);
		buff.retainAll(buff2);
		return buff;
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

	public static ArrayList<Route> filterByDirectPlaceToPlace(String srcPlace, String dstPlace, String type, String airline, boolean activeOnly) {
		ArrayList<Route> buff = new ArrayList<Route>();

		switch (type) {
		case "airport":
		case "Airport":
		case "airp":
			buff = filterByDirectAirpToAirp(srcPlace, dstPlace, airline, activeOnly);
			break;
		case "city":
		case "City":
		case "ville":
			buff = filterByDirectCityToCity(srcPlace, dstPlace, airline, activeOnly);
			break;
		case "country":
		case "Country":
		case "pays":
			buff = filterByDirectCountryToCountry(srcPlace, dstPlace, airline, activeOnly);
			break;
		default:
			System.err.println("Erreur sur le type recherché (" + type + ")");
		}
		return buff;
	}

	public static ArrayList<ArrayList<Route>> filterByAirpToAirp(String srcAirp, String dstAirp, String airline, boolean activeOnly) {

		ArrayList<Route> buff = filterByDirectAirpToAirp(srcAirp, dstAirp, airline, activeOnly);

		ArrayList<Route> buff1 = new ArrayList<Route>(), buff2 = new ArrayList<Route>();

		ArrayList<ArrayList<Route>> arrayRoute = new ArrayList<ArrayList<Route>>();

		ArrayList<ArrayList<Route>> finalBuff = new ArrayList<ArrayList<Route>>();
		finalBuff.add(buff);

		if(buff != null) return finalBuff;
		else {
			buff1 = filterRoutesFromAirport(srcAirp);
			buff1.forEach(route -> { 
				filterByAirpToAirp(route.getSrcAirport().getName(), dstAirp, airline, activeOnly);	
			});

			return null;
		}
	}

	static ArrayList<ArrayList<Route>> routeArray = new ArrayList<ArrayList<Route>>();
	static long cpt = 0;

	private static boolean filterTest(String srcAirp, String dstAirp, String airline, boolean activeOnly, int profondeur) {

		if(profondeur > 2 || routeArray.size() > 10) return false;
		//		System.err.println("profondeur: " + profondeur);
		//		System.err.println("taille route array: " + routeArray.size());
		//		System.err.println("nom airportsrc: " + srcAirp);
		ArrayList<Route> routeList = filterByDirectAirpToAirp(srcAirp, dstAirp, airline, activeOnly);


		//		if(!routeList.isEmpty()) {
		//			routeArray.add(routeList);
		//			return true;
		//		}
		//		else {
		//			
		//			filterRoutesFromAirport(srcAirp).forEach(route -> {
		//				
		//				ArrayList<Route> buffRoute = new ArrayList<Route>();
		//				if(filterTest(route.getDstAirport().getName(), dstAirp, airline, activeOnly, profondeur+1)) {
		//					buffRoute.add(route);
		//					routeArray.add(buffRoute);
		//				}
		//			});
		//		}
		//		
		//		
		//		return false;



		filterRoutesFromAirport(srcAirp).forEach(route -> {

			ArrayList<Route> buffRoute = new ArrayList<Route>();
			if(filterTest(route.getDstAirport().getName(), dstAirp, airline, activeOnly, profondeur+1)) {
				buffRoute.add(route);
				routeArray.add(buffRoute);
			}
		});

		if(!routeList.isEmpty()) {
			routeArray.add(routeList);
			return true;
		}
		return false;
	}

	private static boolean filterTest2(String srcAirp, String dstAirp, String airline, boolean activeOnly, int profondeur) {

		if(profondeur > 2 || routeArray.size() > 20) return false;

		ArrayList<ArrayList<Route>> routeList = filterByDirectAirpToAirp2(srcAirp, dstAirp, airline, activeOnly), 
				buffList = new ArrayList<ArrayList<Route>>();
		//		
		//		// vol direct
		//		if(!routeList.isEmpty()){
		//			routeArray.addAll(routeList);
		//			return true;
		//		}

		// vol non direct

		routeList = filterRoutesFromAirport2(srcAirp);

		//		routeArray.addAll(routeList);

		routeList.forEach(array -> {
			//			System.out.println(array.get(0));
			//			
			//			try {
			//				Thread.sleep(500);
			//			} catch (Exception e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
			ArrayList<ArrayList<Route>> route = filterByDirectAirpToAirp2(array.get(0).getDstAirport().getName(), dstAirp, airline, activeOnly) ;
			//			if(route.isEmpty()) System.out.println("\nroute empty\n");
			//			else System.err.println("\nroute pas empty!\n");

			if(!route.isEmpty() && !array.isEmpty()) {

				routeArray.addAll(route);

				System.err.println("pefjzpoefjeifjesoicfjoidqjoidsqfjpoqifjs dfpih dscfohs");
			} 
			routeArray.add(array);
			if (profondeur > 2) routeArray.remove(array);
			else {
				filterTest2(array.get(0).getDstAirport().getName(), dstAirp, airline, activeOnly, profondeur+1);
				// changer peut être la valeur de retour de la fonction
				// Si vol direct, renvoyer l'arrayList du vol
			}
			//			//else routeArray.remove(array);
		});

		//		if(!routeList.isEmpty()) {
		//			routeList.forEach(array -> {
		//				if(filterTest2(array.get(0).getSrcAirport().getName(), dstAirp, airline, activeOnly, profondeur+1) == true) routeArray.add(array);
		//			});
		//		}

		return false;
	}





	//test avec une seule liste de route
	private static ArrayList<ArrayList<Route>> routeList = new ArrayList<ArrayList<Route>>();
	private static boolean test = false;
	private static long nb = 0;
	private static boolean filterTest3(String srcAirp, String dstAirp, String airline, boolean activeOnly, int profondeur) {
		//if(profondeur > 2) return null;
		//		if(test == true) return true;
		ArrayList<ArrayList<Route>> buff1 = filterByDirectAirpToAirp2(srcAirp, dstAirp, airline, activeOnly);
		// Par définition, la liste si vol direct, ne contient qu'une arraylist de route
		if(!buff1.isEmpty()) {
			routeList.add(buff1.get(0));
			return true;
		}

		// vol non direct
		//boolean f;
		//		filterRoutesFromAirport2(srcAirp).forEach(array -> array.forEach(route -> {
		//			ArrayList<Route> buff =null;
		//			if(profondeur < 1) {
		//				if(test == true) {
		//					routeList.add(array);
		//					System.err.println(array+ "\n");
		//					try {
		//						Thread.sleep(500);
		//					} catch (Exception e) {
		//						// TODO Auto-generated catch block
		//						e.printStackTrace();
		//					}
		//				}
		//				else test = filterTest3(route.getDstAirport().getName(), dstAirp, airline, activeOnly, profondeur+1);

		//			}
		//			
		//		}));

		filterRoutesFromAirport2(srcAirp).forEach(array -> {
			if(profondeur < 2 && nb < 100) {
				// Pour chaque truc, ouvrir un thread?
				if(nb > 90)	{
					System.out.println("osfgso");			
				}
				test = filterTest3(array.get(0).getDstAirport().getName(), dstAirp, airline, activeOnly, profondeur+1);
				if(test == true) {
					routeList.add(array);
					nb++;
					//						System.err.println(array+ "\n");
					//						try {
					//							Thread.sleep(500);
					//						} catch (Exception e) {
					//							// TODO Auto-generated catch block
					//							e.printStackTrace();
					//						}
				}
			}

		});
		return false;
	}

	public static ArrayList<ArrayList<Route>> test(String srcAirp, String dstAirp, String airline, boolean activeOnly) {
		routeArray.clear();
		routeList.clear();
		if(Airport.filterByName(dstAirp).isEmpty() || filterRoutesFromAirport2(dstAirp).isEmpty()) return routeList;

		filterTest3(srcAirp, dstAirp, airline, activeOnly, 0);
		//	System.err.println("array size: " + routeArray.size());
		Collections.reverse(routeArray);
		return routeList;
	}
}