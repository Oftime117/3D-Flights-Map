package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

/**
 * @Class Classe gérant la partie IHM
 * 
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	/* Affichage de la partie 3D */
	private Earth3DMap earth3dMap;
	private Canvas canvas;
	/* les Différent panel utilisés*/
	private JPanel mainPanel;
	private SearchPanel searchPane;
	private ResultPanel resultsPane;
	private RouteInfoPanel routeInfoPane;
	


	public MainWindow() throws HeadlessException {
		super("3D Flight Map -- Amirali Ghazi, Enzo Hamelin");
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				earth3dMap.stop();
			}
		});

		init3D();
		createMenu();

		mainPanel = new JPanel(new BorderLayout(1,1), true);

		routeInfoPane = new RouteInfoPanel();
		mainPanel.add(routeInfoPane, BorderLayout.SOUTH);
		
		resultsPane = new ResultPanel(earth3dMap, routeInfoPane);
		
		searchPane = new SearchPanel(resultsPane, earth3dMap);
		
		
		mainPanel.add(searchPane, BorderLayout.WEST);
		mainPanel.add(canvas, BorderLayout.CENTER);
		mainPanel.add(resultsPane, BorderLayout.EAST);
		mainPanel.setOpaque(true);
		setContentPane(mainPanel);
		this.setVisible(true);
		pack();
	}

	/**
	 * @Brief Initialise les paramètres 3D
	 */
	private void init3D() {
		AppSettings settings = new AppSettings(true);
		settings.setResolution(1000, 520);
		settings.setSamples(8);
		settings.setVSync(true);

		earth3dMap = new Earth3DMap();
		earth3dMap.setSettings(settings);
		earth3dMap.setShowSettings(false);
		earth3dMap.setPauseOnLostFocus(false);

		earth3dMap.createCanvas();

		JmeCanvasContext ctx = (JmeCanvasContext) earth3dMap.getContext();
		canvas = ctx.getCanvas();
		Dimension dim = new Dimension(settings.getWidth(), settings.getHeight());
		canvas.setPreferredSize(dim);
	}

	private void createMenu() {
		// Create the menus
		final JMenuBar menubar = new JMenuBar();
		final JMenu objectsMenu = new JMenu("File");
		final JMenu displayMenu = new JMenu("Display");
		final JMenu helpMenu = new JMenu("Help");

		final JMenuItem createObjectItem = new JMenuItem("Create an object");
		final JMenuItem deleteObjectItem = new JMenuItem("Delete an object");
		final JCheckBoxMenuItem displaySearchPanelItem = new JCheckBoxMenuItem("Display search Panel", true);
		displaySearchPanelItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchPane.setVisible(displaySearchPanelItem.isSelected());
			}
		});
		
		final JCheckBoxMenuItem displayResultPanelItem = new JCheckBoxMenuItem("Display Result Panel", true);
		displayResultPanelItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resultsPane.setVisible(displayResultPanelItem.isSelected());
			}
		});

		final JCheckBoxMenuItem displayInfoPanel = new JCheckBoxMenuItem("Display Info Panel", true);
		displayInfoPanel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				routeInfoPane.setVisible(displayInfoPanel.isSelected());
			}
		});
		
		objectsMenu.add(createObjectItem);
		objectsMenu.add(deleteObjectItem);
		displayMenu.add(displaySearchPanelItem);
		displayMenu.add(displayResultPanelItem);
		displayMenu.add(displayInfoPanel);
		menubar.add(objectsMenu);
		menubar.add(displayMenu);
		menubar.add(helpMenu);
		setJMenuBar(menubar);
	}

	public static void addGridBagItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = align;
		gc.fill = GridBagConstraints.VERTICAL;
		p.add(c, gc);
	}


}
