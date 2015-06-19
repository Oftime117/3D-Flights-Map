package applicationPart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class RoutesTableModel extends AbstractTableModel {
	private String[] columns = {"Departure", "Arrival", "Airline", "Departure Country", "Departure City", "Destination Country", "Destination City"};
	private List<Route> routes = new ArrayList<Route>();
	
	/**
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}


	public RoutesTableModel() {
		super();
	}
	
	
	public RoutesTableModel(ArrayList<Route> routes) {
		super();
		this.routes.addAll(routes);
	}
	
	public void fromDoubleAray(ArrayList<ArrayList<Route>> routes) {
		Iterator<ArrayList<Route>> it = routes.iterator();
		while(it.hasNext()) {
			ArrayList<Route> routesArr = it.next();
			this.routes.addAll(routesArr);
		}
	}
	
	@Override
	public int getRowCount() {
		return routes.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return routes.get(rowIndex).getSrcAirport();
		case 1:
			return routes.get(rowIndex).getDstAirport();
		case 2:
			return routes.get(rowIndex).getAirline();
		case 3:
			return routes.get(rowIndex).getSrcAirport().getCountry();
		case 4:
			return routes.get(rowIndex).getSrcAirport().getCity();
		case 5:
			return routes.get(rowIndex).getDstAirport().getCountry();
		case 6:
			return routes.get(rowIndex).getDstAirport().getCity();
		default:
			return null;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return Airport.class;
		case 1:
			return Airport.class;
		case 2:
			return Airline.class;
		case 3:
			return Country.class;
		case 4:
			return City.class;
		case 5:
			return Country.class;
		case 6: 
			return City.class;
		default:
			return null;
		}
	}


	public Route getRouteAt(int i) {
		return routes.get(i);
	}
}
