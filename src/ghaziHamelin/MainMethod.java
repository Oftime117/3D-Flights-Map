package ghaziHamelin;

import applicationPart.Airport;

public class MainMethod {

	public static void main(String[] args) {

		//AirportParsedList test = new AirportParsedList("airports.dat");
		Airport.parse();
		//Airport tests = Airport.getAirportsList().get(0);

		System.err.println(Airport.getAirportsList());

		//RouteParsedList testr = new RouteParsedList("routes.dat");
		//System.out.println(testr);
		//System.err.println(testr.size());
	}

}
