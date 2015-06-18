package ghaziHamelin;

import gui.Earth3DMap;
import gui.MainWindow;

import java.time.Clock;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import applicationPart.Airline;
import applicationPart.Airport;
import applicationPart.Filter;
import applicationPart.Route;

import com.jme3.system.AppSettings;

public class MainMethod {

	public static Clock clock = Clock.systemDefaultZone();
	public static long deb = clock.millis();
	
	public static void applicationInit() {
		
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
			e.printStackTrace();
		}

		Route.parse();
		System.err.println("\n\n\n" + (System.currentTimeMillis() - deb));

		deb = clock.millis();
		
		
//		Filter.filterRoutesFromAirport2("charles de gaulle").stream().limit(52).forEach(System.out::println);

//		System.out.println(City.getCityMap().get("ParisFrance"));

//		Filter.filterByDirectAirpToAirp2("Charles de Gaulle", "Zagreb", "Air France", false).forEach(System.out::println);;
//		Filter.filterByDirectCountryToCountry2("France", "United Kingdom", null, false).forEach(System.out::println);
		//52
//		Filter.filterByDirectPlaceToPlace2("Charles de gaulle", "Dallas Fort Worth Intl", "airp", null, true).forEach(System.out::println);
//		Filter.test("Charles de gaulle", "Dallas Fort Worth Intl", null, false).forEach(System.out::println);
//		Filter.test("Charles de gaulle", "La Guardia", null, false).forEach(System.out::println);;
//		System.err.println(Filter.test("Charles de gaulle", "La Guardia", null, false).size());
//		Airport.filterByName("charles").forEach(System.out::println);
//		Filter.filterAirportsByCity("London").forEach(System.out::println);
//		City.filterByName("Paris").forEach(System.out::println);
//		System.out.println(Filter.filterRoutesToAirport2("Charles de Gaulle").stream().distinct().count());
//		Filter.filterAirportByCountry("France").forEach(System.out::println);
		System.out.println(-deb + clock.millis());
		
	}
	
	
	public static void guiInit() {
		AppSettings settings = new AppSettings(true);
		settings.setResolution(1600, 1000);
		settings.setSamples(8);
		settings.setVSync(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainWindow();
			}
		});
		
	}
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		applicationInit();
		guiInit();
		
//		try {
//			Thread.sleep(5000);
//			return;
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
