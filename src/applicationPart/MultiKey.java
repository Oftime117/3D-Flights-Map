package applicationPart;

import java.util.Objects;

/**
 * @Class Clé personnalisé pouvant contenir et comparer plusieurs informations
 * 
 */
public class MultiKey {

	public final int id;
	public final String IATA_Code;
	public final String ICAO_Code;

	public MultiKey(int id, String IATA_Code, String ICAO_Code) {
		this.id = id;
		this.IATA_Code = IATA_Code;
		this.ICAO_Code = ICAO_Code;

	}

	public int hashCode() {
		return (id << 16) + Objects.hashCode(IATA_Code)
				+ Objects.hashCode(ICAO_Code);
	}

	public boolean equals(Object o) {
		MultiKey other = (MultiKey) o;

		if (id != -1) {
			if (id == other.id)
				return true;
			return false;
		} else if (IATA_Code != null) {
			if (IATA_Code.equals(other.IATA_Code))
				return true;
			return false;
		} else if (ICAO_Code != null && other.ICAO_Code != null) {
			if (ICAO_Code.equals(other.ICAO_Code))
				return true;
			return false;
		}
		return false;
	}

	public String toString() {
		return "id :" + id + "\nIATA_Code : " + IATA_Code + "\nICAO_Code : "
				+ ICAO_Code + "\n\n";

	}
}
