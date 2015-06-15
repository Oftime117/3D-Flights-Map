package ghaziHamelin;

import java.time.Clock;

import applicationPart.Airline;
import applicationPart.Airport;
import applicationPart.Filter;
import applicationPart.Route;

public class MainMethod {

	public static void main(String[] args) {

		long deb = System.currentTimeMillis();
		Thread portThread = new Thread() {
			@Override
			public synchronized void run() {
				Airport.parse();
			}
		};
		portThread.start();

		Thread lineThread = new Thread() {
			@Override
			public synchronized void run() {
				Airline.parse();
			}
		};
		lineThread.start();

		try {
			portThread.join();
			lineThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Route.parse();
		System.err.println("\n\n\n" + (System.currentTimeMillis() - deb));


		Clock clock = Clock.systemDefaultZone();
		deb = clock.millis();


//		Filter.filterRoutesFromAirport2("charles de gaulle").stream().limit(52).forEach(System.out::println);

//		System.out.println(City.getCityMap().get("ParisFrance"));

//		Filter.filterByDirectAirpToAirp2("Charles de Gaulle", "Zagreb", "Air France", false).forEach(System.out::println);;
//		Filter.filterByDirectCountryToCountry2("France", "United Kingdom", null, false).forEach(System.out::println);
		//52
//		Filter.filterByDirectPlaceToPlace2("Charles de gaulle", "Dallas Fort Worth Intl", "airp", null, true).forEach(System.out::println);
//		Filter.test("Charles de gaulle", "Dallas Fort Worth Intl", null, false).forEach(System.out::println);
		Filter.test("Charles de gaulle", "La Guardia", null, false).forEach(System.out::println);;
		System.err.println(Filter.test("Charles de gaulle", "La Guardia", null, false).size());
//		Airport.filterByName("efef").forEach(System.out::println);;
//		System.out.println(Filter.filterRoutesToAirport2("Charles de Gaulle").stream().distinct().count());
		System.out.println(-deb + clock.millis());
	}
}
