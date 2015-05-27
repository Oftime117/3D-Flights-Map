package applicationPart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Airport {

    private int id;
    private String name;
    private String city;
    private String country;
    private String IATA_FAA_Code;
    private String ICAO_Code;
    private float latitude;
    private float longitude;
    private float altitude;
    private float timezone;
    private String DST;
    private String Tz_timezone;
    
    
   private static ArrayList<Airport> AirportsList;
    
   

    public Airport(int id, String name, String city, String country,
	    String iATA_FAA_Code, String iCAO_Code, float latitude,
	    float longitude, float altitude, float timezone, String dST,
	    String tz_timezone) {
	super();
	this.id = id;
	this.name = name;
	this.city = city;
	this.country = country;
	IATA_FAA_Code = iATA_FAA_Code;
	ICAO_Code = iCAO_Code;
	this.latitude = latitude;
	this.longitude = longitude;
	this.altitude = altitude;
	this.timezone = timezone;
	DST = dST;
	Tz_timezone = tz_timezone;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Airport : " + name
		+ "\n\t{"
		+ "\n\t\tid = " + id 
		+ "\n\t\tcity = " + city
		+ "\n\t\tcountry = " + country 
		+ "\n\t\tIATA_FAA_Code = " + IATA_FAA_Code
		+ "\n\t\tICAO_Code = " + ICAO_Code 
		+ "\n\t\tlatitude = " + latitude
		+ "\n\t\tlongitude = " + longitude 
		+ "\n\t\taltitude = " + altitude
		+ "\n\t\ttimezone = " + timezone 
		+ "\n\t\tDST = " + DST 
		+ "\n\t\tTz_timezone = " + Tz_timezone
		+ "\n\t}";
    }
    
    /**
     * @return the airportsList
     */
    public static ArrayList<Airport> getAirportsList() {
        return AirportsList;
    }

    
    
    @SuppressWarnings("serial")
    public static void parse() {
	
	String path = "Ressources/airports.dat";
	
	File file = new File(path);
	BufferedReader buffReader = null;
	try {

	    buffReader = new BufferedReader(new FileReader(file));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.err.println("Impossible de trouver le fichier " + path);
	    return;
	}

	AirportsList = new ArrayList<Airport>() {
	    
	    /* (non-Javadoc)
	     * @see java.lang.Object#toString()
	     */
	    @Override
	    public String toString() {
		
		StringBuilder sb = new StringBuilder();
		Iterator<Airport> it = this.iterator();
		
		sb.append("List of Airports : ");
		while(it.hasNext()) {
		    Airport buff = it.next();
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

		if (lineSplits.length != 12) {
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
		String city = lineSplits[2];
		String country = lineSplits[3];
		String IATA_FAA_Code = lineSplits[4];
		String ICAO_Code = lineSplits[5];
		float latitude = Float.valueOf(lineSplits[6]);
		float longitude = Float.valueOf(lineSplits[7]);
		float altitude = Float.valueOf(lineSplits[8]);
		float timezone = Float.valueOf(lineSplits[9]);
		String DST = lineSplits[10];
		String Tz_timezone = lineSplits[11];

		AirportsList.add(new Airport(id, name, city, country, IATA_FAA_Code,
			ICAO_Code, latitude, longitude, altitude, timezone,
			DST, Tz_timezone));
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
    
}
