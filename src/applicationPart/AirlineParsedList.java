package applicationPart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class AirlineParsedList extends ArrayList<Airline> {

    public AirlineParsedList(String path) {

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
		lineSplits = lineBuff.split("\"?,(?=(?:(?:[^\"]*\"){2})*[^\"]*$)\"?\"?");

		if (lineSplits.length != 8) {
		    System.err.println("erreur sur le nombre d'arguments dans la ligne du fichier "
				    + path);
		    System.err.println("length: " + lineSplits.length);
		    System.err.println("Contenu de la ligne: \n");

		    for (int i = 0; i < lineSplits.length; ++i) {
			System.err.println(lineSplits[i]);
		    }
		    return;
		}
		
		int id = Integer.valueOf(lineSplits[0]);
		String name = lineSplits[1];
		String alias = lineSplits[2]; 
		String IATA_Code = lineSplits[3];
		String ICAO_Code = lineSplits[4];
		String callsign = lineSplits[5];
		String country = lineSplits[6];
		String active = lineSplits[7];

		this.add(new  Airline(id, name, alias, IATA_Code, 
		ICAO_Code, callsign, country, active));
	    }
	    
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    try {
		buffReader.close();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	
	StringBuilder sb = new StringBuilder();
	Iterator<Airline> it = this.iterator();
	
	sb.append("List of Airports : ");
	while(it.hasNext()) {
	    Airline buff = it.next();
	    sb.append("\n\t" + buff.toString() + "\n");
	}
	
	return sb.toString();
    }

}
