package ghaziHamelin;

import gui.MainWindow;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import applicationPart.Airline;
import applicationPart.Airport;
import applicationPart.Route;

/**
 * @Class classe principal contenant la méthode main
 * 
 */
public class MainMethod {
	
	/**@brief Fonction qui gère la partie applicative, ie le parsage et les filtres
	 * 
	 */
	public static void applicationInit() {
		/* Utilisation des threads, même si en pratique la vitesse est quasi la même en séquentiel */
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
	}
	
	
	/**@brief Fonction qui initialise la partie graphique (3D et swing)
	 * 
	 */
	public static void guiInit() {
		/* Lancement dans le EDT (Event Dispatch Thread) de la partie graphique du logiciel*/
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainWindow();
			}
		});
		
	}
	
	public static void main(String[] args) {
		/* Changement du Look And Feel suivant le système sur lequel est lancé le programme */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		applicationInit();
		guiInit();
	}
}
