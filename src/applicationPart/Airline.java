package applicationPart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Airline {

    private int id;
    private String name;
    private String alias;
    private String IATA_Code;
    private String ICAO_Code;
    private String callsign;
    private String country;
    private String active;
    
    private static ArrayList<Airline> airlinesList;

    public Airline(int id, String name, String alias, String iATA_Code,
	    String iCAO_Code, String callsign, String country, String active) {
	super();
	this.id = id;
	this.name = name;
	this.alias = alias;
	IATA_Code = iATA_Code;
	ICAO_Code = iCAO_Code;
	this.callsign = callsign;
	this.country = country;
	this.active = active;
    }

    
    
    /**
     * @return the airlinesList
     */
    public static ArrayList<Airline> getAirlinesList() {
        return airlinesList;
    }

    @SuppressWarnings("serial")
    public static void parse() {
	
	String path = "Ressources/airlines.dat";
	
	File file = new File(path);
	BufferedReader buffReader = null;
	try {

	    buffReader = new BufferedReader(new FileReader(file));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.err.println("Impossible de trouver le fichier " + path);
	    return;
	}

	airlinesList = new ArrayList<Airline>() {
	    
	    /* (non-Javadoc)
	     * @see java.lang.Object#toString()
	     */
	    @Override
	    public String toString() {
		
		StringBuilder sb = new StringBuilder();
		Iterator<Airline> it = this.iterator();
		
		sb.append("List of Airlines : ");
		while(it.hasNext()) {
		    Airline buff = it.next();
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

		airlinesList.add(new  Airline(id, name, alias, IATA_Code, 
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
	return "Airline : " + name
		+ "\n\t{"
		+ "\n\t\tid = " + id 
		+ "\n\t\tnalias = " + alias
		+ "\n\t\tIATA_Code = " + IATA_Code 
		+ "\n\t\tICAO_Code = " + ICAO_Code
		+ "\n\t\tcallsign = " + callsign 
		+ "\n\t\tcountry = " + country
		+ "\n\t\tactive = " + active
		+ "\n\t}";
    }

    
}
