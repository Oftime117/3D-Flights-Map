package applicationPart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Route {


    private String codeshare;
    private int stops;
    private String equipment;
    
    private Airport srcAirport;
    private Airport dstAirport;
    private Airline airline;
    
    private static ArrayList<Route> routesList = null; 
    
    String airlineCode; int airlineID; String srcAirportCode;
    int srcAirportID; String dstAirportCode; int dstAirportID;

    public Route(String airlineCode, int airlineID, String srcAirportCode,
	    int srcAirportID, String dstAirportCode, int dstAirportID,
	    String codeshare, int stops, String equipment) {
	super();

	this.codeshare = codeshare;
	this.stops = stops;
	this.equipment = equipment;
	
	this.airlineCode = airlineCode;
	this.airlineID = airlineID;
	this.srcAirportCode = srcAirportCode;
	this.srcAirportID = srcAirportID;
	this.dstAirportCode = dstAirportCode;
	this.dstAirportID = dstAirportID;
	
	if(airlineCode.length() == 2) airline = Airline.getairlinesMap().get(new TripleKey(airlineID, airlineCode, ""));
	else if (airlineCode.length() == 3) airline = Airline.getairlinesMap().get(new TripleKey(airlineID, "", airlineCode));
	else System.err.println("Erreur sur le nombre de caractère du airlineCode\nairlineCode = " + airlineCode);
	
	if(srcAirportCode.length() == 3) srcAirport = Airport.getairportsMap().get(new TripleKey(srcAirportID, srcAirportCode, ""));
	else if(srcAirportCode.length() == 4) srcAirport = Airport.getairportsMap().get(new TripleKey(srcAirportID, "", srcAirportCode));
	else System.err.println("Erreur sur le nombre de caractère du srcAirportCode\nsrcAirportCode = " + srcAirportCode);
	

	if(dstAirportCode.length() == 3) dstAirport = Airport.getairportsMap().get(new TripleKey(dstAirportID, dstAirportCode, ""));
	else if(dstAirportCode.length() == 4) dstAirport = Airport.getairportsMap().get(new TripleKey(dstAirportID, "", dstAirportCode));
	else System.err.println("Erreur sur le nombre de caractère du dstAirportCode\ndstAirportCode = " + dstAirportCode);
	
	//System.err.println();
	
    }


    /**
     * @return the routesList
     */
    public static ArrayList<Route> getroutesList() {
	if(routesList == null) Route.parse();
        return routesList;
    }

    
    @SuppressWarnings("serial")
    public static void parse() {
	
	String path = "ressources/routes.dat";
	File file = new File(path);
	BufferedReader buffReader = null;
	try {

	    buffReader = new BufferedReader(new FileReader(file));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.err.println("Impossible de trouver le fichier " + path);
	    return;
	}

	routesList = new ArrayList<Route>() {
	  
	    /*
	     * (non-Javadoc)
	     * 
	     * @see java.lang.Object#toString()
	     */
	    @Override
	    public String toString() {

		StringBuilder sb = new StringBuilder();
		Iterator<Route> it = this.iterator();

		sb.append("List of Routes : ");
		while (it.hasNext()) {
		    Route buff = it.next();
		    sb.append("\n\t" + buff.toString() + "\n");
		}

		return sb.toString();
	    }
	};
	
	try {
	    String lineBuff;
	    while ((lineBuff = buffReader.readLine()) != null) {

		String[] lineSplits;

		/* Chercher meilleure Regex */
		lineSplits = lineBuff
			.split("\"?,(?=(?:(?:[^\"]*\"){2})*[^\"]*$)\"?\"?");

		if (lineSplits.length != 9 && lineSplits.length != 8) {

		    System.err
			    .println("erreur sur le nombre d'arguments dans la ligne du fichier "
				    + path);
		    System.err.println("length: " + lineSplits.length);
		    System.err.println("Contenu de la ligne: \n");

		    for (int i = 0; i < lineSplits.length; ++i) {
			System.err.println(lineSplits[i]);
		    }
		    System.exit(-1);

		}

		String airlineCode = lineSplits[0];

		int airlineID;
		try {
		    airlineID = Integer.valueOf(lineSplits[1]);
		} catch (NumberFormatException e) {
		    airlineID = -1;
		}

		String srcAirportCode = lineSplits[2];

		int srcAirportID;
		try {
		    srcAirportID = Integer.valueOf(lineSplits[3]);
		} catch (NumberFormatException e) {
		    srcAirportID = -1;
		}

		String dstAirportCode = lineSplits[4];

		int dstAirportID;
		try {
		    dstAirportID = Integer.valueOf(lineSplits[5]);
		} catch (NumberFormatException e) {
		    dstAirportID = -1;
		}

		String codeshare = lineSplits[6];

		int stops;
		try {
		    stops = Integer.valueOf(lineSplits[7]);
		} catch (NumberFormatException e) {
		    stops = -1;
		}

		String equipment;
		try {
		    equipment = lineSplits[8];
		} catch (ArrayIndexOutOfBoundsException e) {
		    equipment = null;
		}

		routesList.add(new Route(airlineCode, airlineID, srcAirportCode,
			srcAirportID, dstAirportCode, dstAirportID, codeshare,
			stops, equipment));
	    }

	} catch (IOException e) {

	    e.printStackTrace();
	} finally {
	    try {
		buffReader.close();
	    } catch (IOException e) {

		e.printStackTrace();
	    }
	}
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Route "
		+ "\n\t{"
		+ "\n\t\tairline = " + airline 
		+ "\n\t\tsrcAirport = " + srcAirport
		+ "\n\t\tdstAirport = " + dstAirport
		+ "\n\t\tcodeshare = " + codeshare
		+ "\n\t\tstops = " + stops 
		+ "\n\t\tequipment = " + equipment
		+ "\n\t}";
    }
    
    public String test() {
	
	return "airlineCode: " + airlineCode +"\nairlineID: " + airlineID
		+ "\nsrcAirportCode: " + srcAirportCode + "\nsrcAirportID: " + srcAirportID
		+ "\ndstAirportCode: " + dstAirportCode + "\ndstAirportID: " + dstAirportID + '\n';
    }
    
}
