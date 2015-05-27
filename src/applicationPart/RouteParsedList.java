package applicationPart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class RouteParsedList extends ArrayList<Route> {

    public RouteParsedList(String path) {

	super();

	File file = new File(path);
	BufferedReader buffReader = null;
	try {

	    buffReader = new BufferedReader(new FileReader(file));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.err.println("Impossible de trouver le fichier " + path);
	    return;
	}

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

		this.add(new Route(airlineCode, airlineID, srcAirportCode,
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	Iterator<Route> it = this.iterator();

	sb.append("List of Airports : ");
	while (it.hasNext()) {
	    Route buff = it.next();
	    sb.append("\n\t" + buff.toString() + "\n");
	}

	return sb.toString();
    }

}
