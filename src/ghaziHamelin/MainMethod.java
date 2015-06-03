package ghaziHamelin;

import java.util.Iterator;

import applicationPart.Airline;
import applicationPart.Airport;
import applicationPart.Route;
import applicationPart.TripleKey;


public class MainMethod {

    public static void main(String[] args) {
	
	Airport.parse();
	Airline.parse();
	Route.parse();
	
	TripleKey key = new TripleKey(-1,null, "ZJSY"), key2 = new TripleKey(-1, null, "ZJY");
	System.err.println(Airport.getairportsMap().get(key));
    }

}
