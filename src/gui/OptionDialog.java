package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.jme3.math.ColorRGBA;

/**
 * @Class Classe gérant et affichant le menu Dialog des options
 *
 */
@SuppressWarnings("serial")
public class OptionDialog extends JDialog {

	JSpinner srcAirpSpinner, dstAirpSpinner, geneAirpSpinner, arcSpinner;
	
	JPanel srcAirpColorPanel, dstAirpColorPanel, airpGeneColorPanel;
	JPanel srcColorChooserPanel, dstColorChooserPanel, geneColorChooserPanel, arcColorChooserPanel;
	
	Earth3DMap earth3dMap;
	
	/* On garde en mémoire les valeurs précèdentes */
	static Color srcPrevColor = Color.RED, dstPrevColor = Color.BLUE, genePrevColor = Color.YELLOW, arcPrevColor = Color.ORANGE;
	static double srcPrevSize = 2, dstPrevSize = 2, genePrevSize = 2, arcPrevSize = 2;
	
	private MouseAdapter colorListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JPanel p = (JPanel) e.getSource();
			Color c = p.getBackground();
			c = JColorChooser.showDialog(null, "Select a color", c);
			if(p.equals(srcColorChooserPanel)) srcColorChooserPanel.setBackground(c);
			else if(p.equals(dstColorChooserPanel)) dstColorChooserPanel.setBackground(c);
			else if(p.equals(geneColorChooserPanel)) geneColorChooserPanel.setBackground(c);
			else if(p.equals(arcColorChooserPanel)) arcColorChooserPanel.setBackground(c);
		}
	};
	
	/**@Brief JDialog contenant les options graphiques
	 * @param parent
	 * @param modal
	 * @param earth3dMap
	 */
	public OptionDialog(JFrame parent, boolean modal, Earth3DMap earth3dMap) {
		super(parent, modal);
		this.setTitle("Graphical Options");
		this.setSize(700,200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.earth3dMap = earth3dMap;
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel airportsPanel = new JPanel();
		airportsPanel.setLayout(new BoxLayout(airportsPanel, BoxLayout.X_AXIS));
		
		
		/* Création du panel des src */
		/* ***************************************** */
		JPanel srcAirpPanel =  new JPanel();
		srcAirpPanel.setLayout(new BoxLayout(srcAirpPanel, BoxLayout.Y_AXIS));
		srcColorChooserPanel = createColorSample(srcPrevColor);
		
		srcAirpColorPanel = new JPanel();
		srcAirpColorPanel.setLayout(new BoxLayout(srcAirpColorPanel, BoxLayout.X_AXIS));
		srcAirpColorPanel.add(srcColorChooserPanel);
		srcAirpColorPanel.add(Box.createHorizontalStrut(10));
		srcAirpColorPanel.add(new JLabel("Departing airports color"));
		
		srcAirpPanel.add(srcAirpColorPanel);
		srcAirpPanel.add(Box.createVerticalGlue());
		
		srcAirpSpinner = new JSpinner(new SpinnerNumberModel(srcPrevSize, 1, 10, 0.2));
		JPanel srcAirpSizePanel = new JPanel();
		srcAirpSizePanel.setLayout(new BoxLayout(srcAirpSizePanel, BoxLayout.X_AXIS));

		srcAirpSizePanel.add(srcAirpSpinner);
		srcAirpSizePanel.add(Box.createVerticalGlue());
		srcAirpSizePanel.add(new JLabel("Departing airports size"));
		
		srcAirpPanel.add(srcAirpSizePanel);
		
		srcAirpPanel.setBorder(BorderFactory.createTitledBorder("Departing airports options"));
		airportsPanel.add(srcAirpPanel);
		

		/* Création du panel des dst */
		/* ***************************************** */
		JPanel dstAirpPanel =  new JPanel();
		dstAirpPanel.setLayout(new BoxLayout(dstAirpPanel, BoxLayout.Y_AXIS));
		dstColorChooserPanel = createColorSample(dstPrevColor);
		
		dstAirpColorPanel = new JPanel();
		dstAirpColorPanel.setLayout(new BoxLayout(dstAirpColorPanel, BoxLayout.X_AXIS));
		dstAirpColorPanel.add(dstColorChooserPanel);
		dstAirpColorPanel.add(Box.createHorizontalStrut(10));
		dstAirpColorPanel.add(new JLabel("Arriving airports color"));
		
		dstAirpPanel.add(dstAirpColorPanel);
		dstAirpPanel.add(Box.createVerticalGlue());
		
		dstAirpSpinner = new JSpinner(new SpinnerNumberModel(dstPrevSize, 1, 10, 0.2));
		JPanel dstAirpSizePanel = new JPanel();
		dstAirpSizePanel.setLayout(new BoxLayout(dstAirpSizePanel, BoxLayout.X_AXIS));

		dstAirpSizePanel.add(dstAirpSpinner);
		dstAirpSizePanel.add(Box.createHorizontalStrut(5));
		dstAirpSizePanel.add(new JLabel("Arriving airports size"));
		
		dstAirpPanel.add(dstAirpSizePanel);
		
		dstAirpPanel.setBorder(BorderFactory.createTitledBorder("Arriving airports options"));
		airportsPanel.add(dstAirpPanel);

		
		/* Création du panel des générales */
		/* ***************************************** */
		JPanel airpGenePanel =  new JPanel();
		airpGenePanel.setLayout(new BoxLayout(airpGenePanel, BoxLayout.Y_AXIS));
		geneColorChooserPanel = createColorSample(genePrevColor);
		
		airpGeneColorPanel = new JPanel();
		airpGeneColorPanel.setLayout(new BoxLayout(airpGeneColorPanel, BoxLayout.X_AXIS));
		airpGeneColorPanel.add(geneColorChooserPanel);
		airpGeneColorPanel.add(Box.createHorizontalStrut(10));
		airpGeneColorPanel.add(new JLabel("General airports color"));
		
		airpGenePanel.add(airpGeneColorPanel);
		airpGenePanel.add(Box.createVerticalGlue());
		
		geneAirpSpinner = new JSpinner(new SpinnerNumberModel(genePrevSize, 1, 10, 0.2));
		JPanel airpGeneSizePanel = new JPanel();
		airpGeneSizePanel.setLayout(new BoxLayout(airpGeneSizePanel, BoxLayout.X_AXIS));

		airpGeneSizePanel.add(geneAirpSpinner);
		airpGeneSizePanel.add(Box.createHorizontalStrut(5));
		airpGeneSizePanel.add(new JLabel("General airports size"));
		
		airpGenePanel.add(airpGeneSizePanel);
		
		airpGenePanel.setBorder(BorderFactory.createTitledBorder("General airports options"));
		airportsPanel.add(airpGenePanel);	
		
		/* Création du panel des arcs des routes */
		/* ***************************************** */
		
		JPanel arcOptionPanel = new JPanel();
		arcOptionPanel.setLayout(new BoxLayout(arcOptionPanel, BoxLayout.Y_AXIS));
		arcColorChooserPanel = createColorSample(arcPrevColor);
		
		JPanel arcColorPanel = new JPanel();
		arcColorPanel.setLayout(new BoxLayout(arcColorPanel, BoxLayout.X_AXIS));
		arcColorPanel.add(arcColorChooserPanel);
		arcColorPanel.add(Box.createHorizontalStrut(10));
		arcColorPanel.add(new JLabel("Flights color"));
		
		arcOptionPanel.add(arcColorPanel);
		
		
		arcSpinner = new JSpinner(new SpinnerNumberModel(arcPrevSize, 1, 10, 0.2));
		
		JPanel arcSizePanel = new JPanel();
		arcSizePanel.setLayout(new BoxLayout(arcSizePanel, BoxLayout.X_AXIS));
//		arcSizePanel.add(Box.createHorizontalStrut(50));
		arcSizePanel.add(arcSpinner);
		arcSizePanel.add(Box.createHorizontalStrut(5));
		arcSizePanel.add(new JLabel("Change Flights size"));
		
		arcOptionPanel.add(arcSizePanel);
		
		arcOptionPanel.setBorder(BorderFactory.createTitledBorder("Flights Options"));
		
		JPanel buff = new JPanel();
		buff.setLayout(new BoxLayout(buff, BoxLayout.X_AXIS));
		buff.add(Box.createGlue());
		buff.add(arcOptionPanel);
		buff.add(Box.createGlue());
		
		
		/* Création des boutons */
		/* ***************************************** */
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		
		JButton validButton = new JButton("Validate");
		validButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/* On change l'affichage seulement si la valeur a été changé */
				if(!srcPrevColor.equals(srcColorChooserPanel.getBackground())) {
					srcPrevColor = srcColorChooserPanel.getBackground();
					applyColor(srcPrevColor, "srcAirport");
				}
				if(!dstPrevColor.equals(dstColorChooserPanel.getBackground())) {
					dstPrevColor = dstColorChooserPanel.getBackground();
					applyColor(dstPrevColor, "dstAirport");
				}
				if(!genePrevColor.equals(geneColorChooserPanel.getBackground())) {
					genePrevColor = geneColorChooserPanel.getBackground();
					applyColor(genePrevColor, "General Airport");
				}
				if(!arcPrevColor.equals(arcColorChooserPanel.getBackground())) {
					arcPrevColor = arcColorChooserPanel.getBackground();
					applyColor(arcPrevColor, "arc");
				}
				if(srcPrevSize != (double) srcAirpSpinner.getValue()) { 
					srcPrevSize = (double) srcAirpSpinner.getValue();
					System.out.println(srcPrevSize);
					applySize((float) srcPrevSize, "srcAirport");
				}
				if(dstPrevSize != (double) dstAirpSpinner.getValue()) { 
					dstPrevSize = (double) dstAirpSpinner.getValue();
					applySize((float) dstPrevSize, "dstAirport");
				}
				if(genePrevSize != (double) geneAirpSpinner.getValue()) { 
					genePrevSize = (double) geneAirpSpinner.getValue();
					applySize((float) genePrevSize, "General Airport");
				}
				if(arcPrevSize != (double) arcSpinner.getValue()) { 
					arcPrevSize = (double) arcSpinner.getValue();
					applySize((float) arcPrevSize, "arc");
				}
				dispose();
			}
		});
		
		JButton resetButton = new JButton("Cancel");
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(validButton);
		buttonsPanel.add(resetButton);
		
		/* Ajout des panels dans le panel principal*/
		/* ***************************************** */
		mainPanel.add(airportsPanel);
		mainPanel.add(Box.createGlue());
		mainPanel.add(new JSeparator());
		mainPanel.add(Box.createGlue());
		mainPanel.add(buff);
		mainPanel.add(Box.createGlue());
		mainPanel.add(buttonsPanel);
		
		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setVisible(true);
		pack();
		
	}

	
	/**@Brief Fonction qui crée un JPanel contenant une couleur et associé au bon listener 
	 * @param c
	 * @return
	 */
	private JPanel createColorSample(Color c) {
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.setOpaque(true);
		p.setBackground(c);
		p.setMaximumSize(new Dimension(500, 150));
		p.addMouseListener(colorListener);
		return p;
	}
	
	
	/**@Brief Fonction qui permet d'appliquer les changements de couleur à la partie 3D
	 * @param color
	 * @param type
	 */
	public void applyColor(Color color, String type) {
		earth3dMap.enqueue(new Callable<Boolean>() {

			/* On fait appel au thread spécial de JMonneyEngine qui va s'occupé tout seul de faire la maj */
			@Override
			public Boolean call() throws Exception {
				float[] colorComp = new float[4]; 
				colorComp = color.getRGBComponents(colorComp);
				ColorRGBA buff = new ColorRGBA(colorComp[0], colorComp[1], colorComp[2], colorComp[3]);
				earth3dMap.changeColor(buff, type);
				return true;
			}
			
		});
	}
	
	/**@Brief Fonction qui permet d'appliquer les changements à la partie 31.
	 * @param size
	 * @param type
	 */
	public void applySize(float size, String type) {
		earth3dMap.enqueue(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				earth3dMap.changeSize( size, type);
				return true;
			}
			
		});
	}
}
